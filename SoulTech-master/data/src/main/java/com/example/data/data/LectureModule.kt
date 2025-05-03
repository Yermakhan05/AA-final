package com.example.data.data

import com.example.data.repository.LectureRepositoryImpl
import com.example.domain.repository.LectureRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LectureModule {

    @Binds
    @Singleton
    abstract fun bindLectureRepository(
        impl: LectureRepositoryImpl
    ): LectureRepository
}
