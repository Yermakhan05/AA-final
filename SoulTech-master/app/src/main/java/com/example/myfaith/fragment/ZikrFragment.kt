package com.example.myfaith.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mynavigationapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class ZikrFragment : Fragment() {
    private var counter = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_zikr, container, false)

        val counterView = view.findViewById<TextView>(R.id.zikr_counter)
        val addButton = view.findViewById<Button>(R.id.zikr_add_button)
        val resetButton = view.findViewById<Button>(R.id.zikr_reset_button)
        val minusButton = view.findViewById<Button>(R.id.zikr_minus_button)

        addButton.setOnClickListener {
            counter++
            counterView.text = counter.toString()
        }

        minusButton.setOnClickListener {
            if (counter > 0) {
                counter--
                counterView.text = counter.toString()
            }
        }

        resetButton.setOnClickListener {
            counter = 0
            counterView.text = counter.toString()
        }

        return view
    }
    override fun onResume() {
        super.onResume()
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.visibility = View.GONE
    }
}
