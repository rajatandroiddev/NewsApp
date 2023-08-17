package com.example.newsapp.utils

sealed class NetworkStatus {

    data class isAvailable(val value: Boolean) : NetworkStatus()
}
