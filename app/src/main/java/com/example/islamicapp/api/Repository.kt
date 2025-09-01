package com.example.islamicapp.api

import com.example.islamicapp.apiclient.Quran


class Repository : QuranApiService {
    override suspend fun fetchAllSurahs(): List<Quran> {
        return QuranApiClient.fetchAllSurahs()
    }
}
