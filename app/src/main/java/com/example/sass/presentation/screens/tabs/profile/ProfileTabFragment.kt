package com.example.sass.presentation.screens.tabs.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.sass.R
import com.example.sass.component
import com.example.sass.databinding.ProfileTabFragmentBinding
import com.example.sass.presentation.screens.tabs.profile.models.ProfileEvent
import com.example.sass.presentation.screens.tabs.profile.models.ProfileState
import javax.inject.Inject

class ProfileTabFragment : Fragment(R.layout.profile_tab_fragment) {

    private var _binding: ProfileTabFragmentBinding? = null
    private val binding: ProfileTabFragmentBinding
        get() = _binding!!

    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory

    private val viewModel by activityViewModels<ProfileViewModel> {
        profileViewModelFactory
    }


    override fun onAttach(context: Context) {
        context.component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfileTabFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configButton()

        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configButton() {
        binding.buttonSignOut.setOnClickListener {
            viewModel.obtainEvent(ProfileEvent.OnSignOutEvent)
        }
    }

    private fun observeViewModel() {
        viewModel.profileState.observe(viewLifecycleOwner) { state ->
            when (state) {
                ProfileState.SigningOutState -> {
                    binding.buttonSignOut.text = null
                    binding.tvProfileErrorSnack.visibility = View.GONE
                    binding.progressBarSignOutButton.visibility = View.VISIBLE
                }
                ProfileState.SignedOutState -> {
                    binding.buttonSignOut.text = requireContext().getText(R.string.button_sign_out_text)
                    binding.tvProfileErrorSnack.visibility = View.GONE
                    binding.progressBarSignOutButton.visibility = View.GONE
                }
                ProfileState.SingOutErrorState -> {
                    binding.buttonSignOut.text = requireContext().getText(R.string.button_sign_out_text)
                    binding.tvProfileErrorSnack.visibility = View.VISIBLE
                    binding.progressBarSignOutButton.visibility = View.GONE
                }
                ProfileState.IncorrectTokenState -> {
                    // TODO:will need to clear user data and token
                    navigateToSignInFragment()
                }
            }
        }
    }

    private fun navigateToSignInFragment() {
        findNavController().navigate(R.id.action_profileTabFragment_to_signInFragment2)
    }
}