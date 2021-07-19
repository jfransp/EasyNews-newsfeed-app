package com.example.newsfeed.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsfeed.data.models.Article

@Dao
interface SavedArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Query("SELECT * FROM savedarticles ORDER BY publishedAt")
    fun getAllSavedArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)

}
