package com.example.newsfeed.ui.savedarticles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.data.models.Article
import com.example.newsfeed.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedArticlesViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {

    val savedArticles = newsRepository.getAllSavedArticles()

    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            newsRepository.deleteArticle(article)
        }
    }

    fun undoDeletion(article: Article) {
        viewModelScope.launch {
            newsRepository.upsertArticle(article)
        }
    }

}
