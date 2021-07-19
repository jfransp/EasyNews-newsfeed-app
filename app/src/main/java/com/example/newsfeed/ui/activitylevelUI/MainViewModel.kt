package com.example.newsfeed.ui.activitylevelUI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.data.datastore.PreferencesManager
import com.example.newsfeed.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val preferencesManager: PreferencesManager
): ViewModel() {

    //User preferences as flow and livedata
    private val userPreferences = preferencesManager.preferencesFlow
    val userPreferencesLiveData = userPreferences.asLiveData()


    /*Utility variables for drawer navigation: since the drawer doesn't have set actions
    * on the nav graph on which to insert arguments for safe args use (given the menu style navigation),
    * I decided to use this hacky way of passing information between the fragments that share the MainViewModel.
    * Process death isn't really a problem since we only need the variables while navigating from one fragment
    * to the other and the variables are (should) be reset accordingly before every navigation at the
    * Activity level (navigations not happening on the three main fragments environment) so those
    * happen properly. Would be nice to fix this in the future with a more elegant solution, maybe some bundle thing.*/
    var sourceName: String? = null
    var sourceId: String? = null
    var sourceUrl: String? = null

    fun getArticlesBySource() =
        newsRepository.getAllNews(query = null,
            language = null,
            sortBy = null,
            sourceId = sourceId)

    //Functions for setting user preferences
    fun setCountryPreference(country: String) {
        viewModelScope.launch {
            preferencesManager.setCountryPreference(country)
        }
    }

    fun setCategoryPreference(category: String) {
        viewModelScope.launch {
            preferencesManager.setCategoryPreference(category)
        }
    }

    //Gets list of sources
    suspend fun getSource(country: String, category:String) = newsRepository.getSources(
        country = country,
        category = category
    ).sources
}
