package app.tgayle.bball.ui.recentgames

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.tgayle.BBallApplication
import app.tgayle.bball.databinding.RecentGamesFragmentBinding
import app.tgayle.bball.getTeamLogo
import app.tgayle.bball.ui.SwitchTeamDialog

class RecentGamesFragment : Fragment() {
    private lateinit var binding: RecentGamesFragmentBinding
    private val viewModel by viewModels<RecentGamesViewModel>()

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
            findNavController().navigate(
                RecentGamesFragmentDirections.actionListTeamsFragmentToTeamDetailsFragment(
                    teamId
                )
            )
        }

        viewModel.refreshing.observe(viewLifecycleOwner, Observer {
            binding.swipeRefresh.isRefreshing = it
        })

        viewModel.recentGames.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
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
        binding.currentTeamText.setOnClickListener { showTeamPicker() }
        binding.selectedTeamImage.setOnClickListener { showTeamPicker() }
        binding.selectedSeason.setOnClickListener { showSeasonPicker() }
    }

    fun showTeamPicker() {
        println("outercall")
        viewModel.favoritedTeams.value?.let { teams ->
            val dialog = SwitchTeamDialog(teams)
            println("called")
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
            viewModel.selectNewSeason(years[which])
        }

        builder.show()
    }

}
