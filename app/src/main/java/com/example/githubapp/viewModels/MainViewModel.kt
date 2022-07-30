package com.example.githubapp.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.githubapp.dataSource.DataSourceFactory
import com.example.githubapp.dataSource.PrDataSource
import com.example.githubapp.models.GitHubApiResponseItem
import com.example.githubapp.utils.Constants

class MainViewModel(context: Context) : ViewModel(), PrDataSource.ProgressBarStateListener {

    private var closedPrPagedList: LiveData<PagedList<GitHubApiResponseItem>>
    private var factory: DataSourceFactory
    private var progressBarMutableData = MutableLiveData<Boolean>()
    var progressBarLiveData: LiveData<Boolean> = progressBarMutableData
    init {
        factory = DataSourceFactory(context, this)
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
        factory.prDataSource.invalidate()
    }

    override fun listenProgressBarState(show: Boolean) {
        progressBarMutableData.postValue(show)
    }
}