package com.example.sass.presentation.screens.tabs.main.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.sass.R
import com.example.sass.domain.models.PicturesItem

class PicturesMainAdapter :
    ListAdapter<PicturesItem, PicturesItemViewHolder>(PicturesItemDiffCallBack()) {

    lateinit var onPictureClickListener: (pictureId: String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_post_item, parent, false)

        val viewHolder = PicturesItemViewHolder(view)

        view.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != NO_POSITION) {
                val pictureId = currentList[position].id
                onPictureClickListener.invoke(pictureId)
            }
            onPictureClickListener
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