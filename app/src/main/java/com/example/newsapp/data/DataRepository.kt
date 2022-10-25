package com.example.newsapp.data

import javax.inject.Inject

class DataRepository @Inject constructor(
    private val network: Network,
) {


    suspend fun getNews(cat: String, coun: String) = network.getHeadlines(cat, coun)
}