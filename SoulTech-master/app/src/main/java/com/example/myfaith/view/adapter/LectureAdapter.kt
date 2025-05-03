package com.example.myfaith.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.Lecture
import com.example.myfaith.R

class LectureAdapter(
    private val lectures: List<Lecture>,
    private val onItemClick: (Lecture) -> Unit
) : RecyclerView.Adapter<LectureAdapter.LectureViewHolder>() {

    inner class LectureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView = itemView.findViewById(R.id.imageThumbnail)
        val title: TextView = itemView.findViewById(R.id.textTitle)
        val description: TextView = itemView.findViewById(R.id.textDescription)
        val buttonWatch: Button = itemView.findViewById(R.id.buttonWatch)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lecture, parent, false)
        return LectureViewHolder(view)
    }

    override fun onBindViewHolder(holder: LectureViewHolder, position: Int) {
        val lecture = lectures[position]
        holder.title.text = lecture.title
        holder.description.text = lecture.description
        Glide.with(holder.itemView.context)
            .load(lecture.thumbnailUrl)
            .into(holder.thumbnail)

        holder.buttonWatch.setOnClickListener {
            onItemClick(lecture)
        }
    }

    override fun getItemCount() = lectures.size
}

