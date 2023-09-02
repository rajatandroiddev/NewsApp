package com.example.newsapp.data

import com.squareup.moshi.Json
import kotlinx.android.parcel.RawValue

data class Source(
    @Json(name = "id") val id: Any, @Json(name = "name") val name: String
)
