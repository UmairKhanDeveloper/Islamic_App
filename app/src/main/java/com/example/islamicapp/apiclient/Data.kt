package com.example.islamicapp.apiclient

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val book: Book? = null,
    val bookSlug: String? = null,
    val chapter: Chapter? = null,
    val chapterId: String? = null,
    val englishNarrator: String? = null,
    val hadithArabic: String? = null,
    val hadithEnglish: String? = null,
    val hadithNumber: String? = null,
    val hadithUrdu: String? = null,
    val headingArabic: String? = null,
    val headingEnglish: String? = null,
    val headingUrdu: String? = null,
    val id: Int? = null,
    val status: String? = null,
    val urduNarrator: String? = null,
    val volume: String? = null
)
