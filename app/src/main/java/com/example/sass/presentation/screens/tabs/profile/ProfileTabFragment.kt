package com.example.sass.presentation.screens.tabs.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.example.sass.R
import com.example.sass.component
import com.example.sass.databinding.ProfileTabFragmentBinding
import com.example.sass.domain.models.UserInfo
import com.example.sass.presentation.screens.tabs.SingleDialogFragment
import com.example.sass.presentation.screens.tabs.profile.models.ProfileEvent
import com.example.sass.presentation.screens.tabs.profile.models.ProfileState
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class ProfileTabFragment : Fragment() {

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

        viewModel.obtainEvent(ProfileEvent.OnInit)

        configButton()
        observeUserInfoViewModel()
        observeStateViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configButton() {
        val message = requireContext().getText(R.string.dialog_sign_out_message_text)

        binding.buttonSignOut.setOnClickListener {
            SingleDialogFragment.show(parentFragmentManager, message.toString())
        }
        SingleDialogFragment.setupListener(parentFragmentManager, this) { answer ->
            if (answer) {
                viewModel.obtainEvent(ProfileEvent.OnSignOut)
            }
        }
    }

    private fun observeUserInfoViewModel() {
        viewModel.userInfo.observe(viewLifecycleOwner) { userInfo ->

            with(binding) {
                bindUserInfoFields(userInfo)
            }
        }
    }

    private fun ProfileTabFragmentBinding.bindUserInfoFields(
        userInfo: UserInfo
    ) {
        if (userInfo.avatar.isNotBlank()) {
            Glide.with(requireContext()).load(userInfo.avatar).centerCrop().into(ivAvatar)
        } else {
            ivAvatar.visibility = View.INVISIBLE
        }

        if (userInfo.firstName.isNotBlank() && userInfo.lastName.isNotBlank()) {
            tvFirstName.text = userInfo.firstName
            tvLastName.text = userInfo.lastName
        } else {
            blockName.visibility = View.INVISIBLE
        }

        if (userInfo.about.isNotBlank()) {
            tvAbout.text = userInfo.about
        } else {
            tvAbout.visibility = View.INVISIBLE
        }

        checkAbsentField(userInfo.city, tvCity, blockCity)
        checkAbsentField(userInfo.phone, tvPhone, blockPhone)
        checkAbsentField(userInfo.email, tvEmail, blockEmail)
    }

    private fun checkAbsentField(field: String, textView: TextView, block: LinearLayoutCompat) {
        if (field.isNotBlank()) {
            textView.text = field
        } else {
            block.visibility = View.INVISIBLE
        }
    }


    private fun observeStateViewModel() {
        viewModel.profileState.observe(viewLifecycleOwner) { state ->
            when (state) {
                ProfileState.Default -> configDefaultState()
                ProfileState.SigningOut -> configSigningOutState()
                ProfileState.SignedOut -> navigateToAuthFragment()
                ProfileState.SingOutError -> configErrorState()
                ProfileState.IncorrectToken -> navigateToAuthFragment()
            }
        }
    }

    private fun configDefaultState() {
        binding.buttonSignOut.isLoading = false
    }

    private fun configSigningOutState() {
        binding.buttonSignOut.isLoading = true
    }

    private fun configErrorState() {
        binding.buttonSignOut.isLoading = false
        Snackbar.make(binding.root, R.string.sign_out_error_text, Snackbar.LENGTH_LONG)
            .setAnchorView(binding.buttonSignOut).show()
    }

    private fun navigateToAuthFragment() {
        getRootNavController().navigate(R.id.action_tabsFragment_to_authFragment)
    }

    private fun getRootNavController(): NavController {
        val navHost = requireActivity()
            .supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        return navHost.navController
    }
}