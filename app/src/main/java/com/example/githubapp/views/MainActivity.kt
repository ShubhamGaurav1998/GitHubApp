package com.example.githubapp.views

import android.app.AlertDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.R
import com.example.githubapp.adapter.ClosedPrAdapter
import com.example.githubapp.databinding.ActivityMainBinding
import com.example.githubapp.models.GitHubApiResponseItem
import com.example.githubapp.viewModels.MainViewModel
import com.example.githubapp.viewModels.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

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

        mainViewModelFactory = MainViewModelFactory(this)
        mainViewModel = ViewModelProvider(
            this,
            mainViewModelFactory
        )[MainViewModel::class.java]

        initializeRecyclerView()
        observeClosedPrList()
    }

    private fun initializeRecyclerView() {

        val recyclerView = binding.prRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        closedPrAdapter = ClosedPrAdapter()
        recyclerView.adapter = closedPrAdapter
    }

    private fun observeClosedPrList() {

        mainViewModel.getData().observe(this, object : Observer<PagedList<GitHubApiResponseItem>> {
            override fun onChanged(it: PagedList<GitHubApiResponseItem>?) {
                binding.progressBar.visibility = View.GONE

                if (it != null) {
                    closedPrAdapter.submitList(it)
                } else {
                    Toast.makeText(applicationContext, "No data found", Toast.LENGTH_SHORT).show()
                }
            }
        })

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