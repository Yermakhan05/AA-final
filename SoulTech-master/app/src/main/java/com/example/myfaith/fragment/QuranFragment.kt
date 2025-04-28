package com.example.myfaith.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myfaith.adapter.QuranAdapter
import com.example.myfaith.adapter.SurahListAdapter
import com.example.myfaith.entity.QuranData
import com.example.myfaith.entity.Surah
import com.example.mynavigationapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

class QuranFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuranAdapter
    private lateinit var surahTitle: TextView
    private lateinit var surahInfo: TextView
    private lateinit var paraInfo: TextView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var surahListView: ListView
    private lateinit var menuButton: ImageButton

    private lateinit var allSurahs: List<Surah>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quran, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Скрыть нижнее меню
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.visibility = View.GONE

        // Инициализация UI
        drawerLayout = view.findViewById(R.id.drawer_layout)
        surahListView = view.findViewById(R.id.surahListView)
        menuButton = view.findViewById(R.id.menuButton)

        recyclerView = view.findViewById(R.id.recyclerView)
        surahTitle = view.findViewById(R.id.surahTitle)
        surahInfo = view.findViewById(R.id.surahInfo)
        paraInfo = view.findViewById(R.id.paraInfo)

        // Обработка открытия бокового меню
        menuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        loadQuranData()
    }

    private fun loadQuranData() {
        try {
            val inputStream = context?.assets?.open("quran_data.json")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val jsonString = reader.readText()
            reader.close()

            val quranData = Gson().fromJson(jsonString, QuranData::class.java)
            allSurahs = quranData.surahs

            if (allSurahs.isNotEmpty()) {
                displaySurah(allSurahs[0]) // первая сура по умолчанию

                // Список сур в боковом меню
                val listAdapter = SurahListAdapter(requireContext(), allSurahs) { selectedSurah ->
                    displaySurah(selectedSurah)
                    drawerLayout.closeDrawer(GravityCompat.START)
                }

                surahListView.adapter = listAdapter
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun displaySurah(surah: Surah) {
        updateSurahHeader(surah)
        adapter = QuranAdapter(surah.verses)
        recyclerView.adapter = adapter
    }

    private fun updateSurahHeader(surah: Surah) {
        surahTitle.text = "${surah.number}. ${surah.name}"
        surahInfo.text = "${surah.type} • ${surah.verses.size} Аят"
        paraInfo.text = "Пара 27 • ¼ Hizb 54" // Можешь обновить из JSON позже
    }


}
