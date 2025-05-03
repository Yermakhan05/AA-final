package com.example.myfaith.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfaith.R
import com.example.myfaith.entity.Dhikr

class DhikrListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DhikrAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dhikr_list)

        recyclerView = findViewById(R.id.dhikrRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dhikrList = createDhikrList()
        adapter = DhikrAdapter(dhikrList) { dhikr ->
            val intent = Intent(this, DhikrCounterActivity::class.java).apply {
                putExtra("dhikr_id", dhikr.id)
                putExtra("dhikr_name", dhikr.name)
                putExtra("dhikr_arabic", dhikr.arabicText)
                putExtra("dhikr_translation", dhikr.translation)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }

    private fun createDhikrList(): List<Dhikr> {
        return listOf(
            Dhikr(
                1,
                "SubhanAllah",
                "سُبْحَانَ ٱللَّٰهِ",
                "Glory be to Allah",
                "Glorifying Allah and declaring His perfection"
            ),
            Dhikr(
                2,
                "Alhamdulillah",
                "ٱلْحَمْدُ لِلَّٰهِ",
                "All praise is due to Allah",
                "Expressing gratitude to Allah"
            ),
            Dhikr(
                3,
                "Allahu Akbar",
                "ٱللَّٰهُ أَكْبَرُ",
                "Allah is the Greatest",
                "Declaring the greatness of Allah"
            ),
            Dhikr(
                4,
                "La ilaha illa Allah",
                "لَا إِلَٰهَ إِلَّا ٱللَّٰهُ",
                "There is no deity worthy of worship except Allah",
                "The declaration of faith"
            ),
            Dhikr(
                5,
                "Astaghfirullah",
                "أَسْتَغْفِرُ ٱللَّٰهَ",
                "I seek forgiveness from Allah",
                "Seeking forgiveness from Allah"
            )
        )
    }
}

class DhikrAdapter(
    private val dhikrList: List<Dhikr>,
    private val onItemClick: (Dhikr) -> Unit
) : RecyclerView.Adapter<DhikrAdapter.DhikrViewHolder>() {

    class DhikrViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.dhikrNameTextView)
        val arabicTextView: TextView = view.findViewById(R.id.arabicTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DhikrViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dhikr, parent, false)
        return DhikrViewHolder(view)
    }

    override fun onBindViewHolder(holder: DhikrViewHolder, position: Int) {
        val dhikr = dhikrList[position]
        holder.nameTextView.text = dhikr.name
        holder.arabicTextView.text = dhikr.arabicText
        holder.itemView.setOnClickListener { onItemClick(dhikr) }
    }

    override fun getItemCount() = dhikrList.size
} 