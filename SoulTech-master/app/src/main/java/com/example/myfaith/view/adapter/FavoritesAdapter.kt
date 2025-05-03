package com.example.myfaith.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfaith.R
import com.example.myfaith.model.entity.Quote

class FavoritesAdapter(
    private var favoriteQuotes: List<Quote>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val numberTextView: TextView = view.findViewById(R.id.numberTextView)
        val quoteTextView: TextView = view.findViewById(R.id.quoteTextView)
        val sourceTextView: TextView = view.findViewById(R.id.sourceTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite_quote, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val quote = favoriteQuotes[position]

        holder.numberTextView.text = (position + 1).toString()
        holder.quoteTextView.text = quote.text
        holder.sourceTextView.text = quote.source

        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }

    override fun getItemCount() = favoriteQuotes.size

    fun updateData(newFavorites: List<Quote>) {
        favoriteQuotes = newFavorites
        notifyDataSetChanged()
    }
}
