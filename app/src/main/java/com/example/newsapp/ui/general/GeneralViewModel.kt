package com.example.newsapp.ui.general

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.DataRepository
import com.example.newsapp.data.models.HeadlineResponse
import com.example.newsapp.utils.GeneralUiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeneralViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {

    val articleData = dataRepository
        .getNewsWithFlows("general", "us")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), GeneralUiEvents.Loading)
}