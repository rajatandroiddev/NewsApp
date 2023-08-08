package com.example.newsapp.ui.general

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.DataRepository
import com.example.newsapp.data.models.HeadlineResponse
import com.example.newsapp.utils.GeneralUiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeneralViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {

    private var _events = MutableStateFlow<GeneralUiEvents<HeadlineResponse>?>(GeneralUiEvents.Loading)
    val events = _events.asStateFlow()

    /**
     * Calling repository to fetch news based on category and country
     */
    fun generalNewsListWithFlow() {
        viewModelScope.launch {
            dataRepository.getNewsWithFlows("general", "us").collect {
                when (it) {
                    is GeneralUiEvents.Loading -> _events.value = GeneralUiEvents.Loading
                    is GeneralUiEvents.Success -> _events.value = GeneralUiEvents.Success(it.data)
                    is GeneralUiEvents.Error -> _events.emit(GeneralUiEvents.Error(it.errorMessage))
                }
            }
        }
    }
}