package com.example.newsfeed.di

import android.app.Application
import androidx.room.Room
import com.example.newsfeed.data.database.SavedArticlesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ) = Room.databaseBuilder(app, SavedArticlesDatabase::class.java, "saved_articles_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideDao(db: SavedArticlesDatabase) = db.getDao()

    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

}
