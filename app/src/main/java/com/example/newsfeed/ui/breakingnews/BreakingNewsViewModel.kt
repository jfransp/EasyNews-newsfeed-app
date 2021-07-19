package com.example.newsfeed.ui.breakingnews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsfeed.data.datastore.PreferencesManager
import com.example.newsfeed.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(
    val newsRepository: NewsRepository,
    private val preferencesManager: PreferencesManager
): ViewModel() {

    //Get user preferences for request parameters
    private val userPreferences = preferencesManager.preferencesFlow

    //Combine flow from preferences and flow from repository request into a livedata
    @ExperimentalCoroutinesApi
    val articles = userPreferences.flatMapLatest { preferences ->
        val country = preferences.country
        val category = preferences.category
        newsRepository.getBreakingNews(country = country, category = category)
    }.asLiveData().cachedIn(viewModelScope)

}
