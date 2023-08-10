package com.example.newsapp.data.models

data class HeadlineResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
