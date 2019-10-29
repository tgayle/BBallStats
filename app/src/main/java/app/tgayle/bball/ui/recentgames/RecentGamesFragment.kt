package app.tgayle.bball.ui.recentgames

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.tgayle.BBallApplication
import app.tgayle.bball.R
import app.tgayle.bball.databinding.RecentGamesFragmentBinding
import app.tgayle.bball.getTeamLogo
import app.tgayle.bball.ui.SwitchTeamDialog
import app.tgayle.bball.ui.buildIAPDialog
import com.google.android.material.snackbar.Snackbar

class RecentGamesFragment : Fragment() {
    private lateinit var binding: RecentGamesFragmentBinding
    private val viewModel by viewModels<RecentGamesViewModel>()
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.refresh()

        val lastFavTeam = BBallApplication.sharedPreferences.getString("lastTeam", null)
        if (lastFavTeam == null) {
            findNavController().navigate(RecentGamesFragmentDirections.actionRecentGamesFragmentToOnboardingFragment())
        } else {
            viewModel.setCurrentTeamFromAbbreviation(lastFavTeam)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecentGamesFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_INDEFINITE)
        val snackbarView = snackbar?.view
        val snackbarTextId = com.google.android.material.R.id.snackbar_text
        val textView = snackbarView?.findViewById<View>(snackbarTextId) as TextView?
        textView?.setTextColor(context!!.getColor(R.color.colorAccent))

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = RecentGamesAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context!!)
        adapter.onClick = { position, game ->
            findNavController().navigate(
                RecentGamesFragmentDirections.actionListTeamsFragmentToGameDetailsFragment(
                    game.game.id
                )
            )
        }

        adapter.onMenuItemSelected = { teamId ->
            Toast.makeText(context!!, "Stub! ¯\\_(ツ)_/¯", Toast.LENGTH_SHORT).show()
        }

        viewModel.refreshing.observe(viewLifecycleOwner, Observer {
            binding.swipeRefresh.isRefreshing = it
        })

        viewModel.message.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                snackbar?.setText(it)
                snackbar?.show()
            } else {
                snackbar?.dismiss()
            }
        })

        viewModel.recentGames.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            binding.recyclerView.scrollToPosition(0)
            binding.noStatsText.alpha = if (it.isNullOrEmpty()) {
                1f
            } else {
                0f
            }
        })

        viewModel.selectedTeam.observe(viewLifecycleOwner, Observer {
            binding.currentTeamText.text = it.fullName
            binding.selectedTeamImage.setImageDrawable(context!!.getDrawable(getTeamLogo(it.abbreviation)))
        })

        viewModel.currentSeasonRange.observe(viewLifecycleOwner, Observer {
            binding.selectedSeason.text = it
        })

        // Empty observer to get LiveData emitting
        viewModel.favoritedTeams.observe(viewLifecycleOwner, Observer { })

        binding.swipeRefresh.setOnRefreshListener { viewModel.refresh() }
        binding.switchTeamBtn.setOnClickListener { showTeamPicker() }
        binding.currentTeamText.setOnClickListener { showTeamPicker() }
        binding.selectedTeamImage.setOnClickListener { showTeamPicker() }
        binding.selectedSeason.setOnClickListener { showSeasonPicker() }
    }

    fun showTeamPicker() {
        viewModel.favoritedTeams.value?.let { teams ->
            val dialog = SwitchTeamDialog(teams)
            dialog.onTeamSelected = { selectedTeam -> viewModel.selectNewTeam(selectedTeam) }
            dialog.show(childFragmentManager.beginTransaction(), "teampicker")
        }
    }

    fun showSeasonPicker() {
        val builder = AlertDialog.Builder(context!!)

        val years = arrayListOf<Int>()
        val seasonRanges = arrayListOf<String>()
        for (year in 2018 downTo 1979) {
            years += year
            seasonRanges += "$year-${year + 1}"
        }

        builder.setItems(seasonRanges.toTypedArray()) { dialog, which ->
            val yearSelected = years[which]

            if (2019 - yearSelected > 6) {
                buildIAPDialog(context!!).show()
            } else {
                viewModel.selectNewSeason(years[which])
            }
        }

        builder.show()
    }

}
