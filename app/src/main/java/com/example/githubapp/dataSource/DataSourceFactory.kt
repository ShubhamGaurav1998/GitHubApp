package com.example.githubapp.dataSource

import android.content.Context
import androidx.paging.DataSource
import com.example.githubapp.di.DaggerApplicationComponent
import com.example.githubapp.models.GitHubApiResponseItem
import javax.inject.Inject

class DataSourceFactory (private val context: Context, private val listener: PrDataSource.ProgressBarStateListener) :
    DataSource.Factory<Int, GitHubApiResponseItem>() {

    @Inject
    lateinit var prDataSource: PrDataSource
    override fun create(): DataSource<Int, GitHubApiResponseItem> {
        DaggerApplicationComponent.factory().create(context).injectDependency(this)
//        prDataSource = PrDataSource(context)
        prDataSource.setProgressbarStateListenr(listener)
        return prDataSource
    }

}
