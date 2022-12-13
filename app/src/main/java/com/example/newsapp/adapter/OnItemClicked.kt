package com.example.newsapp.adapter

import com.example.newsapp.data.models.Article

interface OnItemClicked {

    fun clickItem(article: Article)
}