package com.kulloveth.covid19virustracker.ui

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class StatusLoadStateAdapter (
    private val retry: () -> Unit
    ) : LoadStateAdapter<StatusLoadStateViewHolder>() {
        override fun onBindViewHolder(holder: StatusLoadStateViewHolder, loadState: LoadState) {
            holder.bind(loadState)
        }

        override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): StatusLoadStateViewHolder {
            return StatusLoadStateViewHolder.create(parent, retry)
        }
}