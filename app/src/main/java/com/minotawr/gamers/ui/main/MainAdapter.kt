package com.minotawr.gamers.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.minotawr.gamers.databinding.AdapterMainBinding
import com.minotawr.gamers.domain.model.Game

class MainAdapter: RecyclerView.Adapter<MainViewHolder>() {

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