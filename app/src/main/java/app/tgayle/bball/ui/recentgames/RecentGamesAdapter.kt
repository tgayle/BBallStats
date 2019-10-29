package app.tgayle.bball.ui.recentgames

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.tgayle.bball.R
import app.tgayle.bball.databinding.ItemGameBriefBinding
import app.tgayle.bball.getTeamLogo
import app.tgayle.bball.models.db.SimpleGameWithTeams

typealias OnGameClick = (position: Int, game: SimpleGameWithTeams) -> Unit
typealias OnMenuInteraction = (teamId: Int) -> Unit

class RecentGamesAdapter : ListAdapter<SimpleGameWithTeams, RecentGamesAdapter.RecentGameVH>(DIFF) {
    var onClick: OnGameClick = { _, _ -> }
    var onMenuItemSelected: OnMenuInteraction = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentGameVH {
        return RecentGameVH(
            ItemGameBriefBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecentGameVH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RecentGameVH(val binding: ItemGameBriefBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val context = binding.root.context

        fun bind(game: SimpleGameWithTeams) {
            binding.game = game.game
            binding.homeTeam = game.homeTeam
            binding.visitorTeam = game.visitorTeam

            val homeAbbv = game.homeTeam.abbreviation
            val awayAbbv = game.visitorTeam.abbreviation

            binding.homeTeamImage.setImageDrawable(context.getDrawable(getTeamLogo(homeAbbv)))
            binding.visitorTeamImage.setImageDrawable(context.getDrawable(getTeamLogo(awayAbbv)))

            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onClick(adapterPosition, game)
            }

            // TODO: Reformat date

            binding.moreMenuIcon.setOnClickListener {
                val menu = PopupMenu(binding.root.context, binding.moreMenuIcon)
                menu.inflate(R.menu.menu_item_game_brief)
                menu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.homeTeamInfo -> {
                            onMenuItemSelected(game.homeTeam.id)
                            true
                        }
                        R.id.visitorTeamInfo -> {
                            onMenuItemSelected(game.homeTeam.id)
                            true
                        }
                        else -> false
                    }
                }
                menu.show()
            }
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<SimpleGameWithTeams>() {
            override fun areItemsTheSame(
                oldItem: SimpleGameWithTeams,
                newItem: SimpleGameWithTeams
            ): Boolean {
                return oldItem.game.id == newItem.game.id
            }

            override fun areContentsTheSame(
                oldItem: SimpleGameWithTeams,
                newItem: SimpleGameWithTeams
            ): Boolean {
                return oldItem.game == newItem.game
            }

        }
    }

}
