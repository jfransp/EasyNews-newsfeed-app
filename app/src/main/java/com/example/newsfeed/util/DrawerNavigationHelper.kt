package com.example.newsfeed.util

import android.view.MenuItem
import androidx.navigation.NavController
import com.example.newsfeed.R
import com.example.newsfeed.ui.activitylevelUI.MainViewModel

fun handleDrawerNavigation(it: MenuItem, viewModel: MainViewModel, navController: NavController) {
    when (it.itemId) {
        R.id.settings_button -> {
            navController.navigate(R.id.settingsScreenFragment)
        }
        R.id.all_sources_button -> {
            navController.navigate(R.id.sourceListScreenFragment)
        }
        else -> {
            when (it.itemId) {
                R.id.abc_news -> {
                    viewModel.sourceId = "abc-news"
                    viewModel.sourceUrl = "https://abcnews.go.com"
                }
                R.id.al_jazeera -> {
                    viewModel.sourceId = "al-jazeera-english"
                    viewModel.sourceUrl = "http://www.aljazeera.com"
                }
                R.id.bloomberg -> {
                    viewModel.sourceId = "bloomberg"
                    viewModel.sourceUrl = "http://www.bloomberg.com"
                }
                R.id.breitbart -> {
                    viewModel.sourceId = "breitbart-news"
                    viewModel.sourceUrl = "http://www.breitbart.com/"
                }
                R.id.buzzfeed -> {
                    viewModel.sourceId = "buzzfeed"
                    viewModel.sourceUrl = "https://www.buzzfeed.com/"
                }
                R.id.cbc_news -> {
                    viewModel.sourceId = "cbc-news"
                    viewModel.sourceUrl = "http://www.aljazeera.com"
                }
                R.id.cbs_news -> {
                    viewModel.sourceId = "cbs-news"
                    viewModel.sourceUrl = "http://www.cbsnews.com/"
                }
                R.id.cnn -> {
                    viewModel.sourceId = "cnn"
                    viewModel.sourceUrl = "http://us.cnn.com/"
                }
                R.id.financial_post -> {
                    viewModel.sourceId = "financial-post"
                    viewModel.sourceUrl = "http://business.financialpost.com/"
                }
                R.id.fox_news -> {
                    viewModel.sourceId = "fox-news"
                    viewModel.sourceUrl = "http://www.foxnews.com/"
                }
                R.id.independent -> {
                    viewModel.sourceId = "independent"
                    viewModel.sourceUrl = "http://www.independent.co.uk/"
                }
                R.id.msnbc -> {
                    viewModel.sourceId = "msnbc"
                    viewModel.sourceUrl = "http://www.msnbc.com/"
                }
                R.id.nbc_news -> {
                    viewModel.sourceId = "nbc-news"
                    viewModel.sourceUrl = "http://www.nbcnews.com/"
                }
                R.id.newsweek -> {
                    viewModel.sourceId = "newsweek"
                    viewModel.sourceUrl = "https://www.newsweek.com/"
                }
                R.id.politico -> {
                    viewModel.sourceId = "politico"
                    viewModel.sourceUrl = "https://www.politico.com/"
                }
                R.id.reuters -> {
                    viewModel.sourceId = "reuters"
                    viewModel.sourceUrl = "http://www.reuters.com/"
                }
                R.id.rt -> {
                    viewModel.sourceId = "rt"
                    viewModel.sourceUrl = "https://russian.rt.com/"
                }
                R.id.the_hill -> {
                    viewModel.sourceId = "the-hill"
                    viewModel.sourceUrl = "http://thehill.com/"
                }
                R.id.the_washington_post -> {
                    viewModel.sourceId = "the-washington-post"
                    viewModel.sourceUrl = "https://www.washingtonpost.com/"
                }
                R.id.time -> {
                    viewModel.sourceId = "time"
                    viewModel.sourceUrl = "http://time.com/"
                }
                R.id.vice_news -> {
                    viewModel.sourceId = "vice-news"
                    viewModel.sourceUrl = "https://news.vice.com/"
                }
            }
            viewModel.sourceName = it.title.toString()
            navController.navigate(R.id.sourceScreenFragment)
        }
    }
}
