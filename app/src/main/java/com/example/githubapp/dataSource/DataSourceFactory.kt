package com.example.githubapp.dataSource

import android.content.Context
import androidx.paging.DataSource
import com.example.githubapp.models.GitHubApiResponseItem

class DataSourceFactory(private val context: Context) :
    DataSource.Factory<Int, GitHubApiResponseItem>() {

    lateinit var imageDataSource: PrDataSource
    override fun create(): DataSource<Int, GitHubApiResponseItem> {
        imageDataSource = PrDataSource(context)
        return imageDataSource
    }

}
