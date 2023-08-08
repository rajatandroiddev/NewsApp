package com.example.newsapp.data

import com.example.newsapp.data.models.HeadlineResponse
import com.example.newsapp.utils.GeneralUiEvents
import com.example.newsapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
        catgry: String,
        country: String
    ): Flow<GeneralUiEvents<HeadlineResponse?>> =
        flow {
            emit(GeneralUiEvents.Loading)
            val result = network.getHeadlines(catgry, country)
            if (result.isSuccessful) {
                emit(GeneralUiEvents.Success(result.body()))
            } else {
                emit(GeneralUiEvents.Error(generateErrorMessage(result)))
            }
        }.catch {
            emit(GeneralUiEvents.Error(it.toString() ?: "An unknown error occurred..."))

        }.flowOn(Dispatchers.IO)


}