package app.tgayle.bball.ui.onboarding

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import app.tgayle.BBallApplication
import app.tgayle.bball.databinding.WelcomeFragmentBinding
import app.tgayle.bball.ui.buildIAPDialog
import com.google.android.material.snackbar.Snackbar


class WelcomeFragment : Fragment() {
    private lateinit var binding: WelcomeFragmentBinding
    private val viewModel by viewModels<WelcomeViewModel>()
    private var numFavorited = 0
    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WelcomeFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.loadTeams()
        snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_INDEFINITE)

        val snackbarView = snackbar?.view
        val snackbarTextId = com.google.android.material.R.id.snackbar_text
        val textView = snackbarView?.findViewById<View>(snackbarTextId) as TextView?
        textView?.setTextColor(Color.WHITE)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = OnboardingTeamAdapter()
        binding.teamsList.adapter = adapter

        adapter.onClick = { team ->
            val teamIsBeingUnfavorited = team.favorited

            if (teamIsBeingUnfavorited || numFavorited < 4) {
                viewModel.toggleFavorite(team)
                if (!team.favorited) { // new favorite
                    BBallApplication.sharedPreferences.edit(commit = true) {
                        putString("lastTeam", team.abbreviation)
                    }
                }
            } else if (numFavorited > 3) {
                buildIAPDialog(context!!).show()
            }
        }

        viewModel.message.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                snackbar?.setText(it)
                snackbar?.show()
            } else {
                snackbar?.dismiss()
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            binding.swipeRefresh.isRefreshing = it
        })

        viewModel.teams.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            binding.teamCount.text = "${it.filter { it.favorited }.size}/4"
            numFavorited = it.filter { it.favorited }.size
        })

        viewModel.continueAllowed.observe(viewLifecycleOwner, Observer { allowed ->
            if (allowed) {
                binding.continueFab.show()
            } else {
                binding.continueFab.hide()
            }
        })

        binding.continueFab.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionOnboardingFragmentToRecentGamesFragment())
        }

        binding.moreTeamsBtn.setOnClickListener { buildIAPDialog(context!!).show() }
        binding.swipeRefresh.setOnRefreshListener { viewModel.loadTeams() }
    }

}
