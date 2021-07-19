package com.example.newsfeed.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.example.newsfeed.data.api.NewsApi
import com.example.newsfeed.util.Constants.Companion.PAGING_STARTER_PAGE_INDEX
import com.example.newsfeed.data.models.Article
import java.io.IOException

class AllNewsPagingSource(
    private val newsApi: NewsApi,
    private val query: String?,
    private val language: String?,
    private val sortBy: String?,
    private val sourceId: String?
): PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: PAGING_STARTER_PAGE_INDEX

        return try {

            val response = newsApi.getAllNews(
                searchQuery = query,
                language = language,
                pageNumber = position,
                pageSize = params.loadSize,
                sortBy = sortBy,
                sourceId = sourceId)

            val articles = response.articles

            LoadResult.Page(
                data = articles,
                prevKey = if (position == PAGING_STARTER_PAGE_INDEX) null else position - 1,
                nextKey = if (articles.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition
    }

}
