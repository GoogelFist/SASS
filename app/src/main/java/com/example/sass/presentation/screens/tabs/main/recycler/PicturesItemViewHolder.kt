package com.example.sass.presentation.screens.tabs.main.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sass.R
import com.example.sass.databinding.MainPostItemBinding
import com.example.sass.domain.models.PicturesItem

class PicturesItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val binding = MainPostItemBinding.bind(view)
    val favoriteButton = binding.ibFavoritePostItem

    fun bind(picturesItem: PicturesItem) {
        Glide.with(view.context)
            .load(picturesItem.photoUrl)
            .centerCrop()
            .into(binding.ivPhotoPostItem)

        binding.tvTitlePostItem.text = picturesItem.title

        if (picturesItem.isFavorite) {
            favoriteButton.setImageResource(R.drawable.ic_favorite_active)
        } else {
            favoriteButton.setImageResource(R.drawable.ic_favorite_inactive)
        }
    }
}