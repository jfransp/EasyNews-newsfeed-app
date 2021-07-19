package com.example.newsfeed.util

class Constants {
    companion object {
        //REST API Request constants
        const val BASE_URL = "https://newsapi.org"
        const val PAGING_STARTER_PAGE_INDEX = 1
        //PreferencesManager constants
        const val PREFERENCES_NAME = "user_settings"
        const val PREFERENCES_MANAGER_TAG = "PreferencesManager"
        const val DEFAULT_COUNTRY_PREFERENCE = "us"
        const val DEFAULT_CATEGORY_PREFERENCE = "general"
    }
}
