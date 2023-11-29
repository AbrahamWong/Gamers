package com.minotawr.gamers.ui.main

import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.minotawr.gamers.R
import com.minotawr.gamers.databinding.AdapterMainBinding
import com.minotawr.gamers.domain.model.Game

class MainViewHolder(
    private val binding: AdapterMainBinding,
    private val delegate: MainListDelegate?,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Game) {
        Glide.with(itemView.context)
            .load(item.backgroundImage)
            .into(binding.imgGame)

        binding.tvTitle.text = item.name
        binding.tvRating.text = String.format("Rated %.2f out of 5 stars", item.rating)

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