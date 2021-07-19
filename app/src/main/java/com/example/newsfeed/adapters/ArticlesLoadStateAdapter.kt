package com.example.newsfeed.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.databinding.NewsScreenLoadStateFooterBinding

class ArticlesLoadStateAdapter(
    private val onRetryButtonClicked: () -> Unit
): LoadStateAdapter<ArticlesLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = NewsScreenLoadStateFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)

        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


    inner class LoadStateViewHolder(private val binding: NewsScreenLoadStateFooterBinding):
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener {
                onRetryButtonClicked.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState is LoadState.Error
                errorMessageText.isVisible = loadState is LoadState.Error
            }
        }
    }
}
