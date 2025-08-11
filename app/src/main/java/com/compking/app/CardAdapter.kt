package com.compking.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CardAdapter :
    ListAdapter<Card, CardAdapter.CardViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val playerName: TextView = itemView.findViewById(R.id.playerName)
        private val cardSet: TextView = itemView.findViewById(R.id.cardSet)
        private val price: TextView = itemView.findViewById(R.id.price)

        fun bind(card: Card) {
            playerName.text = card.playerName
            cardSet.text = "${card.year} ${card.cardSet}"
            price.text = "$${card.salePrice}"
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card) =
            oldItem.playerName == newItem.playerName &&
            oldItem.cardSet == newItem.cardSet &&
            oldItem.year == newItem.year

        override fun areContentsTheSame(oldItem: Card, newItem: Card) = oldItem == newItem
    }
}
