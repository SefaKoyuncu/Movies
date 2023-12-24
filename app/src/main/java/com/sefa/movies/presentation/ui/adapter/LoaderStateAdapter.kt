package com.sefa.movies.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sefa.movies.databinding.LoaderStateItemBinding

class LoaderStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoaderStateAdapter.LoaderStateViewHolder>() {

    inner class LoaderStateViewHolder(private val binding: LoaderStateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBarLoading.visibility = if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
                buttonRetry.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
                textViewErrorMsg.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderStateViewHolder {
        val binding =
            LoaderStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoaderStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoaderStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}