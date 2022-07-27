package com.example.githubapp.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.githubapp.MyApplication
import com.example.githubapp.R
import com.example.githubapp.databinding.ActivityMainBinding
import com.example.githubapp.di.RetrofitInstance
import com.example.githubapp.models.GitHubApiResponse
import com.example.githubapp.repository.GiHubRepository
import com.example.githubapp.retrofit.GitHubService
import com.example.githubapp.utils.CommonUtils
import com.example.githubapp.viewModels.MainViewModel
import com.example.githubapp.viewModels.MainViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

private lateinit var binding: ActivityMainBinding
private lateinit var mainViewModel: MainViewModel
private lateinit var gitHubRepository: GiHubRepository

//@Inject
lateinit var mainViewModelFactory: MainViewModelFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        (application as MyApplication).applicationComponent.inject(this)
        gitHubRepository = GiHubRepository(RetrofitInstance.getRetrofitInstance().create(GitHubService::class.java))
        mainViewModelFactory = MainViewModelFactory(gitHubRepository)
        mainViewModel = ViewModelProvider(
            this,
            mainViewModelFactory
        )[MainViewModel::class.java]

        fetchClosePrFromApi()
    }

    private fun fetchClosePrFromApi() {
        if (CommonUtils.isInternetAvailable(this)) {
            lifecycleScope.launch {
                mainViewModel.fetchClosedPr()
            }
        }
    }
}