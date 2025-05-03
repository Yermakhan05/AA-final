package com.example.myfaith.view.adapter
import com.example.myfaith.model.entity.Notification
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfaith.R
import android.view.LayoutInflater
import android.view.View

class NotificationAdapter(
    private val notifications: List<Notification>,
    private val onShowClick: (Notification) -> Unit
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    inner class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.notification_title)
        private val messageTextView: TextView = itemView.findViewById(R.id.notification_message)
        private val showButton: TextView = itemView.findViewById(R.id.show_button)

        fun bind(notification: Notification) {
            titleTextView.text = notification.title
            messageTextView.text = notification.message.take(20) + "..."  // Shortened preview
            showButton.setOnClickListener { onShowClick(notification) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_item, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position])
    }

    override fun getItemCount() = notifications.size
}
