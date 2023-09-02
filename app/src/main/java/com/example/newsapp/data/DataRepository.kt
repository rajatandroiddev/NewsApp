package com.example.newsapp.data

import com.example.newsapp.data.models.HeadlineResponse
import com.example.newsapp.utils.GeneralUiEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import retrofit2.Response
import javax.inject.Inject

class DataRepository @Inject constructor(private val network: Network) {

    companion object {
        fun generateErrorMessage(result: Response<HeadlineResponse>) =
            "Code: ${result.code()} - Error: ${result.message()} - ${result.errorBody()}"
    }

    /**
     * Calling api for fetching news
     */
    fun getNewsWithFlows(
        category: String, country: String
    ): Flow<GeneralUiEvents<HeadlineResponse>> = flow {
        emit(GeneralUiEvents.Loading)
        val result = network.getHeadlines(category, country)
        if (result.isSuccessful) {
            val responseBody = result.body()
            if (responseBody != null) {
                emit(GeneralUiEvents.Success(responseBody))
            } else {
                emit(GeneralUiEvents.Error("Response body is null"))
            }
        } else {
            emit(GeneralUiEvents.Error(generateErrorMessage(result)))
        }
    }.catch {
            emit(GeneralUiEvents.Error(it.toString()))
        }.flowOn(Dispatchers.IO)

}