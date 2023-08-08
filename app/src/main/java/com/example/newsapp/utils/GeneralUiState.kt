package com.example.newsapp.utils

import com.example.newsapp.data.models.HeadlineResponse

sealed class GeneralUiState{

    data class showRecyclerView(val data: HeadlineResponse?) : GeneralUiState()

}
