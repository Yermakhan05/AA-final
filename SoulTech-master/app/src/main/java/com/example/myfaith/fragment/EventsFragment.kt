package com.example.myfaith.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfaith.adapter.HolidayAdapter
import com.example.mynavigationapp.databinding.FragmentEventsBinding
import com.example.myfaith.entity.Holiday
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class EventsFragment : Fragment() {

    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)

        val holidays = loadHolidaysFromAssets()
        val adapter = HolidayAdapter(holidays)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    private fun loadHolidaysFromAssets(): List<Holiday> {
        val inputStream = requireContext().assets.open("islamic_holidays_2025.json")
        val json = inputStream.bufferedReader().use { it.readText() }

        val type = object : TypeToken<List<Holiday>>() {}.type
        return Gson().fromJson(json, type)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}