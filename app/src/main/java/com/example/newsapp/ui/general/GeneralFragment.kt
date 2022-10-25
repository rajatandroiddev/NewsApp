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
import com.example.newsapp.databinding.FragmentGeneralBinding
import com.example.newsapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeneralFragment : Fragment() {

    private lateinit var bindingImpl: FragmentGeneralBinding
    private lateinit var newsAdapter: NewsAdapter
    private val generalViewModel: GeneralViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingImpl = DataBindingUtil
            .inflate(inflater, R.layout.fragment_general, container, false)
        bindingImpl.apply {
            recycle.layoutManager = LinearLayoutManager(context)
            recycle.hasFixedSize()
        }
        generalViewModel.generalNewsList()



        generalViewModel.generalNews.observe(this, { response ->
            when (response) {
                is Resource.Success -> {
                    bindingImpl.recycle.visibility = View.VISIBLE
                    bindingImpl.shimmerLayout.stopShimmer()
                    bindingImpl.shimmerLayout.visibility = View.GONE
                    newsAdapter = NewsAdapter(response.data?.articles)
                    bindingImpl.recycle.adapter = newsAdapter
                }

                is Resource.Loading -> bindingImpl.shimmerLayout.startShimmer()
                is Resource.Error -> Toast.makeText(context, response.message, Toast.LENGTH_SHORT)
                    .show()

            }
        })

        return bindingImpl.root
    }
}