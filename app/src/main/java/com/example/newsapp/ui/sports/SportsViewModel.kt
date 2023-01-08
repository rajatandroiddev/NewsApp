package com.example.newsapp.ui.sports

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
class SportsViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {

    private var _sportsNews = MutableLiveData<Resource<HeadlineResponse?>>()
    val sportsNews: LiveData<Resource<HeadlineResponse?>> get() = _sportsNews

    fun sportsNewsList() {
        viewModelScope.launch {
            dataRepository.getNews("sports", "us").collect { _sportsNews.value = it }
        }
    }
}