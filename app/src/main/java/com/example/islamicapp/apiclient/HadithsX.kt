package com.example.islamicapp.apiclient

import kotlinx.serialization.Serializable

@Serializable
data class HadithsX(
    val current_page: Int? = null,
    val `data`: List<Data>? = null,
    val first_page_url: String? = null,
    val from: Int? = null,
    val last_page: Int? = null,
    val last_page_url: String? = null,
    val links: List<Link>? = null,
    val next_page_url: String? = null,
    val path: String? = null,
    val per_page: Int? = null,
    val prev_page_url: String? = null,
    val to: Int? = null,
    val total: Int? = null
)