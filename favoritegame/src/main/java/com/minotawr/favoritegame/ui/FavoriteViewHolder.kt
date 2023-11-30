package com.minotawr.favoritegame.ui

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.minotawr.gamers.databinding.AdapterMainBinding
import com.minotawr.gamers.domain.model.Game

class FavoriteViewHolder(private val binding: AdapterMainBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Game) {
        Glide.with(itemView.context)
            .load(item.backgroundImage)
            .into(binding.imgGame)

        binding.tvTitle.text = item.name
        binding.tvRating.text = String.format("Rated %.2f out of 5 stars", item.rating)

        binding.imgFavorite.isVisible = false

        binding.imgGame.contentDescription = "Image of ${item.name}'s banner."
    }
}