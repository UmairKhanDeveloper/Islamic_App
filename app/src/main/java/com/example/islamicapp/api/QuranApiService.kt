package com.example.islamicapp.api

import com.example.islamicapp.apiclient.Audio
import com.example.islamicapp.apiclient.Hadiths
import com.example.islamicapp.apiclient.Quran

interface ApiService {
    suspend fun fetchAllSurahs(): List<Quran>
    suspend fun fetchHadiths(): Hadiths

    suspend fun fetchSurahAudio(surahId: Int): Audio
}