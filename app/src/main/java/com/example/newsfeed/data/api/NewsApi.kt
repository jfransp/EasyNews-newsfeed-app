package com.example.newsfeed.data.api

import com.example.newsfeed.data.api.ApiKey.Companion.API_KEY
import com.example.newsfeed.data.models.NewsApiResponse
import com.example.newsfeed.data.models.SourceResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApi {

    @Headers("X-Api-Key:$API_KEY")
    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String?,
        @Query("page")
        pageNumber: Int = 1,
        @Query("pageSize")
        pageSize: Int,
        @Query("category")
        category: String?
    ): NewsApiResponse

    @Headers("X-Api-Key:$API_KEY")
    @GET("/v2/everything")
    suspend fun getAllNews(
        @Query("sources")
        sourceId: String?,
        @Query("q")
        searchQuery: String?,
        @Query("language")
        language: String?,
        @Query("page")
        pageNumber: Int = 1,
        @Query("pageSize")
        pageSize: Int,
        @Query("sortBy")
        sortBy: String?
    ): NewsApiResponse

    @Headers("X-Api-Key:$API_KEY")
    @GET("/v2/top-headlines/sources")
    suspend fun getSources(
        @Query("country")
        country: String,
        @Query("category")
        category: String): SourceResponse

}
