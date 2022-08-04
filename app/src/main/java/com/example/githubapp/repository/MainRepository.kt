package com.example.githubapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.githubapp.dataSource.ClosedPrDataSource
import com.example.githubapp.models.GitHubApiResponseItem
import com.example.githubapp.retrofit.GitHubService
import com.example.githubapp.utils.Constants
import javax.inject.Inject

class MainRepository @Inject constructor(private val gitHubService: GitHubService) {

    fun getAllClosedPrs(): LiveData<PagingData<GitHubApiResponseItem>> {

        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                ClosedPrDataSource(gitHubService)
            }
            , initialKey = 1
        ).liveData
    }

}