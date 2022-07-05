package com.example.sass.presentation.screens.tabs.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.sass.R
import com.example.sass.databinding.MainTabFragmentBinding

class MainTabFragment : Fragment(R.layout.main_tab_fragment) {

    private lateinit var binding: MainTabFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainTabFragmentBinding.bind(view)
    }
}