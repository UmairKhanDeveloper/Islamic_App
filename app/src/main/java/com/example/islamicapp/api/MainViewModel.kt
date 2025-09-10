package com.example.islamicapp.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.islamicapp.apiclient.Audio
import com.example.islamicapp.apiclient.Hadiths
import com.example.islamicapp.apiclient.Quran
import com.example.islamicapp.db.MostRecently
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _allQuran = MutableStateFlow<ResultState<List<Quran>>>(ResultState.Loading)
    val allQuran: StateFlow<ResultState<List<Quran>>> = _allQuran.asStateFlow()

    private val _allHadiths = MutableStateFlow<ResultState<Hadiths>>(ResultState.Loading)
    val allHadiths: StateFlow<ResultState<Hadiths>> = _allHadiths.asStateFlow()

    private val _surahAudio = MutableStateFlow<ResultState<Audio>>(ResultState.Loading)
    val surahAudio: StateFlow<ResultState<Audio>> = _surahAudio.asStateFlow()

    val allMostRecently: LiveData<List<MostRecently>> = repository.getAllMostRecently()

    fun loadAllSurahs() {
        viewModelScope.launch(Dispatchers.IO) {
            _allQuran.value = ResultState.Loading
            try {
                val response = repository.fetchAllSurahs()
                _allQuran.value = ResultState.Succses(response)
            } catch (e: Exception) {
                _allQuran.value = ResultState.Error(e)
            }
        }
    }

    fun loadAllHadiths() {
        viewModelScope.launch(Dispatchers.IO) {
            _allHadiths.value = ResultState.Loading
            try {
                val response = repository.fetchHadiths()
                _allHadiths.value = ResultState.Succses(response)
            } catch (e: Exception) {
                _allHadiths.value = ResultState.Error(e)
            }
        }
    }

    // 🔥 New Function for Surah Audio
    fun loadSurahAudio(surahId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _surahAudio.value = ResultState.Loading
            try {
                val response = repository.fetchSurahAudio(surahId)
                _surahAudio.value = ResultState.Succses(response)
            } catch (e: Exception) {
                _surahAudio.value = ResultState.Error(e)
            }
        }
    }

    fun insertMostRecently(mostRecently: MostRecently) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(mostRecently)
        }
    }

    fun updateMostRecently(mostRecently: MostRecently) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(mostRecently)
        }
    }

    fun deleteMostRecently(mostRecently: MostRecently) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(mostRecently)
        }
    }
}