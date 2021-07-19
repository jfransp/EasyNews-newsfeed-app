package com.example.newsfeed.ui.searcharticles

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsfeed.R
import com.example.newsfeed.adapters.ArticlesLoadStateAdapter
import com.example.newsfeed.adapters.ArticlesPagingAdapter
import com.example.newsfeed.data.models.Article
import com.example.newsfeed.databinding.FragmentSearchScreenBinding
import com.example.newsfeed.util.convertToISO
import com.example.newsfeed.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchNewsFragment : Fragment(R.layout.fragment_search_screen), ArticlesPagingAdapter.OnItemClickListener {

    private val viewModel: SearchNewsViewModel by viewModels()

    private var _binding: FragmentSearchScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ArticlesPagingAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSearchScreenBinding.bind(view)

        setupRecyclerView()

        setupSearchButton()

        setupSpinners()

        setupRetryButton()

    }

    private fun setupSearchButton() {
        binding.searchButton.setOnClickListener {
            onSearchButtonClicked()
        }
    }

    /*The function has to deal with the possibility of an empty searchquery - the backend
    * doesn't accept an empty parameter for the required "q" argument in the request, so I decided to deal
    * with it here and let the user know instead of providing some default value for the request.*/
    private fun onSearchButtonClicked() {
        when {
            binding.searchQueryEdittext.text.isEmpty() -> {
                onSearchQueryEmpty()
                hideKeyboard()
            }
            binding.searchQueryEdittext.text.isNotEmpty() -> {
                onSearchQueryNotEmpty()

                viewModel.searchQuery.value = binding.searchQueryEdittext.text.toString()

                lifecycleScope.launch {
                    viewModel.getAllNews().collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
                }
                hideKeyboard()
            }
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
            searchItemsRecyclerview.setHasFixedSize(true)
            searchItemsRecyclerview.adapter = adapter.withLoadStateHeaderAndFooter(
                header = ArticlesLoadStateAdapter { adapter.retry() },
                footer = ArticlesLoadStateAdapter { adapter.retry() }
            )
            searchItemsRecyclerview.layoutManager = LinearLayoutManager(activity)
        }
    }

    /*Trying to collect args.article.source.name on ArticleScreenFragment shows a weird error,
     had to pass the article title through a different argument in order to display the source as the
     ArticleScreen fragment's label*/
    override fun onRecyclerViewItemClicked(article: Article) {
        val articleSourceName = if (article.source?.name != null) article.source.name else "Article Web Page"
        val action = SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(article, articleSourceName)
        view?.findNavController()?.navigate(action)
    }

    private fun setupSpinners() {
        binding.apply {

            languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val formattedLanguage = convertToISO(parent?.getItemAtPosition(position).toString())
                    viewModel.language.value = formattedLanguage
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            sortBySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val item = parent?.getItemAtPosition(position).toString()
                    viewModel.sortBy.value = when (item) {
                        "Relevancy" -> "relevancy"
                        "Popularity" -> "popularity"
                        "Most recent" -> "publishedAt"
                        else -> return
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }

    fun setupRetryButton() {
        binding.retryButton.setOnClickListener {
            adapter.retry()
        }
    }

    /*I was intending to use a MotionlLayout for the search screen, but the library has several
    * finicky issues with programmatically setting the visibility of it's child views and I didn't
    * really wanna deal with it - might try and fix it in the future. For now, I've left it as
    * a regular ConstraintLayout so the following functions work properly. Just handling some error
    * messages visibility.*/
    fun onSearchQueryEmpty() {
        binding.searchItemsRecyclerview.visibility = View.INVISIBLE
        binding.emptySearchQueryErrorMessage.visibility = View.VISIBLE
    }

    fun onSearchQueryNotEmpty() {
        binding.searchItemsRecyclerview.visibility = View.VISIBLE
        binding.emptySearchQueryErrorMessage.visibility = View.INVISIBLE
    }

    /*Load state listener functions*/
    private fun loading() {
        if (binding.searchItemsRecyclerview.isNotEmpty()) {
            binding.searchItemsRecyclerview.visibility = View.INVISIBLE
        }
        binding.progressBar.visibility = View.VISIBLE
        binding.errorMessage.visibility = View.GONE
        binding.retryButton.visibility = View.GONE
    }

    private fun error() {
        if (binding.searchItemsRecyclerview.isNotEmpty()) {
            binding.searchItemsRecyclerview.visibility = View.INVISIBLE
        }
        binding.progressBar.visibility = View.GONE
        binding.errorMessage.visibility = View.VISIBLE
        binding.retryButton.visibility = View.VISIBLE
    }

    private fun success() {
        binding.searchItemsRecyclerview.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        binding.errorMessage.visibility = View.GONE
        binding.retryButton.visibility = View.GONE

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
