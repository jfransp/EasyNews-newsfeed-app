package com.example.newsfeed.ui.savedarticles

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.R
import com.example.newsfeed.adapters.SavedArticlesRecycleViewAdapter
import com.example.newsfeed.data.models.Article
import com.example.newsfeed.databinding.FragmentSavedNewsScreenBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedArticlesFragment: Fragment(R.layout.fragment_saved_news_screen), SavedArticlesRecycleViewAdapter.OnItemClickListener {

    private val viewModel: SavedArticlesViewModel by viewModels()

    private var _binding: FragmentSavedNewsScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SavedArticlesRecycleViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSavedNewsScreenBinding.bind(view)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        adapter = SavedArticlesRecycleViewAdapter(this)

        binding.apply {
            savedNewsRecyclerview.setHasFixedSize(true)
            savedNewsRecyclerview.adapter = adapter
            savedNewsRecyclerview.layoutManager = LinearLayoutManager(activity)

        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val article = adapter.currentList[position]
                viewModel.deleteArticle(article)
                view?.let {
                    Snackbar.make(it, "Article deleted", Snackbar.LENGTH_LONG).apply {
                        setAction("Undo") {
                            viewModel.undoDeletion(article)
                        }
                        show()
                    }
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.savedNewsRecyclerview)
        }

        viewModel.savedArticles.observe(viewLifecycleOwner, { articleList ->
            adapter.submitList(articleList)
        })
    }

    override fun onRecyclerViewItemClicked(article: Article) {
        val articleSourceName = if (article.source?.name != null) article.source.name else "Article Web Page"
        val action = SavedArticlesFragmentDirections.actionSavedNewsFragmentToArticleFragment(article, articleSourceName)
        view?.findNavController()?.navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
