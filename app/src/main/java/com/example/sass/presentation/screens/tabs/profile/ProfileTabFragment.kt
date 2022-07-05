package com.example.sass.presentation.screens.tabs.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.sass.R
import com.example.sass.databinding.ProfileTabFragmentBinding

class ProfileTabFragment : Fragment(R.layout.profile_tab_fragment) {

    private lateinit var binding: ProfileTabFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProfileTabFragmentBinding.bind(view)
    }
}