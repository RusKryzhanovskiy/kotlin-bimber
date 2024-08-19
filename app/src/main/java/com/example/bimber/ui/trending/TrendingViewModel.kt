package com.example.bimber.ui.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bimber.data.model.Gif
import com.example.bimber.data.repository.GifRepository
import com.example.bimber.data.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TrendingViewModel(private val repository: GifRepository) : ViewModel() {

    private val _trendingGifs = MutableStateFlow<Resource<List<Gif>>>(Resource.Loading())
    val trendingGifs: StateFlow<Resource<List<Gif>>> = _trendingGifs

    fun fetchTrendingGifs() {
        viewModelScope.launch {
            _trendingGifs.value = repository.getTrendingGifs()
        }
    }
}
