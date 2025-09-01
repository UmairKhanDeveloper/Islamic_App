package com.example.islamicapp.apiclient

import kotlinx.serialization.Serializable

@Serializable
data class X1(
    val content: String,
    val id: Double,
    val translation_eng: String,
    val transliteration: String
)