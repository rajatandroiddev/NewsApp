package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.models.Article
import com.example.newsapp.databinding.NewsLayoutBinding
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class NewsAdapter @Inject constructor(
    private val article: List<Article>?,
    private val onItemClicked: OnItemClicked
) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private lateinit var newsBinding: NewsLayoutBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        newsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.news_layout,
            parent,
            false
        );
        return NewsViewHolder(newsBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(article?.get(position)?.urlToImage)
            .into(newsBinding.imageView);

        article?.get(position)?.let { holder.bind(it, onItemClicked) }

    }

    override fun getItemCount(): Int {
        return article!!.size
    }


    inner class NewsViewHolder(private var binding: NewsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bestArticle: Article, itemClicked: OnItemClicked) {
            binding.news = bestArticle
            binding.textAuthor.text = bestArticle.source.name
            binding.onClickListener = itemClicked
        }
    }
}