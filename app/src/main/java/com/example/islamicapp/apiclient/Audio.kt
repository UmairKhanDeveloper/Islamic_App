package com.example.islamicapp.apiclient

import kotlinx.serialization.Serializable

@Serializable
data class Audio(
    val `1`: X1X,
    val `2`:X1X,
    val `3`: X1X,
    val `4`: X1X,
    val `5`: X1X,
) {
    fun toList(): List<X1X> {
        return listOf(`1`, `2`, `3`, `4`, `5`)
    }
}