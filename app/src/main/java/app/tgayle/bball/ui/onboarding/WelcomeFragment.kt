package app.tgayle.bball.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import app.tgayle.BBallApplication
import app.tgayle.bball.databinding.WelcomeFragmentBinding

class WelcomeFragment : Fragment() {
    private lateinit var binding: WelcomeFragmentBinding
    private val viewModel by viewModels<WelcomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WelcomeFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = OnboardingTeamAdapter()
        adapter.onClick = { team ->
            viewModel.toggleFavorite(team)
            if (!team.favorited) { // new favorite
                BBallApplication.sharedPreferences.edit(commit = true) {
                    putString("lastTeam", team.abbreviation)
                }
            }
        }

        binding.continueFab.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionOnboardingFragmentToRecentGamesFragment())
        }

        viewModel.teams.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.continueAllowed.observe(viewLifecycleOwner, Observer { allowed ->
            if (allowed) {
                binding.continueFab.show()
            } else {
                binding.continueFab.hide()
            }
        })

        binding.teamsList.adapter = adapter
    }

}
