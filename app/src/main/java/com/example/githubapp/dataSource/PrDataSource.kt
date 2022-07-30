package com.example.githubapp.dataSource

import android.content.Context
import androidx.paging.PageKeyedDataSource
import com.example.githubapp.di.RetrofitInstance
import com.example.githubapp.models.GitHubApiResponse
import com.example.githubapp.models.GitHubApiResponseItem
import com.example.githubapp.retrofit.GitHubService
import com.example.githubapp.utils.CommonUtils
import com.example.githubapp.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PrDataSource @Inject constructor(private val context: Context) :
    PageKeyedDataSource<Int, GitHubApiResponseItem>() {

    var mProgressBarStateListener: ProgressBarStateListener? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GitHubApiResponseItem>
    ) {
        if (CommonUtils.isInternetAvailable(context)) {
            getPRs(callback)
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GitHubApiResponseItem>
    ) {
        if (CommonUtils.isInternetAvailable(context)) {
            val nextPageNo = params.key + 1
            getMorePRs(params.key, nextPageNo, callback)
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GitHubApiResponseItem>
    ) {
        if (CommonUtils.isInternetAvailable(context)) {
            val previousPageNo = if (params.key > 1) params.key - 1 else 0
            getMorePRs(params.key, previousPageNo, callback)
        }
    }

    private fun getPRs(callback: LoadInitialCallback<Int, GitHubApiResponseItem>) {
        mProgressBarStateListener?.listenProgressBarState(true)

        RetrofitInstance.getRetrofitInstance().create(GitHubService::class.java)
            .getResults(
                Constants.API_PATH,
                Constants.API_SUBPATH1,
                Constants.API_SUBPATH2,
                Constants.API_SUBPATH3,
                Constants.QUERY_PARAM_CLOSED,
                1,
                Constants.PAGE_SIZE,
                Constants.GITHUB_AUTH_TOKEN
            ).enqueue(object :
                Callback<GitHubApiResponse> {
                override fun onFailure(call: Call<GitHubApiResponse>, t: Throwable) {
                    mProgressBarStateListener?.listenProgressBarState(false)
                }

                override fun onResponse(
                    call: Call<GitHubApiResponse>,
                    response: Response<GitHubApiResponse>
                ) {
                    mProgressBarStateListener?.listenProgressBarState(false)
                    val clodedPrResponse = response.body()
                    clodedPrResponse?.let { callback.onResult(it, null, 2) }
                }
            })
    }

    private fun getMorePRs(
        pageNo: Int,
        previousOrNextPageNo: Int,
        callback: LoadCallback<Int, GitHubApiResponseItem>
    ) {
        mProgressBarStateListener?.listenProgressBarState(true)
        RetrofitInstance.getRetrofitInstance().create(GitHubService::class.java)
            .getResults(
                Constants.API_PATH,
                Constants.API_SUBPATH1,
                Constants.API_SUBPATH2,
                Constants.API_SUBPATH3,
                Constants.QUERY_PARAM_CLOSED,
                pageNo,
                Constants.PAGE_SIZE,
                Constants.GITHUB_AUTH_TOKEN
            ).enqueue(object :
                Callback<GitHubApiResponse> {
                override fun onFailure(call: Call<GitHubApiResponse>, t: Throwable) {
                    mProgressBarStateListener?.listenProgressBarState(false)
                }

                override fun onResponse(
                    call: Call<GitHubApiResponse>,
                    response: Response<GitHubApiResponse>
                ) {
                    mProgressBarStateListener?.listenProgressBarState(false)
                    val clodedPrResponse = response.body()
                    clodedPrResponse?.let { callback.onResult(it, previousOrNextPageNo) }
                }
            })
    }

    interface ProgressBarStateListener {
        fun listenProgressBarState(show: Boolean)
    }

    fun setProgressbarStateListenr(progressBarStateListener: ProgressBarStateListener) {
        mProgressBarStateListener = progressBarStateListener
    }

}
