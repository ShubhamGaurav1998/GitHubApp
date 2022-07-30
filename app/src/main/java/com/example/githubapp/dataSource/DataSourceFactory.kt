package com.example.githubapp.dataSource

import android.content.Context
import androidx.paging.DataSource
import com.example.githubapp.models.GitHubApiResponseItem
import javax.inject.Inject

class DataSourceFactory @Inject constructor(private val context: Context, private val listener: PrDataSource.ProgressBarStateListener) :
    DataSource.Factory<Int, GitHubApiResponseItem>() {

    lateinit var prDataSource: PrDataSource
    override fun create(): DataSource<Int, GitHubApiResponseItem> {
        prDataSource = PrDataSource(context)
        prDataSource.setProgressbarStateListenr(listener)
        return prDataSource
    }

}
