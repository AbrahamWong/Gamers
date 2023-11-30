package com.minotawr.favoritegame.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minotawr.gamers.databinding.AdapterMainBinding
import com.minotawr.gamers.domain.model.Game

class FavoriteAdapter : RecyclerView.Adapter<FavoriteViewHolder>() {

    private val list = mutableListOf<Game>()

    var delegate: FavoriteListDelegate? = null

    fun setList(data: List<Game>) {
        val currentSize = list.size
        list.clear()
        notifyItemRangeRemoved(0, currentSize)

        list.addAll(data)
        notifyItemRangeInserted(0, data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = AdapterMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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