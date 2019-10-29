package app.tgayle.bball.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.tgayle.bball.databinding.ItemChooseTeamBinding
import app.tgayle.bball.databinding.SwitchTeamFragmentBinding
import app.tgayle.bball.getTeamLogo
import app.tgayle.bball.models.Team
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SwitchTeamDialog(val teams: List<Team>) : BottomSheetDialogFragment() {
    private lateinit var binding: SwitchTeamFragmentBinding
    var onTeamSelected: (team: Team) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SwitchTeamFragmentBinding.inflate(inflater, container, false)

        val adapter = TeamAdapter()
        binding.itemList.adapter = adapter
        binding.itemList.layoutManager = LinearLayoutManager(context!!)
        return binding.root
    }

    inner class TeamAdapter : RecyclerView.Adapter<TeamVH>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamVH {
            return TeamVH(
                ItemChooseTeamBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun getItemCount() = teams.size

        override fun onBindViewHolder(holder: TeamVH, position: Int) {
            holder.bind(teams[position])
        }

    }

    inner class TeamVH(val binding: ItemChooseTeamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(team: Team) {
            binding.team = team
            binding.image.setImageDrawable(binding.root.context.getDrawable(getTeamLogo(team.abbreviation)))

            binding.root.setOnClickListener {
                onTeamSelected(team)
                dismiss()
            }
        }
    }
}