package app.tgayle.bball.ui.gamedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import app.tgayle.bball.R

class GameDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = GameDetailsFragment()
    }

    private lateinit var viewModel: GameDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.game_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GameDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
