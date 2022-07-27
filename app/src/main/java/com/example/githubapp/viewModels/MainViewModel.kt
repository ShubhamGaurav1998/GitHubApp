package com.example.githubapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.models.GitHubApiResponse
import com.example.githubapp.repository.GiHubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val gitHubRepository: GiHubRepository) : ViewModel() {

    fun fetchClosedPr() {
        viewModelScope.launch(Dispatchers.IO) {
            gitHubRepository.getClosedPRs()
        }
    }

    val prLivedata: LiveData<GitHubApiResponse>
        get() = gitHubRepository.prLiveData

}