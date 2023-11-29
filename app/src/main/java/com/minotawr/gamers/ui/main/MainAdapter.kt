package com.minotawr.gamers.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.minotawr.gamers.R
import com.minotawr.gamers.databinding.AdapterMainBinding
import com.minotawr.gamers.domain.model.Game

class MainAdapter: RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val list = mutableListOf<Game>()

    var delegate: MainListDelegate? = null

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

    class MainViewHolder(private val binding: AdapterMainBinding, private val delegate: MainListDelegate?): RecyclerView.ViewHolder(binding.root) {
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

            setFavoriteIcon(item)

            binding.imgFavorite.setOnClickListener {
                item.isFavorite = !item.isFavorite
                setFavoriteIcon(item)
                delegate?.onFavorite(item)
            }

            binding.imgGame.contentDescription = "Image of ${item.name}'s banner."
        }

        private fun setFavoriteIcon(item: Game) {
            binding.imgFavorite.setImageDrawable(
                ResourcesCompat.getDrawable(
                    itemView.resources,
                    if (item.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border,
                    null
                )
            )
        }
    }

    interface MainListDelegate {
        fun onItemClick(item: Game)

        fun onFavorite(item: Game)
    }

    fun setList(data: List<Game>) {
        val diffCallback = GameDiffCallback(list, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        list.clear()
        list.addAll(data)

        diffResult.dispatchUpdatesTo(this)
    }

    fun addList(data: List<Game>) {
        val newData = list.toMutableList()
        newData.addAll(data)
        val diffCallback = GameDiffCallback(list, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        list.clear()
        list.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = AdapterMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding, delegate)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            delegate?.onItemClick(list[position])
        }
    }
}