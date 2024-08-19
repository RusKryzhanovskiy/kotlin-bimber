package com.example.bimber.data.services

import com.example.bimber.data.model.Gif
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApiService {
    @GET("/v1/gifs/trending")
    suspend fun getTrendingGifs(
        @Query("limit") limit: Int = 26,
        @Query("rating") rating: String = "r",
    ): Response<GifResponse>

    @GET("/v1/gifs/search")
    suspend fun searchGifs(
        @Query("q") query: String,
        @Query("limit") limit: Int = 26,
    ): Response<GifResponse>
}

data class GifResponse(
    @SerializedName("data") val gifs: List<Gif>
)
