package com.example.newsapp.data.models

data class HeadlineResponse(
    val status: String? = null,
    val totalResults: Int = 0,
    val articles: List<Article>
)
