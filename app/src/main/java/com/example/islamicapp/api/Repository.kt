package com.example.islamicapp.api

import androidx.lifecycle.LiveData
import com.example.islamicapp.apiclient.Hadiths
import com.example.islamicapp.apiclient.Quran
import com.example.islamicapp.db.MostRecently
import com.example.islamicapp.db.MostRecentlyDataBase


class Repository(private val mostRecentlyDataBase: MostRecentlyDataBase) : ApiService {

    override suspend fun fetchAllSurahs(): List<Quran> {
        return QuranApiClient.fetchAllSurahs()
    }

    override suspend fun fetchHadiths(): Hadiths {
        return HadithApiClient.fetchHadiths()
    }

    fun getAllMostRecently(): LiveData<List<MostRecently>> =
        mostRecentlyDataBase.getDao().getAllMostRecently()

    suspend fun insert(mostRecently: MostRecently) {
        mostRecentlyDataBase.getDao().insert(mostRecently)
    }

    suspend fun update(mostRecently: MostRecently) {
        mostRecentlyDataBase.getDao().update(mostRecently)
    }

    suspend fun delete(mostRecently: MostRecently) {
        mostRecentlyDataBase.getDao().delete(mostRecently)
    }

}
