package com.example.sass.presentation.screens.tabs.favorite.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sass.R
import com.example.sass.databinding.FavoritePostItemBinding
import com.example.sass.domain.models.FavoritePicItem

class FavoriteItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val binding = FavoritePostItemBinding.bind(view)
    val favoriteButton = binding.ibFavoriteItemFavoriteButtonPost

    fun bind(favoritePicItem: FavoritePicItem) {
        Glide.with(view.context)
            .load(favoritePicItem.photoUrl)
            .centerCrop()
            .into(binding.ivFavoriteItemPhotoPost)

        binding.tvFavoriteItemTitlePost.text = favoritePicItem.title
        binding.tvFavoriteItemContentPost.text = favoritePicItem.content
        binding.tvFavoriteItemPublicationDatePost.text = favoritePicItem.publicationDate

        if (favoritePicItem.isFavorite) {
            favoriteButton.setImageResource(R.drawable.ic_favorite_active)
        } else {
            favoriteButton.setImageResource(R.drawable.ic_favorite_inactive)
        }
    }
}