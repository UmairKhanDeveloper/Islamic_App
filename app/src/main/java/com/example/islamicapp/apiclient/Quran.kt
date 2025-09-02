package com.example.islamicapp.apiclient

import com.example.islamicapp.screen.X1
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
    val verses: Map<String, X1>
)
