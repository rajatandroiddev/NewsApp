package com.example.newsapp.ui.general

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.DataRepository
import com.example.newsapp.data.models.HeadlineResponse
import com.example.newsapp.utils.GeneralUiEvents
import com.example.newsapp.utils.Network
import com.example.newsapp.utils.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GeneralViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val network: Network
) :
    ViewModel() {

    private val sharedFlow = MutableSharedFlow<NetworkStatus>()
    val _sharedFlow = sharedFlow.asSharedFlow()

    /**
     * Calling repository to fetch news based on category and country
     */
    fun getNewsFromApi() {
        if (network.isConnected()) {
            toggleNetworkError(check = true)
        }

            dataRepository.getNewsWithFlows("general", "us").stateIn(
                scope = viewModelScope,
                started = WhileSubscribed(5000), initialValue = GeneralUiEvents.Loading)
        toggleNetworkError(check = false)

    }

    private fun toggleNetworkError(check: Boolean) {
        sharedFlow.tryEmit(
            NetworkStatus.isAvailable(value = check)
        )
    }



}