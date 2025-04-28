package com.example.myfaith.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynavigationapp.R
import com.example.myfaith.entity.Verse

class VerseAdapter(private val verses: List<Verse>) :
    RecyclerView.Adapter<VerseAdapter.VerseViewHolder>() {

    class VerseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val verseNumber: TextView = itemView.findViewById(R.id.verseNumber)  // Получаем ссылку на verseNumber
        val arabicText: TextView = itemView.findViewById(R.id.arabicText)
        val transliterationText: TextView = itemView.findViewById(R.id.transliterationText)
        val translationText: TextView = itemView.findViewById(R.id.translationText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_verse, parent, false)
        return VerseViewHolder(view)
    }

    override fun getItemCount() = verses.size

    override fun onBindViewHolder(holder: VerseViewHolder, position: Int) {
        val verse = verses[position]

        // Устанавливаем номер аята
        holder.verseNumber.text = verse.number.toString()

        // Устанавливаем арабский текст с подсветкой
        val arabicSpannable = SpannableString(verse.arabicText)
        verse.highlights.forEach { (word, colorHex) ->
            val startIndex = verse.arabicText.indexOf(word)
            if (startIndex >= 0) {
                arabicSpannable.setSpan(
                    ForegroundColorSpan(Color.parseColor(colorHex)),
                    startIndex,
                    startIndex + word.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        holder.arabicText.text = arabicSpannable
        holder.transliterationText.text = verse.transliteration
        holder.translationText.text = verse.translation
    }
}
