package com.example.islamicapp.apiclient

import kotlinx.serialization.Serializable

@Serializable
data class Link(
    val active: Boolean? = null,
    val label: String? = null,
    val url: String? = null
)