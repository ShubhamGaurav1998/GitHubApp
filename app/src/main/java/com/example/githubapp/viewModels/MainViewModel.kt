package com.example.githubapp.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.githubapp.dataSource.DataSourceFactory
import com.example.githubapp.models.GitHubApiResponseItem
import com.example.githubapp.utils.Constants

class MainViewModel(context: Context) : ViewModel() {

    private var closedPrPagedList: LiveData<PagedList<GitHubApiResponseItem>>
    private var factory: DataSourceFactory
    init {
//        val factory: DataSourceFactory by lazy {
//            DataSourceFactory(context)
//        }
        factory = DataSourceFactory(context)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Constants.PAGE_SIZE)
            .build()

        closedPrPagedList = LivePagedListBuilder(factory, config).build()

    }

    fun getPrPagedList(): LiveData<PagedList<GitHubApiResponseItem>> {
        return closedPrPagedList

    }

    fun refresh() {
//        closedPrPagedList.value?.dataSource?.invalidate()
        factory.imageDataSource.invalidate()
    }
}