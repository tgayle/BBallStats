package app.tgayle.bball.ui.listteams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import app.tgayle.bball.R

class ListTeamsFragment : Fragment() {

    companion object {
        fun newInstance() = ListTeamsFragment()
    }

    private lateinit var viewModel: ListTeamsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_teams_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListTeamsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
