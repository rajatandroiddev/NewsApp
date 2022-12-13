package com.example.newsapp.data

import com.example.newsapp.data.models.HeadlineResponse
import com.example.newsapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class DataRepository @Inject constructor(private val network: Network) {

    suspend fun getNews(catgry: String, country: String): Flow<Resource<HeadlineResponse?>> = flow {
        emit(Resource.Loading())
        try {
            val result = network.getHeadlines(catgry, country)
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()))
            } else {
                emit(Resource.Error(generateErrorMessage(result)))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unknown error occurred..."))
        }
    }

    companion object {
        fun generateErrorMessage(result: Response<HeadlineResponse>) =
            "Code: ${result.code()} - Error: ${result.message()} - ${result.errorBody()}"
    }
}