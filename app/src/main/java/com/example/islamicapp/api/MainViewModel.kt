package com.example.islamicapp.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.islamicapp.apiclient.Quran
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository = Repository()
) : ViewModel() {

    private val _allQuran = MutableStateFlow<ResultState<List<Quran>>>(ResultState.Loading)
    val allQuran: StateFlow<ResultState<List<Quran>>> = _allQuran.asStateFlow()

    fun loadAllSurahs() {
        viewModelScope.launch {
            _allQuran.value = ResultState.Loading
            try {
                val response = repository.fetchAllSurahs()
                _allQuran.value = ResultState.Succses(response)
            } catch (e: Exception) {
                _allQuran.value = ResultState.Error(e)
            }
        }
    }
}
