package com.example.bimber.ui.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bimber.data.model.Gif
import com.example.bimber.data.repository.GifRepository
import com.example.bimber.data.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(private val gifRepository: GifRepository) : ViewModel() {
    var searchResults = mutableStateOf<Resource<List<Gif>>>(Resource.Loading())

    private var debounceJob: Job? = null

    fun searchGifs(query: String) {
        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(500)
            if (query.isNotBlank()) {
                searchResults.value = Resource.Loading()
                val result = gifRepository.searchGifs(query)
                searchResults.value = result
            } else {
                searchResults.value = Resource.Success(emptyList())
            }
        }
    }
}
