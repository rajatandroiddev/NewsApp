package com.example.newsapp.data

import com.example.newsapp.BuildConfig
import com.example.newsapp.data.models.HeadlineResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Network {

    @Headers("X-Api-Key:" + BuildConfig.NewsApiKey)
    @GET("/v2/top-headlines")
    suspend fun getHeadlines(
        @Query("category") category: String?,
        @Query("country") country: String?
    ): Response<HeadlineResponse?>
}