package com.example.sass.presentation.screens.tabs.favorite.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.sass.domain.models.FavoritePicItem

class FavoriteItemDiffCallBack : DiffUtil.ItemCallback<FavoritePicItem>() {
    override fun areItemsTheSame(oldItem: FavoritePicItem, newItem: FavoritePicItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FavoritePicItem, newItem: FavoritePicItem): Boolean {
        return oldItem == newItem
    }
}