package com.example.myfaith.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.myfaith.model.entity.Surah
import com.example.myfaith.R

class SurahListAdapter(
    private val context: Context,
    private val surahs: List<Surah>,
    private val onClick: (Surah) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = surahs.size
    override fun getItem(position: Int): Any = surahs[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_surah, parent, false)

        val surah = surahs[position]

        val numberText = view.findViewById<TextView>(R.id.surahNumber)
        val nameText = view.findViewById<TextView>(R.id.surahName)
        val ayahCountText = view.findViewById<TextView>(R.id.ayahCount)

        numberText.text = surah.number.toString()
        nameText.text = surah.name
        ayahCountText.text = "${surah.verses.size} аят"

        view.setOnClickListener { onClick(surah) }

        return view
    }
}
