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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsListAdapter
import com.example.newsapp.data.models.Article
import com.example.newsapp.databinding.FragmentGeneralBinding
import com.example.newsapp.utils.GeneralUiEvents
import com.example.newsapp.utils.NetworkStatus
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GeneralFragment : Fragment() {

    private lateinit var bindingImpl: FragmentGeneralBinding
    private lateinit var newsAdapter: NewsListAdapter
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
        initNetworkObserver()
    }

    /**
     * Observing events and reacting upon based on the behavior
     */
    private fun initUiEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                generalViewModel.stateFlow.collectLatest { response ->
                    when (response) {
                        is GeneralUiEvents.Loading -> bindingImpl.shimmerLayout.startShimmer()
                        is GeneralUiEvents.Error -> Toast.makeText(
                            context, response.errorMessage, Toast.LENGTH_SHORT
                        ).show()

                        is GeneralUiEvents.Success -> {
                            bindingImpl.apply {
                                shimmerLayout.apply {
                                    visibility = View.GONE
                                    stopShimmer()
                                }
                                handleData(response.data.articles)
                                recycle.apply {
                                    visibility = View.VISIBLE
                                    adapter = newsAdapter
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    private fun initNetworkObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                generalViewModel.sharedFlow.collectLatest { response ->
                    when (response) {
                        is NetworkStatus.isAvailable -> showSnackBar(response.value)
                    }
                }
            }
        }
    }

    private fun showSnackBar(value: Boolean) {
        val snackBar = Snackbar.make(requireView().rootView, "No internet", Snackbar.LENGTH_SHORT)
        if (value) {
            snackBar.show()
        } else {
            snackBar.dismiss()
        }
    }

    /**
     * Setting data inside recycler view
     */
    private fun handleData(data: List<Article>) {
        initRecyclerView()
        newsAdapter = NewsListAdapter(data) {
            findNavController().navigate(R.id.action_general_fragment_to_sports_fragment)
        }

        newsAdapter.submitList(data)
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
}