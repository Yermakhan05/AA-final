package com.example.myfaith.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfaith.view.adapter.QuranAdapter
import com.example.myfaith.view.adapter.SurahListAdapter
import com.example.myfaith.model.entity.QuranData
import com.example.myfaith.model.entity.Surah
import com.example.myfaith.R
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
    private lateinit var progressBar: ProgressBar
    private lateinit var errorText: TextView

    private lateinit var allSurahs: List<Surah>
    private var selectedSurahIndex: Int = 0

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
        progressBar = view.findViewById(R.id.progressBar)
        errorText = view.findViewById(R.id.errorText)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Обработка открытия бокового меню
        menuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
            activity?.findViewById<Toolbar>(R.id.toolbar)?.visibility = View.GONE
        }


        val args: QuranFragmentArgs by navArgs()
        selectedSurahIndex = if (args.surahId != 0) {
            args.surahId - 1
        } else {
            savedInstanceState?.getInt("selected_surah_index", 0) ?: 0
        }

        loadQuranData()
    }

    private fun loadQuranData() {
        progressBar.visibility = View.VISIBLE
        errorText.visibility = View.GONE
        try {
            val inputStream = context?.assets?.open("quran_data.json")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val jsonString = reader.readText()
            reader.close()

            val quranData = Gson().fromJson(jsonString, QuranData::class.java)
            allSurahs = quranData.surahs

            if (allSurahs.isNotEmpty()) {
                setupSurahListView()
                displaySurah(allSurahs[selectedSurahIndex])
            } else {
                showError("Нет данных о Коране")
            }

        } catch (e: Exception) {
            e.printStackTrace()
            showError("Ошибка загрузки данных")
        } finally {
            progressBar.visibility = View.GONE
        }
    }

    private fun setupSurahListView() {
        val listAdapter = SurahListAdapter(requireContext(), allSurahs) { selectedSurah ->
            selectedSurahIndex = allSurahs.indexOf(selectedSurah)
            displaySurah(selectedSurah)
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        surahListView.adapter = listAdapter
    }

    private fun displaySurah(surah: Surah) {
        activity?.findViewById<Toolbar>(R.id.toolbar)?.visibility = View.VISIBLE
        updateSurahHeader(surah)
        adapter = QuranAdapter(surah.verses)
        recyclerView.adapter = adapter
        recyclerView.scrollToPosition(0) // скроллим вверх к первому аяту
    }

    private fun updateSurahHeader(surah: Surah) {
        surahTitle.text = "${surah.number}. ${surah.name}"
        surahInfo.text = "${surah.type} • ${surah.verses.size} Аят"
        paraInfo.text = "Пара 27 • ¼ Hizb 54"
    }

    private fun showError(message: String) {
        errorText.text = message
        errorText.visibility = View.VISIBLE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selected_surah_index", selectedSurahIndex)

        //new commit
    }
}
