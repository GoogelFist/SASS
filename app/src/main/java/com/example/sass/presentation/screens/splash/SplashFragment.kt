package com.example.sass.presentation.screens.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.sass.R
import com.example.sass.databinding.FragmentSplashBinding

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var binding: FragmentSplashBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)
    }
}