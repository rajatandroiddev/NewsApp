package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsapp.R
import com.example.newsapp.data.models.Article
import com.example.newsapp.databinding.NewsLayoutBinding

class NewsListAdapter(
    private val article: List<Article>, private val onclick: (text: String) -> Unit
) : ListAdapter<Article, NewsListAdapter.NewsViewHolder>(NewsDiffCallBack) {

    inner class NewsViewHolder(val binding: NewsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.news = article
            binding.textAuthor.text = article.source.name
            binding.imageView.setOnClickListener { onclick.invoke(article.title) }
        }
    }

    object NewsDiffCallBack : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(
            oldItem: Article, newItem: Article
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Article, newItem: Article
        ): Boolean {
            return oldItem == newItem
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.news_layout, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.imageView.load(article[position].urlToImage)
        holder.bind(article[position])
    }
}
