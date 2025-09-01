package com.example.islamicapp.api

import com.example.islamicapp.apiclient.Quran

interface QuranApiService {
    suspend fun fetchAllSurahs(): List<Quran>
}
