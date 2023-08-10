package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsapp.data.models.Article
import com.example.newsapp.databinding.NewsLayoutBinding

class NewsAdapter(
    private val article: List<Article>,
    private val onClick: (Article) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            NewsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.imageViewNews.load(article[position].urlToImage)
        holder.bind(article[position])
    }

    override fun getItemCount() = article.size


    inner class NewsViewHolder(val binding: NewsLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.textViewNewsTitle.text = article.title
            binding.textViewNewsAuthor.text = article.source.name
            binding.root.setOnClickListener { onClick(article) }
        }
    }
}