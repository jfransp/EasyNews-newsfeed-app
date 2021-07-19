package com.example.newsfeed.ui.articlescreen

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.newsfeed.R
import com.example.newsfeed.databinding.FragmentArticleScreenBinding
import com.example.newsfeed.ui.activitylevelUI.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment: Fragment(R.layout.fragment_article_screen) {

    private val viewModel: ArticleViewModel by viewModels()

    private val args: ArticleFragmentArgs by navArgs()

    private var _binding: FragmentArticleScreenBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentArticleScreenBinding.bind(view)

        applyPageSourceAsLabel()

        setupWebView()

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.article_screen_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_article_button -> {
                val articleToSave = args.article
                viewModel.saveArticle(articleToSave)
                Toast.makeText(activity, "Article saved", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.share_article_button -> {
                val urlToShare = args.article.url
                val shareIntent = Intent().apply {
                    this.action = Intent.ACTION_SEND
                    this.putExtra(Intent.EXTRA_TEXT, urlToShare)
                    this.type = "text/plain"
                }
                startActivity(shareIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun applyPageSourceAsLabel() {
        val fragmentLabel = args.sourceName
        (activity as MainActivity).supportActionBar?.title = fragmentLabel
    }

    private fun setupWebView() {
        val articleUrl = args.article.url

        binding.webview.apply {
            webViewClient = WebViewClient()
            loadUrl(articleUrl)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
