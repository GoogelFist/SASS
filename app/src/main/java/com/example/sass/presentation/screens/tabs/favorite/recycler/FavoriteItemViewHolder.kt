package com.example.sass.presentation.screens.tabs.favorite.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sass.R
import com.example.sass.databinding.FavoritePostItemBinding
import com.example.sass.domain.models.FavoritePictureItem

class FavoriteItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val binding = FavoritePostItemBinding.bind(view)
    val favoriteButton = binding.ibFavoriteItemFavoriteButtonPost

    fun bind(favoritePictureItem: FavoritePictureItem) {
        configFavoritePhoto(favoritePictureItem)
        binding.tvFavoriteItemTitlePost.text = favoritePictureItem.title
        binding.tvFavoriteItemContentPost.text = favoritePictureItem.content
        binding.tvFavoriteItemPublicationDatePost.text = favoritePictureItem.publicationDate
        configFavoriteButton(favoritePictureItem)
    }

    private fun configFavoritePhoto(favoritePictureItem: FavoritePictureItem) {
        Glide.with(view.context)
            .load(favoritePictureItem.photoUrl)
            .centerCrop()
            .into(binding.ivFavoriteItemPhotoPost)
    }

    private fun configFavoriteButton(favoritePictureItem: FavoritePictureItem) {
        if (favoritePictureItem.isFavorite) {
            favoriteButton.setImageResource(R.drawable.ic_favorite_active)
        } else {
            favoriteButton.setImageResource(R.drawable.ic_favorite_inactive)
        }
    }
}