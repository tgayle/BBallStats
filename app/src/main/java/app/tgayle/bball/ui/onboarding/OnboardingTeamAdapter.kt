package app.tgayle.bball.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.tgayle.bball.databinding.ItemTeamIconBinding
import app.tgayle.bball.getTeamLogo
import app.tgayle.bball.models.Team

class OnboardingTeamAdapter : ListAdapter<Team, OnboardingTeamAdapter.FavoriteTeamVH>(diff) {
    var onClick: (team: Team) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTeamVH {
        return FavoriteTeamVH(
            ItemTeamIconBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteTeamVH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FavoriteTeamVH(val binding: ItemTeamIconBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Team) {
            binding.image.setImageDrawable(binding.root.context.getDrawable(getTeamLogo(item.abbreviation)))
            binding.teamName.text = item.fullName

            binding.root.setOnClickListener { onClick(item) }

            binding.favoritedOverlay.alpha = if (item.favorited) {
                0.7f
            } else {
                0f
            }
        }
    }

    companion object {
        val diff = object : DiffUtil.ItemCallback<Team>() {
            override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
                return oldItem.favorited == newItem.favorited
            }
        }
    }
}