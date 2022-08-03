package com.example.githubapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapp.databinding.RcvListItemBinding
import com.example.githubapp.models.GitHubApiResponseItem


class ClosedPrAdapter : PagingDataAdapter<GitHubApiResponseItem, ClosedPrAdapter.MyViewHolder>(
    USER_COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItemBinding = RcvListItemBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder, position: Int
    ) {
        val closedPrItem = getItem(position)
        if (closedPrItem != null) {
            holder.bind(closedPrItem)
        }
    }

    class MyViewHolder(val binding: RcvListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(closedPrItem: GitHubApiResponseItem) {
            binding.listItem = closedPrItem
            binding.executePendingBindings()
        }
    }

    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<GitHubApiResponseItem>() {
            override fun areItemsTheSame(
                oldItem: GitHubApiResponseItem,
                newItem: GitHubApiResponseItem
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: GitHubApiResponseItem,
                newItem: GitHubApiResponseItem
            ): Boolean =
                newItem == oldItem
        }
    }
}