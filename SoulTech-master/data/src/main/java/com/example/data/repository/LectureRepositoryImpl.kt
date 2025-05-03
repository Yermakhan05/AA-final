package com.example.data.repository


import com.example.domain.entity.Lecture
import com.example.domain.repository.LectureRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LectureRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : LectureRepository {

    override suspend fun getLectures(): List<Lecture> {
        val result = firestore.collection("lessons").get().await()
        return result.documents.mapNotNull { it.toObject(Lecture::class.java) }
    }
}

