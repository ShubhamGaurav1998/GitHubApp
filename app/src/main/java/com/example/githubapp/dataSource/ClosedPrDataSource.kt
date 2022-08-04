package com.example.githubapp.dataSource

import android.content.Context
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubapp.models.GitHubApiResponseItem
import com.example.githubapp.retrofit.GitHubService
import com.example.githubapp.utils.Constants
import javax.inject.Inject

class ClosedPrDataSource @Inject constructor(private val gitHubService: GitHubService) :
    PagingSource<Int, GitHubApiResponseItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitHubApiResponseItem> {
        return try {
            val position = params.key ?: 1
            val response = gitHubService.getResults(
                Constants.API_PATH,
                Constants.API_SUBPATH1,
                Constants.API_SUBPATH2,
                Constants.API_SUBPATH3,
                Constants.QUERY_PARAM_CLOSED,
                position,
                Constants.PAGE_SIZE,
                Constants.GITHUB_AUTH_TOKEN)
            LoadResult.Page(data = response.body()!!, prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GitHubApiResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}