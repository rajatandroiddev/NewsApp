package com.example.newsapp.ui.general

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.DataRepository
import com.example.newsapp.data.models.HeadlineResponse
import com.example.newsapp.utils.GeneralUiEvents
import com.example.newsapp.utils.NetworkConnectivity
import com.example.newsapp.utils.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeneralViewModel @Inject constructor(
    private val dataRepository: DataRepository, private val network: NetworkConnectivity
) : ViewModel() {

    private val _sharedFlow = MutableSharedFlow<NetworkStatus>(replay = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    private val _stateFlow =
        MutableStateFlow<GeneralUiEvents<HeadlineResponse>>(GeneralUiEvents.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    /**
     * Calling repository to fetch news based on category and country
     */
    init {
        getNws()
    }

    private fun getNws() {
        if (!network.isConnected()) {
            toggleNetworkError(check = true)
        } else {
            toggleNetworkError(check = false)
            viewModelScope.launch(context = Dispatchers.IO) {
                dataRepository.getNewsWithFlows("general", "us").collectLatest {
                    _stateFlow.value = it
                }
            }

        }

    }

    private fun toggleNetworkError(check: Boolean) {
        _sharedFlow.tryEmit(
            NetworkStatus.isAvailable(check)
        )
    }
}