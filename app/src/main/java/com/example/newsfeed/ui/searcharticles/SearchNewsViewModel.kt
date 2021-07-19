package com.example.newsfeed.ui.searcharticles

import androidx.lifecycle.*
import com.example.newsfeed.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
): ViewModel() {

    var language = MutableStateFlow("en")
    var sortBy = MutableStateFlow("relevancy")
    var searchQuery = MutableStateFlow(" ")

    fun getAllNews() = newsRepository.getAllNews(
            query = searchQuery.value,
            language = language.value,
            sortBy = sortBy.value,
            sourceId = null)

}
