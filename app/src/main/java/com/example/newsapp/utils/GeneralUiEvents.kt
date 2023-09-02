package com.example.newsapp.utils

sealed class GeneralUiEvents<out T> {

    object Loading : GeneralUiEvents<Nothing>()
    data class Error(val errorMessage: String) : GeneralUiEvents<Nothing>()
    data class Success<out T>(val data: T) : GeneralUiEvents<T>()
}