package com.example.myfaith.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfaith.model.entity.Notification
import com.example.myfaith.view.adapter.NotificationAdapter
import com.example.myfaith.R

class NotificationFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificationAdapter
    private val notifications = listOf( // Sample notifications
        Notification("Prayer Reminder", "Time for Asr prayer."),
        Notification("Quran Reading", "Don't forget your daily Quran reading."),
        Notification("New Quote", "Check out today's inspirational quote.")
        // Add more notifications
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.notification_fragment, container, false)

        recyclerView = view.findViewById(R.id.notification_recyclerview) // Correct ID
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = NotificationAdapter(notifications) { notification ->
            // Handle show button click (e.g., show full message in a dialog)
            AlertDialog.Builder(requireContext())
                .setTitle(notification.title)
                .setMessage(notification.message)
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
        }
        recyclerView.adapter = adapter

        return view
    }
}