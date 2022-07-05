package com.example.sass.presentation.screens.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.sass.R
import com.example.sass.databinding.SingInFragmentBinding

class SignInFragment : Fragment(R.layout.sing_in_fragment) {

    private lateinit var binding: SingInFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SingInFragmentBinding.bind(view)
    }
}