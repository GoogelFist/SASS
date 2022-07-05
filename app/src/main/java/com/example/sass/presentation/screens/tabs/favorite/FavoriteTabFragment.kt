package com.example.sass.presentation.screens.tabs.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.sass.R
import com.example.sass.databinding.FavoriteTabFragmentBinding

class FavoriteTabFragment: Fragment(R.layout.favorite_tab_fragment) {

    private lateinit var binding: FavoriteTabFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FavoriteTabFragmentBinding.bind(view)
    }
}