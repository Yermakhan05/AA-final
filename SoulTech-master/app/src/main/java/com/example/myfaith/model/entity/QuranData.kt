package com.example.myfaith.model.entity

import androidx.navigation.NavType

data class Verse(
    val number: Int,
    val arabicText: String,
    val transliteration: String,
    val translation: String,
    val highlights: Map<String, String>
)

data class Surah(
    val name: String,
    val number: Int,
    val type: String,
    val verses: List<Verse>
)

data class QuranData(val surahs: List<Surah>)
