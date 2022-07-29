package com.example.githubapp.views

import android.app.AlertDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.R
import com.example.githubapp.adapter.ClosedPrAdapter
import com.example.githubapp.databinding.ActivityMainBinding
import com.example.githubapp.di.RetrofitInstance
import com.example.githubapp.models.GitHubApiResponseItem
import com.example.githubapp.repository.GiHubRepository
import com.example.githubapp.retrofit.GitHubService
import com.example.githubapp.utils.CommonUtils
import com.example.githubapp.viewModels.MainViewModel
import com.example.githubapp.viewModels.MainViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var gitHubRepository: GiHubRepository

    //@Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var closedPrAdapter: ClosedPrAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        binding.progressBar.visibility = View.VISIBLE
//        (application as MyApplication).applicationComponent.inject(this)
        gitHubRepository = GiHubRepository(
            RetrofitInstance.getRetrofitInstance().create(GitHubService::class.java)
        )
        mainViewModelFactory = MainViewModelFactory(gitHubRepository)
        mainViewModel = ViewModelProvider(
            this,
            mainViewModelFactory
        )[MainViewModel::class.java]

        initializeRecyclerView()
        fetchClosedPrFromApi()
        observeClosedPrList()
    }

    private fun fetchClosedPrFromApi() {
        if (CommonUtils.isInternetAvailable(this)) {
            lifecycleScope.launch {
                mainViewModel.fetchClosedPr()
            }
        }
    }

    private fun initializeRecyclerView() {

        val recyclerView = binding.prRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        closedPrAdapter = ClosedPrAdapter()
        recyclerView.adapter = closedPrAdapter
    }

    private fun observeClosedPrList() {
        mainViewModel.prLivedata.observe(this) {
            binding.progressBar.visibility = View.GONE

            if (it != null) {
                val closedPrList = ArrayList<GitHubApiResponseItem>()
                it.iterator().forEach { closedPr ->
                    closedPrList.add(closedPr)
                }
                closedPrAdapter.setList(closedPrList)
            }
            else{
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {

        AlertDialog.Builder(this)
            .setMessage(this.getString(R.string.quit_confirmation_message))
            .setCancelable(false)
            .setPositiveButton(
                this.getString(R.string.yes)
            ) { dialog, id -> this@MainActivity.finish() }
            .setNegativeButton(this.getString(R.string.no), null)
            .show()
    }
}