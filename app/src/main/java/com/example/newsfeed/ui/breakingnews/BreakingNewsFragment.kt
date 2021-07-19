package com.example.newsfeed.ui.breakingnews

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsfeed.R
import com.example.newsfeed.adapters.ArticlesLoadStateAdapter
import com.example.newsfeed.adapters.ArticlesPagingAdapter
import com.example.newsfeed.data.models.Article
import com.example.newsfeed.databinding.FragmentBreakingNewsScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news_screen), ArticlesPagingAdapter.OnItemClickListener {

    private val viewModel: BreakingNewsViewModel by viewModels()

    private var _binding: FragmentBreakingNewsScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArticlesPagingAdapter

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentBreakingNewsScreenBinding.bind(view)

        setupRecyclerView()

        setupRetryButton()

    }

    @ExperimentalCoroutinesApi
    private fun setupRecyclerView() {

        adapter = ArticlesPagingAdapter(this)

        adapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    loading()
                }
                is LoadState.Error -> {
                    error()
                }
                else -> {
                    success()
                }
            }
        }

        binding.apply {
            newsRecyclerView.setHasFixedSize(true)
            newsRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ArticlesLoadStateAdapter { adapter.retry() },
                footer = ArticlesLoadStateAdapter { adapter.retry() }
            )
            newsRecyclerView.layoutManager = LinearLayoutManager(activity)
        }

        viewModel.articles.observe(viewLifecycleOwner) { newPagingData ->
            adapter.submitData(viewLifecycleOwner.lifecycle, newPagingData)
        }
    }

    /*Trying to collect args.article.source.name on ArticleScreenFragment shows a weird error,
     had to pass the article title through a different argument in order to display the source as the
     ArticleScreen fragment's label*/
    override fun onRecyclerViewItemClicked(article: Article) {
        val articleSourceName = if (article.source?.name != null) article.source.name else "Article Web Page"
        val action = BreakingNewsFragmentDirections.actionNewsScreenFragmentToArticleFragment(article, articleSourceName)
        view?.findNavController()?.navigate(action)
    }

    private fun setupRetryButton() {
        binding.retryButton.setOnClickListener {
            adapter.retry()
        }
    }

    /*Load state listener functions*/
    private fun loading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.errorMessage.visibility = View.GONE
        binding.retryButton.visibility = View.GONE
    }

    private fun error() {
        binding.progressBar.visibility = View.GONE
        binding.errorMessage.visibility = View.VISIBLE
        binding.retryButton.visibility = View.VISIBLE
    }

    private fun success() {
        binding.progressBar.visibility = View.GONE
        binding.errorMessage.visibility = View.GONE
        binding.retryButton.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
