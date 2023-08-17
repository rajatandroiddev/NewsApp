package com.example.newsapp.ui.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.adapter.OnItemClicked
import com.example.newsapp.data.models.Article
import com.example.newsapp.databinding.FragmentGeneralBinding
import com.example.newsapp.utils.GeneralUiEvents
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GeneralFragment : Fragment(), OnItemClicked {

    private lateinit var bindingImpl: FragmentGeneralBinding
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var generalViewModel: GeneralViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bindingImpl = DataBindingUtil.inflate(inflater, R.layout.fragment_general, container, false)
        generalViewModel = ViewModelProvider(this)[GeneralViewModel::class.java]
        return bindingImpl.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUiEvents()
    }

    /**
     * Observing events and reacting upon based on the behavior
     */
    private fun initUiEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                generalViewModel.getNewsFromApi().collectLatest { response ->
                    when (response) {
                        is GeneralUiEvents.Loading -> bindingImpl.shimmerLayout.startShimmer()
                        is GeneralUiEvents.Error -> Toast.makeText(
                            context, response.errorMessage, Toast.LENGTH_SHORT
                        ).show()

                        is GeneralUiEvents.Success -> {
                            bindingImpl.apply {
                                recycle.apply {
                                    visibility = View.VISIBLE
                                    adapter = newsAdapter
                                }
                                shimmerLayout.apply {
                                    visibility = View.GONE
                                    stopShimmer()
                                }
                                handleData(response.data.articles)
                            }

                        }
                    }

                }
            }
        }
    }

    /**
     * Setting data inside recycler view
     */
    private fun handleData(data: List<Article>) {
        initRecyclerView()
        newsAdapter = NewsAdapter(data, this)
        newsAdapter.notifyDataSetChanged()
    }

    /**
     * Initializing recycler view
     */
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