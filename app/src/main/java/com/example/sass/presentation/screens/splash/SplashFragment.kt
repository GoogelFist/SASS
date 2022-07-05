package com.example.sass.presentation.screens.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sass.R
import com.example.sass.databinding.FragmentSplashBinding
import com.example.sass.presentation.screens.MainActivity
import com.example.sass.presentation.screens.MainActivityArgs

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var binding: FragmentSplashBinding

    // TODO: will need create fabric
    private val viewModel by activityViewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)
        viewModel.launchMainScreenEvent.observe(viewLifecycleOwner) { launchMainScreen(it) }
    }

    // TODO: will need think about delay
    private fun launchMainScreen(isSignedIn: Boolean) {
        Handler(Looper.myLooper()!!).postDelayed({
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

            val args = MainActivityArgs(isSignedIn)
            intent.putExtras(args.toBundle())

            startActivity(intent)
        }, 500)
    }
}