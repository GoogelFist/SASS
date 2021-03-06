package com.example.sass.presentation.screens.tabs.favorite.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.sass.R
import com.example.sass.domain.models.FavoritePictureItem

class FavoritePicsAdapter :
    ListAdapter<FavoritePictureItem, FavoriteItemViewHolder>(FavoriteItemDiffCallBack()) {

    lateinit var onPictureClickListener: (id: String) -> Unit
    lateinit var onRemoveFavoriteButtonClickListener: (pictureId: String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_post_item, parent, false)

        val viewHolder = FavoriteItemViewHolder(view)
        val favoriteButton = viewHolder.favoriteButton

        favoriteButton.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != NO_POSITION) {
                val id = currentList[position].id
                onRemoveFavoriteButtonClickListener.invoke(id)
            }
        }

        view.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != NO_POSITION) {
                val id = currentList[position].id
                onPictureClickListener.invoke(id)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: FavoriteItemViewHolder, position: Int) {
        val favoritePicItem = getItem(position)
        holder.bind(favoritePicItem)
    }

    companion object {
        private const val NO_POSITION = -1
    }
}