package com.example.bimber.data.repository

import com.example.bimber.data.model.Gif
import com.example.bimber.data.services.GiphyApiService
import com.example.bimber.data.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GifRepository(private val apiService: GiphyApiService) {
    suspend fun getTrendingGifs(): Resource<List<Gif>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getTrendingGifs()
                if (response.isSuccessful) {
                    response.body()?.let {
                        Resource.Success(it.gifs)
                    } ?: Resource.Error("An unknown error occurred")
                } else {
                    Resource.Error(response.message())
                }
            } catch (e: Exception) {
                Resource.Error("Couldn't reach the server. Check your internet connection.")
            }
        }
    }

    suspend fun searchGifs(query: String): Resource<List<Gif>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.searchGifs(query)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Resource.Success(it.gifs)
                    } ?: Resource.Error("An unknown error occurred")
                } else {
                    Resource.Error(response.message())
                }
            } catch (e: Exception) {
                Resource.Error("Couldn't reach the server. Check your internet connection.")
            }
        }
    }
}
