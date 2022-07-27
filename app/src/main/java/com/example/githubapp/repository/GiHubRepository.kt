package com.example.githubapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubapp.models.GitHubApiResponse
import com.example.githubapp.retrofit.GitHubService
import com.example.githubapp.utils.Constants
import javax.inject.Inject

//@Inject constructor
class GiHubRepository  (
    private val gitHubService: GitHubService
) {
    private val prMutableLivedata = MutableLiveData<GitHubApiResponse>()
    val prLiveData: LiveData<GitHubApiResponse>
        get() = prMutableLivedata

    suspend fun getClosedPRs() {
        val result =
            gitHubService.getResults(
                Constants.API_PATH, Constants.API_SUBPATH1, Constants.API_SUBPATH2,
                Constants.API_SUBPATH3, Constants.QUERY_PARAM_CLOSED, Constants.GITHUB_AUTH_TOKEN
            )

        if (result.body() != null) {
            prMutableLivedata.postValue(result.body())
        }
    }
}