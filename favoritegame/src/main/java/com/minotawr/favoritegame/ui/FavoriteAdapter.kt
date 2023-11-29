package com.minotawr.favoritegame.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.minotawr.favoritegame.databinding.AdapterFavoriteBinding
import com.minotawr.gamers.domain.model.Game

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val list = mutableListOf<Game>()

    var delegate: FavoriteListDelegate? = null

    class GameDiffCallback(private val oldList: List<Game>, private val newList: List<Game>): DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return oldItem == newItem
        }
    }

    class FavoriteViewHolder(private val binding: AdapterFavoriteBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Game) {
            Glide.with(itemView.context)
                .load(item.backgroundImage)
                .into(binding.imgGame)

            binding.tvTitle.text = item.name
            binding.tvRating.text = String.format("Rated %.2f out of 5 stars", item.rating)

            item.genres?.take(2)?.forEachIndexed { index, genre ->
                if (index < 1) {
                    binding.tvGenre1.text = genre.name
                } else {
                    binding.tvGenre2.text = genre.name
                }
            }

            binding.imgGame.contentDescription = "Image of ${item.name}'s banner."
        }
    }

    interface FavoriteListDelegate {
        fun onItemClick(item: Game)
    }

    fun setList(data: List<Game>) {
        val diffCallback = GameDiffCallback(list, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        list.clear()
        list.addAll(data)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = AdapterFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            delegate?.onItemClick(list[position])
        }
    }
}