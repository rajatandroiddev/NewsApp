package com.example.newsapp.data

import com.example.newsapp.data.models.HeadlineResponse
import com.example.newsapp.utils.GeneralUiEvents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class DataRepository @Inject constructor(private val network: Network) {

    fun getNewsWithFlows(
        category: String,
        country: String
    ): Flow<GeneralUiEvents<HeadlineResponse>> = flow {
        emit(GeneralUiEvents.Loading)
        try {
            emit(GeneralUiEvents.Success(network.getHeadlines(category, country)))
        } catch (e: Exception) {
            emit(GeneralUiEvents.Error(e.toString()))
        }
    }
}