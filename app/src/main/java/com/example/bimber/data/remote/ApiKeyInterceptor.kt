package com.example.bimber.data.remote

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // Get the original request
        val originalRequest = chain.request()

        // Get the original URL
        val originalUrl = originalRequest.url

        // Modify the URL to include the API key as a query parameter
        val newUrl: HttpUrl = originalUrl.newBuilder()
            .addQueryParameter("api_key", "rO3pj0Wn3VSweN6OjIbwoqPvuD3x7Pfm")
            .build()

        // Create a new request with the modified URL
        val newRequest: Request = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        // Proceed with the modified request
        return chain.proceed(newRequest)
    }
}
