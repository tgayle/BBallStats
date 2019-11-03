package app.tgayle.bball.ui.recentgames

import android.app.AlertDialog
import android.graphics.Color
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
import app.tgayle.bball.databinding.RecentGamesFragmentBinding
import app.tgayle.bball.getTeamLogo
import app.tgayle.bball.models.db.SimpleGameWithTeams
import app.tgayle.bball.ui.ItemOrAd
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
        textView?.setTextColor(Color.WHITE)

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
            val gamesWithAds = mutableListOf<ItemOrAd<SimpleGameWithTeams>>()

            if (it.isNullOrEmpty()) {
                binding.noStatsText.alpha = 1f
            } else {
                it.forEachIndexed { index, gameWithTeams ->
                    if (index % 5 == 0) {
                        gamesWithAds.add(ItemOrAd.Ad())
                    }
                    gamesWithAds += ItemOrAd.Item(gameWithTeams)
                }

                binding.noStatsText.alpha = 0f
            }

            adapter.submitList(gamesWithAds)
            binding.recyclerView.scrollToPosition(0)
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

        binding.previousSeasonButton.setOnClickListener { switchSeason(viewModel.currentSeason.value!! - 1) }
        binding.nextSeasonButton.setOnClickListener {
            val newYear = viewModel.currentSeason.value!! + 1

            if (newYear < 2020) {
                switchSeason(newYear)
            } else {
                Toast.makeText(context, "No future seasons...", Toast.LENGTH_SHORT).show()
            }
        }

        binding.nextTeamButton.setOnClickListener { viewModel.viewNextTeam() }
        binding.previousTeamBtn.setOnClickListener { viewModel.viewPreviousTeam() }

        binding.refreshBtn.setOnClickListener { viewModel.refresh() }
        binding.swipeRefresh.setOnRefreshListener { viewModel.refresh() }
//        binding.switchTeamBtn.setOnClickListener { showTeamPicker() }
        binding.currentTeamText.setOnClickListener { showTeamPicker() }
        binding.selectedTeamImage.setOnClickListener { showTeamPicker() }
        binding.selectedSeason.setOnClickListener { showSeasonPicker() }
    }

    private fun switchSeason(newYear: Int) {
        if (canViewThisSeason(newYear)) {
            viewModel.selectNewSeason(newYear)
        } else {
            buildIAPDialog(context!!).show()
        }
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
        for (year in 2019 downTo 1979) {
            years += year
            seasonRanges += "$year-${year + 1}"
        }

        builder.setItems(seasonRanges.toTypedArray()) { dialog, which ->
            val yearSelected = years[which]

            if (canViewThisSeason(yearSelected)) {
                viewModel.selectNewSeason(years[which])
            } else {
                buildIAPDialog(context!!).show()
            }
        }

        builder.show()
    }

    fun canViewThisSeason(year: Int) = 2019 - year < 6

}
