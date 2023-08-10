package com.example.newsapp.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Article(
    @SerializedName("source")
    val source: Source,
    @SerializedName("author")
    val author: @RawValue Any,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("urlToImage")
    val urlToImage: String,

    @SerializedName("publishedAt")
    val publishedAt: String,

    @SerializedName("content")
    val content: @RawValue Any
) : Parcelable
