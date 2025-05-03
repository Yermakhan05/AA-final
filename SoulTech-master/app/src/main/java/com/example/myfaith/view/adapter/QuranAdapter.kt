package com.example.myfaith.view.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfaith.R
import com.example.myfaith.model.entity.Verse

class QuranAdapter(private val verses: List<Verse>) : RecyclerView.Adapter<QuranAdapter.QuranViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuranViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_verse, parent, false)
        return QuranViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuranViewHolder, position: Int) {
        val verse = verses[position]
        holder.bind(verse)
    }

    override fun getItemCount(): Int = verses.size

    class QuranViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val verseNumber: TextView = itemView.findViewById(R.id.verseNumber)
        private val arabicText: TextView = itemView.findViewById(R.id.arabicText)
        private val transliterationText: TextView = itemView.findViewById(R.id.transliterationText)
        private val translationText: TextView = itemView.findViewById(R.id.translationText)

        fun bind(verse: Verse) {
            verseNumber.text = verse.number.toString()
            transliterationText.text = verse.transliteration
            translationText.text = verse.translation

            // Подсветка арабского текста
            var highlightedText = verse.arabicText
            for ((word, color) in verse.highlights) {
                highlightedText = highlightedText.replace(
                    word,
                    "<font color=\"$color\">$word</font>"
                )
            }

            arabicText.text = Html.fromHtml(highlightedText, Html.FROM_HTML_MODE_LEGACY)
        }
    }
}
