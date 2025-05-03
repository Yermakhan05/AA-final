package com.example.domain.repository

import com.example.domain.entity.Lecture

interface LectureRepository {
    suspend fun getLectures(): List<Lecture>
}
