package app.tgayle.bball.ui.recentgames

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.tgayle.bball.R
import app.tgayle.bball.databinding.ItemGameBriefBinding
import app.tgayle.bball.models.Game

typealias OnGameClick = (position: Int, game: Game) -> Unit
typealias OnMenuInteraction = (teamId: Int) -> Unit

class RecentGamesAdapter : ListAdapter<Game, RecentGamesAdapter.RecentGameVH>(DIFF) {
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
        fun bind(game: Game) {
            binding.game = game
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onClick(adapterPosition, game)
            }

            binding.moreMenuIcon.setOnClickListener {
                val menu = PopupMenu(binding.root.context, binding.moreMenuIcon)
                menu.inflate(R.menu.menu_item_game_brief)
                menu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.homeTeamInfo -> {
                            onMenuItemSelected(game.homeTeamId)
                            true
                        }
                        R.id.visitorTeamInfo -> {
                            onMenuItemSelected(game.visitorTeamId)
                            true
                        }
                        else -> false
                    }
                }
            }
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Game>() {
            override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem == newItem
            }

        }
    }

}
