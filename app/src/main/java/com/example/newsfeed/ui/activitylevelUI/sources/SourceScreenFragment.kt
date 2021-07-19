package com.example.newsfeed.ui.activitylevelUI.sources

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsfeed.R
import com.example.newsfeed.adapters.ArticlesLoadStateAdapter
import com.example.newsfeed.adapters.ArticlesPagingAdapter
import com.example.newsfeed.data.models.Article
import com.example.newsfeed.databinding.FragmentSourceScreenBinding
import com.example.newsfeed.ui.activitylevelUI.MainActivity
import com.example.newsfeed.ui.activitylevelUI.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class SourceScreenFragment : Fragment(R.layout.fragment_source_screen), ArticlesPagingAdapter.OnItemClickListener {

    val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentSourceScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArticlesPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSourceScreenBinding.bind(view)

        applyPageSourceAsLabel()

        setupRecyclerView()

        setHasOptionsMenu(true)
    }

    private fun applyPageSourceAsLabel() {
        val fragmentLabel = viewModel.sourceName
        (activity as MainActivity).supportActionBar?.title = fragmentLabel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.source_screen_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.goto_website_button -> {
                val url = viewModel.sourceUrl
                val navigateToWebIntent = Intent(Intent.ACTION_VIEW)
                navigateToWebIntent.data = Uri.parse(url)
                startActivity(navigateToWebIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

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
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ArticlesLoadStateAdapter { adapter.retry() },
                footer = ArticlesLoadStateAdapter { adapter.retry() }
            )
            recyclerView.layoutManager = LinearLayoutManager(activity)
        }

        lifecycleScope.launch {
            viewModel.getArticlesBySource().collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    override fun onRecyclerViewItemClicked(article: Article) {
        val articleSourceName = if (article.source?.name != null) article.source.name else "Article Web Page"
        val action = SourceScreenFragmentDirections.actionSourceScreenFragmentToArticleFragment(article, articleSourceName)
        view?.findNavController()?.navigate(action)
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
