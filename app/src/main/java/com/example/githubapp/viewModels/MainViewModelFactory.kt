package com.example.githubapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubapp.repository.GiHubRepository
import javax.inject.Inject

class MainViewModelFactory @Inject constructor
    (private val gitHubRepository: GiHubRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(gitHubRepository) as T
    }
}