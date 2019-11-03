package app.tgayle.bball.ui.recentgames

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.tgayle.bball.R
import app.tgayle.bball.databinding.ItemAdBinding
import app.tgayle.bball.databinding.ItemGameBriefBinding
import app.tgayle.bball.getTeamLogo
import app.tgayle.bball.models.db.SimpleGameWithTeams
import app.tgayle.bball.ui.ItemOrAd
import java.text.SimpleDateFormat
import java.util.*

typealias OnGameClick = (position: Int, game: SimpleGameWithTeams) -> Unit
typealias OnMenuInteraction = (teamId: Int) -> Unit

class RecentGamesAdapter :
    ListAdapter<ItemOrAd<SimpleGameWithTeams>, RecentGamesAdapter.RecentGameVHOption>(DIFF) {
    var onClick: OnGameClick = { _, _ -> }
    var onMenuItemSelected: OnMenuInteraction = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentGameVHOption {
        return when (viewType) {
            1 -> RecentGameVH(
                ItemGameBriefBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> AdVH(
                ItemAdBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is ItemOrAd.Ad) 0 else 1
    }

    override fun onBindViewHolder(holder: RecentGameVHOption, position: Int) {
        val item = getItem(position)
        if (holder is RecentGameVH && item is ItemOrAd.Item) {
            holder.bind(item.item)
        }
    }

    abstract class RecentGameVHOption(view: View) : RecyclerView.ViewHolder(view)

    inner class AdVH(val binding: ItemAdBinding) : RecentGameVHOption(binding.root)

    inner class RecentGameVH(val binding: ItemGameBriefBinding) : RecentGameVHOption(binding.root) {
        val context = binding.root.context

        fun bind(game: SimpleGameWithTeams) {
            binding.game = game.game
            binding.homeTeam = game.homeTeam
            binding.visitorTeam = game.visitorTeam
            val initialDate =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US).parse(game.game.date)
            val date =
                SimpleDateFormat("EEEE MMMM dd, yyyy, h:mm a zz", Locale.US).format(initialDate)

            val homeAbbv = game.homeTeam.abbreviation
            val awayAbbv = game.visitorTeam.abbreviation

            binding.homeTeamImage.setImageDrawable(context.getDrawable(getTeamLogo(homeAbbv)))
            binding.visitorTeamImage.setImageDrawable(context.getDrawable(getTeamLogo(awayAbbv)))
            binding.gameDate.text = date.toString()

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
        val DIFF = object : DiffUtil.ItemCallback<ItemOrAd<SimpleGameWithTeams>>() {
            override fun areItemsTheSame(
                oldItem: ItemOrAd<SimpleGameWithTeams>,
                newItem: ItemOrAd<SimpleGameWithTeams>
            ): Boolean {
                if (oldItem is ItemOrAd.Item && newItem is ItemOrAd.Item) {
                    return oldItem.item.game.id == newItem.item.game.id
                }

                return false
            }

            override fun areContentsTheSame(
                oldItem: ItemOrAd<SimpleGameWithTeams>,
                newItem: ItemOrAd<SimpleGameWithTeams>
            ): Boolean {
                return when {
                    oldItem is ItemOrAd.Item && newItem is ItemOrAd.Item -> oldItem.item.game == newItem.item.game
                    oldItem is ItemOrAd.Ad && newItem is ItemOrAd.Ad -> true
                    else -> false
                }
            }


        }
    }

}
