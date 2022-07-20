package com.example.sass.presentation.screens.tabs.main.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.sass.R
import com.example.sass.domain.models.PicturesItem

class PicturesMainAdapter :
    ListAdapter<PicturesItem, PicturesItemViewHolder>(PicturesItemDiffCallBack()) {

    lateinit var onPictureClickListener: (id: String) -> Unit
    lateinit var onFavoriteButtonClickListener: (id: String, isFavorite: Boolean) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_post_item, parent, false)

        val viewHolder = PicturesItemViewHolder(view)
        val favoriteButton = viewHolder.favoriteButton

        favoriteButton.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != NO_POSITION) {
                val id = currentList[position].id
                onFavoriteButtonClickListener.invoke(id, currentList[position].isFavorite)
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

    override fun onBindViewHolder(holder: PicturesItemViewHolder, position: Int) {
        val picturesItem = getItem(position)
        holder.bind(picturesItem)
    }

    companion object {
        private const val NO_POSITION = -1
    }
}