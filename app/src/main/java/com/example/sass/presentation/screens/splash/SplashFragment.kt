package com.example.sass.presentation.screens.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sass.R
import com.example.sass.databinding.FragmentSplashBinding
import com.example.sass.presentation.screens.MainActivity
import com.example.sass.presentation.screens.MainActivityArgs

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding
        get() = _binding!!

    // TODO: will need create fabric
    private val viewModel by activityViewModels<SplashViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.launchMainScreenEvent.observe(viewLifecycleOwner) { launchMainScreen(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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