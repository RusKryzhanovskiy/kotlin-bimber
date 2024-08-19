package com.example.bimber.ui.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bimber.data.repository.GifRepository
import com.example.bimber.data.remote.RetrofitInstance

class TrendingViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = GifRepository(RetrofitInstance.api)
        return TrendingViewModel(repository) as T
    }
}
