package com.example.newsfeed.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.example.newsfeed.data.api.NewsApi
import com.example.newsfeed.data.models.Article
import com.example.newsfeed.util.Constants.Companion.PAGING_STARTER_PAGE_INDEX
import java.io.IOException

class TopNewsPagingSource(
    private val newsapi: NewsApi,
    private val country: String,
    private val category: String
): PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: PAGING_STARTER_PAGE_INDEX

        return try {

            val response = newsapi.getBreakingNews(
                pageNumber = position,
                pageSize = params.loadSize,
                countryCode = country,
                category = category
            )
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
