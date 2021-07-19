package com.example.newsfeed.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.newsfeed.data.api.NewsApi
import com.example.newsfeed.data.database.SavedArticlesDatabase
import com.example.newsfeed.data.models.Article
import com.example.newsfeed.paging.AllNewsPagingSource
import com.example.newsfeed.paging.TopNewsPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val db: SavedArticlesDatabase,
) {

    //Remote API retrofit request functions
    fun getBreakingNews(country: String, category: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 250,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TopNewsPagingSource(
                newsApi,
                country = country,
                category = category) }
        ).flow

    fun getAllNews(
        query: String?,
        language: String?,
        sortBy: String?,
        sourceId: String?
    ) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 250,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { AllNewsPagingSource(
                newsApi,
                query = query,
                language = language,
                sortBy = sortBy,
                sourceId = sourceId) }
        ).flow

    suspend fun getSources(country: String, category: String) =
        newsApi.getSources(
            country = country,
            category = category)


    //Local Room database functions
    suspend fun upsertArticle(article: Article) {
        db.getDao().insert(article)
    }

    fun getAllSavedArticles() = db.getDao().getAllSavedArticles()

    suspend fun deleteArticle(article: Article) {
        db.getDao().deleteArticle(article)
    }

}
