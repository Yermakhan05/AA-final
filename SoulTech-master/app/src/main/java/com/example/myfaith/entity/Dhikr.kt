package com.example.myfaith.entity

data class Dhikr(
    val id: Int,
    val name: String,
    val arabicText: String,
    val translation: String,
    val benefits: String,
    var count: Int = 0
) 