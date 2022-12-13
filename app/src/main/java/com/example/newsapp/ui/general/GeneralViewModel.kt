package com.example.newsapp.ui.general

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.DataRepository
import com.example.newsapp.data.models.HeadlineResponse
import com.example.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeneralViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {

    private var _generalNews = MutableLiveData<Resource<HeadlineResponse?>>()
    val generalNews: LiveData<Resource<HeadlineResponse?>> get() = _generalNews


    fun generalNewsList() {
        viewModelScope.launch {
            dataRepository.getNews("general", "us").collect { _generalNews.value = it }
        }
    }

}