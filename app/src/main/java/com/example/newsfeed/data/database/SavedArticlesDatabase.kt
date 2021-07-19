package com.example.newsfeed.data.database

import androidx.room.*
import com.example.newsfeed.data.models.Article
import com.example.newsfeed.util.DataBaseTypeConverter

@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataBaseTypeConverter::class)
abstract class SavedArticlesDatabase: RoomDatabase() {

    abstract fun getDao(): SavedArticlesDao
}
