package com.example.myfaith.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.domain.repository.LectureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LessonViewModel @Inject constructor(
    private val lectureRepository: LectureRepository
) : ViewModel() {

    val lectures = liveData {
        try {
            emit(lectureRepository.getLectures())
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}
