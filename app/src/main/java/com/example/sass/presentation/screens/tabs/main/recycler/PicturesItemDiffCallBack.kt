package com.example.sass.presentation.screens.tabs.main.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.sass.domain.models.PicturesItem

class PicturesItemDiffCallBack : DiffUtil.ItemCallback<PicturesItem>() {
    override fun areItemsTheSame(oldItem: PicturesItem, newItem: PicturesItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PicturesItem, newItem: PicturesItem): Boolean {
        return oldItem == newItem
    }
}