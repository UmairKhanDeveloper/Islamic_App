package com.example.islamicapp.apiclient

import kotlinx.serialization.Serializable

@Serializable
data class X1X(
    val originalUrl: String,
    val reciter: String,
    val url: String
)