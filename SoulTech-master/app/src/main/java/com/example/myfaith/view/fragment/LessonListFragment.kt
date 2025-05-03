package com.example.myfaith.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfaith.R
import com.example.myfaith.view.adapter.LectureAdapter
import com.example.myfaith.viewmodel.LessonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LessonListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val viewModel: LessonViewModel by viewModels()

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

        lifecycleScope.launch {
            viewModel.lectures.observe(viewLifecycleOwner) { lectures ->
                recyclerView.adapter = LectureAdapter(lectures) { lecture ->
                    val videoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(lecture.videoUrl))
                    startActivity(videoIntent)
                }
            }
        }
    }
}


