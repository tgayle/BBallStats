package app.tgayle.bball.ui.gamedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import app.tgayle.bball.databinding.GameDetailsFragmentBinding
import app.tgayle.bball.getTeamLogo

class GameDetailsFragment : Fragment() {
    private lateinit var binding: GameDetailsFragmentBinding
    private val args by navArgs<GameDetailsFragmentArgs>()
    private val viewModel by viewModels<GameDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.setGame(args.gameId)
        binding = GameDetailsFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.currentGame.observe(viewLifecycleOwner, Observer {
            binding.homeTeamImage.setImageDrawable(context!!.getDrawable(getTeamLogo(it.homeTeam.abbreviation)))
            binding.visitorTeamImage.setImageDrawable(context!!.getDrawable(getTeamLogo(it.visitorTeam.abbreviation)))
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            binding.swipeRefresh.isRefreshing = it
        })
    }

}
