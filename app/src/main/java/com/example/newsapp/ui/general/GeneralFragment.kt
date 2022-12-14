package com.example.newsapp.ui.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.adapter.OnItemClicked
import com.example.newsapp.data.models.Article
import com.example.newsapp.data.models.HeadlineResponse
import com.example.newsapp.databinding.FragmentGeneralBinding
import com.example.newsapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeneralFragment : Fragment(), OnItemClicked {

    private lateinit var bindingImpl: FragmentGeneralBinding
    private lateinit var newsAdapter: NewsAdapter
    private val generalViewModel: GeneralViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingImpl = DataBindingUtil
            .inflate(inflater, R.layout.fragment_general, container, false)
        generalViewModel.generalNewsList()
        getData()

        return bindingImpl.root
    }

    private fun getData() {
        generalViewModel.generalNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    bindingImpl.recycle.visibility = View.VISIBLE
                    bindingImpl.shimmerLayout.stopShimmer()
                    bindingImpl.shimmerLayout.visibility = View.GONE
                    handleData(response.data?.articles)
                    bindingImpl.recycle.adapter = newsAdapter
                }
                is Resource.Loading -> bindingImpl.shimmerLayout.startShimmer()
                is Resource.Error -> Toast.makeText(context, response.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun handleData(data: List<Article>?) {
        initRecyclerView()
        newsAdapter = NewsAdapter(data, this)
        newsAdapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        bindingImpl.apply {
            recycle.layoutManager = LinearLayoutManager(context)
            recycle.hasFixedSize()
        }
    }

    override fun clickItem(article: Article) {
        Toast.makeText(activity, article.description, Toast.LENGTH_LONG).show()

    }
}