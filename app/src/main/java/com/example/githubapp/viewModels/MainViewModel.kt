package com.example.githubapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubapp.repository.MainRepository
import com.example.githubapp.models.GitHubApiResponseItem


class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()

    fun getClosedPrList(): LiveData<PagingData<GitHubApiResponseItem>> {
        return mainRepository.getAllClosedPrs().cachedIn(viewModelScope)
    }

}

