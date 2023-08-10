package com.example.newsapp.ui.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.FragmentGeneralBinding
import com.example.newsapp.utils.GeneralUiEvents
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GeneralFragment : Fragment() {

    private lateinit var binding: FragmentGeneralBinding
    private val generalViewModel by viewModels<GeneralViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentGeneralBinding.inflate(inflater, container, false)
        return binding.root
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
                generalViewModel.articleData.collectLatest { response ->
                    when (response) {
                        is GeneralUiEvents.Loading -> {
                            binding.shimmerLayout.startShimmer()
                        }

                        is GeneralUiEvents.Error -> {
                            Toast.makeText(
                                context, response.errorMessage, Toast.LENGTH_SHORT
                            ).show()
                        }

                        is GeneralUiEvents.Success -> {
                            binding.apply {
                                shimmerLayout.visibility = View.GONE
                                shimmerLayout.stopShimmer()
                                val adapter = NewsAdapter(response.data.articles) {
                                    Snackbar.make(
                                        binding.root,
                                        it.description,
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                                recycle.adapter = adapter
                                recycle.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }
}