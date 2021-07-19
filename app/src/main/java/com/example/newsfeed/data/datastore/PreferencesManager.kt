package com.example.newsfeed.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.newsfeed.data.models.UserPreferences
import com.example.newsfeed.util.Constants.Companion.DEFAULT_CATEGORY_PREFERENCE
import com.example.newsfeed.util.Constants.Companion.DEFAULT_COUNTRY_PREFERENCE
import com.example.newsfeed.util.Constants.Companion.PREFERENCES_MANAGER_TAG
import com.example.newsfeed.util.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext appContext: Context) {

    private val preferencesDataStore = appContext.dataStore

    val preferencesFlow = preferencesDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(PREFERENCES_MANAGER_TAG, "Error while trying to read user preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val country = preferences[PreferenceKeys.COUNTRY] ?: DEFAULT_COUNTRY_PREFERENCE
            val category = preferences[PreferenceKeys.CATEGORY] ?: DEFAULT_CATEGORY_PREFERENCE
            UserPreferences(country, category)
        }


    suspend fun setCountryPreference(country: String) {
        preferencesDataStore.edit { preference ->
            preference[PreferenceKeys.COUNTRY] = country
            Log.e(PREFERENCES_MANAGER_TAG, "Country preference set")
        }
    }

    suspend fun setCategoryPreference(category: String) {
        preferencesDataStore.edit { preference ->
            preference[PreferenceKeys.CATEGORY] = category
            Log.e(PREFERENCES_MANAGER_TAG, "Category preference set")
        }
    }

    private object PreferenceKeys {
        val COUNTRY = stringPreferencesKey("country")
        val CATEGORY = stringPreferencesKey("category")
    }

}
