package com.example.sass.presentation.screens.tabs.favorite.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.sass.domain.models.FavoritePictureItem

class FavoriteItemDiffCallBack : DiffUtil.ItemCallback<FavoritePictureItem>() {
    override fun areItemsTheSame(oldItem: FavoritePictureItem, newItem: FavoritePictureItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FavoritePictureItem, newItem: FavoritePictureItem): Boolean {
        return oldItem == newItem
    }
}