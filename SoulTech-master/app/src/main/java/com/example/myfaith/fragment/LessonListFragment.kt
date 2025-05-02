package com.example.myfaith.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynavigationapp.R
import com.example.myfaith.adapter.LectureAdapter
import com.example.myfaith.models.Lecture
import com.google.firebase.firestore.FirebaseFirestore

class LessonListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var lectureAdapter: LectureAdapter
    private val lectures = mutableListOf<Lecture>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_lesson_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewLectures)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lectureAdapter = LectureAdapter(lectures) { lecture ->
            val videoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(lecture.videoUrl))
            startActivity(videoIntent)
        }

        recyclerView.adapter = lectureAdapter
        loadLectures()
    }

    private fun loadLectures() {
        FirebaseFirestore.getInstance().collection("lessons")
            .get()
            .addOnSuccessListener { result ->
                lectures.clear()
                for (document in result) {
                    val lecture = document.toObject(Lecture::class.java)
                    lectures.add(lecture)
                }
                lectureAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    requireContext(),
                    "Қате: ${exception.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
