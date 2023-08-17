package com.example.newsapp.ui.general

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.DataRepository
import com.example.newsapp.data.models.HeadlineResponse
import com.example.newsapp.utils.GeneralUiEvents
import com.example.newsapp.utils.Network
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GeneralViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val network: Network
) : ViewModel() {

    val networkAvailable = MutableSharedFlow<Boolean>()

    /**
     * Calling repository to fetch news based on category and country
     */
    fun getNewsFromApi(): StateFlow<GeneralUiEvents<HeadlineResponse>> {
        return dataRepository.getNewsWithFlows("general", "us").stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000), initialValue = GeneralUiEvents.Loading
        )
    }

    fun checkNetwork() {
        networkAvailable.tryEmit(network.isConnected())
    }
}