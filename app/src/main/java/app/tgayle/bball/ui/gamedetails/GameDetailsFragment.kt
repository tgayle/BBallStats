package app.tgayle.bball.ui.gamedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.tgayle.bball.databinding.GameDetailsFragmentBinding
import app.tgayle.bball.getTeamLogo
import java.text.SimpleDateFormat
import java.util.*

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

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.currentGame.observe(viewLifecycleOwner, Observer { game ->
            binding.homeTeamImage.setImageDrawable(context!!.getDrawable(getTeamLogo(game.homeTeam.abbreviation)))
            binding.visitorTeamImage.setImageDrawable(context!!.getDrawable(getTeamLogo(game.visitorTeam.abbreviation)))

            val initialDate =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US).parse(game.game.date)
            val date =
                SimpleDateFormat("EEEE MMMM dd, yyyy, h:mm a zz", Locale.US).format(initialDate)
            binding.gameDate.text = date
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            binding.swipeRefresh.isRefreshing = it
        })
    }

}
