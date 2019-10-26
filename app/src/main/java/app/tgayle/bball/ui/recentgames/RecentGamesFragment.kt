package app.tgayle.bball.ui.recentgames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import app.tgayle.bball.databinding.RecentGamesFragmentBinding

class RecentGamesFragment : Fragment() {
    private lateinit var binding: RecentGamesFragmentBinding
    private val viewModel by viewModels<RecentGamesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.refresh()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecentGamesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = RecentGamesAdapter()
        adapter.onClick = { position, game ->
            findNavController().navigate(
                RecentGamesFragmentDirections.actionListTeamsFragmentToGameDetailsFragment(
                    game.id
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
    }

}
