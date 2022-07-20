package com.example.sass.presentation.screens.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sass.component
import com.example.sass.databinding.FragmentSplashBinding
import com.example.sass.presentation.screens.MainActivity
import com.example.sass.presentation.screens.MainActivityArgs
import com.example.sass.presentation.screens.splash.models.SplashState
import javax.inject.Inject

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding
        get() = _binding!!

    @Inject
    lateinit var splashViewModelFactory: SplashViewModelFactory

    private val viewModel by activityViewModels<SplashViewModel> { splashViewModelFactory }

    override fun onAttach(context: Context) {
        context.component.inject(this)
        super.onAttach(context)
    }

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

        viewModel.splashState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SplashState.LaunchingMainScreen -> launchMainScreen(state.isSignedIn)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchMainScreen(isSignedIn: Boolean) {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val args = MainActivityArgs(isSignedIn)
        intent.putExtras(args.toBundle())

        startActivity(intent)
    }
}