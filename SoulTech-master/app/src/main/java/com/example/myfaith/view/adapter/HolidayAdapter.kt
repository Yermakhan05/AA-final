package com.example.myfaith.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfaith.model.entity.Holiday
import com.example.myfaith.R




class HolidayAdapter(private val holidays: List<Holiday>) :
    RecyclerView.Adapter<HolidayAdapter.HolidayViewHolder>() {

    class HolidayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.holidayTitle)
        val dates: TextView = itemView.findViewById(R.id.holidayDates)
        val description: TextView = itemView.findViewById(R.id.holidayDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolidayViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_holiday, parent, false)
        return HolidayViewHolder(view)
    }

    override fun onBindViewHolder(holder: HolidayViewHolder, position: Int) {
        val holiday = holidays[position]
        holder.title.text = "🕌 ${holiday.title}"
        holder.dates.text = "📅 ${holiday.hijri} — ${holiday.gregorian}"
        holder.description.text = "🔸 ${holiday.description}"
    }

    override fun getItemCount() = holidays.size
}