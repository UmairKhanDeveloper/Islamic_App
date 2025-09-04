package com.example.islamicapp.apiclient

import kotlinx.serialization.Serializable

@Serializable
data class Hadiths(
    val hadiths: HadithsX? = null,
    val message: String? = null,
    val status: Int? = null
)