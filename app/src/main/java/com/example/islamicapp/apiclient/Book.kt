package com.example.islamicapp.apiclient

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val aboutWriter: String? = null,
    val bookName: String? = null,
    val bookSlug: String? = null,
    val id: Int? = null,
    val writerDeath: String? = null,
    val writerName: String? = null
)