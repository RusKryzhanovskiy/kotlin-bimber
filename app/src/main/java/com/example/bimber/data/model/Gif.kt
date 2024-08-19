package com.example.bimber.data.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

data class Gif(
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
    @SerializedName("title") val title: String,
    @SerializedName("images") val images: GifImages
)

data class GifImages(
    @SerializedName("original") val original: GifImage
)

data class GifImage(
    @SerializedName("url") val url: String
)

fun serializeGif(gif: Gif): String {
    val gson = Gson()
    return URLEncoder.encode(gson.toJson(gif), StandardCharsets.UTF_8.toString())
}

fun deserializeGif(json: String): Gif {
    val gson = Gson()
    val gifJson = URLDecoder.decode(json, StandardCharsets.UTF_8.toString())
    return gson.fromJson(gifJson, Gif::class.java)
}