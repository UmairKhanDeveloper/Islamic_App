package com.example.islamicapp.apiclient

import kotlinx.serialization.Serializable

@Serializable
data class Quran(
    val description: String,
    val id: Int,
    val surah_name: String,
    val surah_name_ar: String,
    val total_verses: Int,
    val translation: String,
    val type: String,
    val verses: Verses
)