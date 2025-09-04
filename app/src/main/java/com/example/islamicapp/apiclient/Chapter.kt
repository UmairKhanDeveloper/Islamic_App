package com.example.islamicapp.apiclient

import kotlinx.serialization.Serializable

@Serializable
data class Chapter(
    val bookSlug: String? = null,
    val chapterArabic: String? = null,
    val chapterEnglish: String? = null,
    val chapterNumber: String? = null,
    val chapterUrdu: String? = null,
    val id: Int? = null
)