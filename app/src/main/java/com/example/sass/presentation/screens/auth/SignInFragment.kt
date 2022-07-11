package com.example.sass.presentation.screens.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.sass.R
import com.example.sass.component
import com.example.sass.databinding.SingInFragmentBinding
import com.example.sass.presentation.screens.auth.models.AuthEvent
import com.example.sass.presentation.screens.auth.models.AuthState
import com.example.sass.presentation.screens.auth.models.ErrorLoginSubState
import com.example.sass.presentation.screens.auth.models.ErrorPasswordSubState
import javax.inject.Inject

class SignInFragment : Fragment() {

    private var _binding: SingInFragmentBinding? = null
    private val binding: SingInFragmentBinding
        get() = _binding!!

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory

    private val viewModel by activityViewModels<AuthViewModel> {
        authViewModelFactory
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
        _binding = SingInFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFragment()
        configLoginField()
        configPasswordField()
        setupButton()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initFragment() {
        viewModel.obtainEvent(AuthEvent.OnDefaultState)
    }

    private fun configLoginField() {
        binding.textInputLayoutLogin.errorIconDrawable = null
        binding.editTextLogin.addTextChangedListener(LoginFormatTextWatcher(binding.editTextLogin))
    }

    private fun configPasswordField() {
        binding.textInputLayoutPassword.errorIconDrawable = null
        configPasswordVisibleEndIcon()
        configPasswordTransformationText()
    }


    private fun configPasswordVisibleEndIcon() {
        binding.textInputLayoutPassword.isEndIconVisible = false

        binding.editTextPassword.doOnTextChanged { text, _, _, _ ->
            text?.let {
                binding.textInputLayoutPassword.isEndIconVisible = it.isNotEmpty()
            }
        }
    }

    private fun configPasswordTransformationText() {
        with(binding) {
            editTextPassword.transformationMethod = BigPointTransformationMethod()

            textInputLayoutPassword.setEndIconOnClickListener {
                if (editTextPassword.transformationMethod is BigPointTransformationMethod) {

                    editTextPassword.transformationMethod = null

                    textInputLayoutPassword.endIconDrawable =
                        requireContext().getDrawable(R.drawable.ic_hide_password)

                    editTextPassword.text?.let { text ->
                        if (text.isNotEmpty()) {
                            editTextPassword.setSelection(text.length)
                        }
                    }
                } else {
                    editTextPassword.transformationMethod = BigPointTransformationMethod()

                    textInputLayoutPassword.endIconDrawable =
                        requireContext().getDrawable(R.drawable.ic_show_password)

                    editTextPassword.text?.let { text ->
                        if (text.isNotEmpty()) {
                            editTextPassword.setSelection(text.length)
                        }
                    }
                }
            }
        }
    }

    private fun setupButton() {
        with(binding) {
            buttonSignIn.setOnClickListener { view ->
                val login = editTextLogin.text.toString()
                val password = editTextPassword.text.toString()

                viewModel.obtainEvent(AuthEvent.OnSignIn(login, password))

                editTextPassword.clearFocus()
                editTextLogin.clearFocus()
            }
        }
    }

    private fun observeViewModel() {
        val authHelper = AuthHelper(binding, requireContext())

        viewModel.authState.observe(viewLifecycleOwner) { state ->
            when (state) {

                AuthState.Default -> authHelper.configDefaultState()

                AuthState.SigningIn -> authHelper.configSigningState()

                AuthState.SignedIn -> navigateToTabFragment()

                AuthState.SingInError -> authHelper.configSignInErrorState()

                is AuthState.SingInLoginError -> {
                    when (state.subState) {
                        ErrorLoginSubState.Default -> {
                            authHelper.configInitErrorState(binding.textInputLayoutLogin)
                        }

                        ErrorLoginSubState.IsBlank -> {
                            binding.textInputLayoutLogin.error =
                                requireContext().getString(R.string.login_blank_error_text)
                        }

                        ErrorLoginSubState.Invalidate -> {
                            binding.textInputLayoutLogin.error =
                                requireContext().getString(R.string.login_wrong_format_error_text)
                        }
                    }
                }

                is AuthState.SingInPasswordError -> {
                    when (state.subState) {
                        ErrorPasswordSubState.Default -> {
                            authHelper.configInitErrorState(binding.textInputLayoutPassword)
                        }

                        ErrorPasswordSubState.IsBlank -> {
                            binding.textInputLayoutPassword.error =
                                requireContext().getString(R.string.password_blank_error_text)
                        }

                        ErrorPasswordSubState.Invalidate -> {
                            binding.textInputLayoutPassword.error =
                                requireContext().getString(R.string.password_wrong_format_error_text)
                        }
                    }
                }
            }
        }
    }

    private fun navigateToTabFragment() {
        findNavController().navigate(R.id.action_signInFragment_to_tabsFragment)
    }
}