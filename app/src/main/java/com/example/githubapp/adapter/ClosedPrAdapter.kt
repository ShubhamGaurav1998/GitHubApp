package com.example.githubapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapp.databinding.RcvListItemBinding
import com.example.githubapp.models.GitHubApiResponseItem


class ClosedPrAdapter() : RecyclerView.Adapter<MyViewHolder>() {

    private var closedPrList = ArrayList<GitHubApiResponseItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItemBinding = RcvListItemBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(listItemBinding)

    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val videoItem = closedPrList[position]
        holder.bind(videoItem)
    }

    override fun getItemCount(): Int {
        return closedPrList.size
    }

    fun setList(closedPRList: List<GitHubApiResponseItem>) {
        closedPrList.addAll(closedPRList)
        this.notifyDataSetChanged()
    }
}

class MyViewHolder(val binding: RcvListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(videoItem: GitHubApiResponseItem) {
        binding.listItem = videoItem
    }
}