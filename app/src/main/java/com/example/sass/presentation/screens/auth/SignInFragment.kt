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

        configLoginField()
        configPasswordField()
        setupButton()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    private fun setupButton() {
        with(binding) {
            buttonSignIn.setOnClickListener { view ->
                val login = editTextLogin.text.toString()
                val password = editTextPassword.text.toString()

                validateSignInData(login, password)

                editTextPassword.clearFocus()
                editTextLogin.clearFocus()
            }
        }
    }

    private fun observeViewModel() {
        val authHelper = AuthHelper(binding, requireContext())

        viewModel.authState.observe(viewLifecycleOwner) { state ->
            when (state) {
                AuthState.SigningInState -> {
                    viewModel.obtainEvent(AuthEvent.OnInitLoginErrorEvent)
                    viewModel.obtainEvent(AuthEvent.OnInitPasswordErrorEvent)

                    authHelper.configSigningState()
                }

                AuthState.SignedInState -> {
                    navigateToTabFragment()
                }

                AuthState.SingInErrorState -> {
                    authHelper.configErrorState()
                }

                is AuthState.InvalidateLoginErrorState -> {
                    viewModel.obtainEvent(AuthEvent.OnInitPasswordErrorEvent)
                    binding.textInputLayoutLogin.error = state.message
                }

                is AuthState.InvalidatePasswordErrorState -> {
                    viewModel.obtainEvent(AuthEvent.OnInitLoginErrorEvent)
                    binding.textInputLayoutPassword.error = state.message
                }

                is AuthState.ValidatedState -> {
                    val login = authHelper.formatLogin(state.login)
                    val password = state.password

                    viewModel.obtainEvent(AuthEvent.OnSignInEvent(login, password))
                }

                AuthState.InitLoginErrorState -> {
                    authHelper.configInitErrorState(binding.textInputLayoutLogin)
                }

                AuthState.InitPasswordErrorState -> {
                    authHelper.configInitErrorState(binding.textInputLayoutPassword)
                }
            }
        }
    }

    private fun navigateToTabFragment() {
        findNavController().navigate(R.id.action_signInFragment_to_tabsFragment)
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

    private fun configPasswordVisibleEndIcon() {
        binding.textInputLayoutPassword.isEndIconVisible = false

        binding.editTextPassword.doOnTextChanged { text, _, _, _ ->
            text?.let {
                binding.textInputLayoutPassword.isEndIconVisible = it.isNotEmpty()
            }
        }
    }

    private fun validateSignInData(login: String, password: String) {
        val regex = Regex(PATTERN)

        when {
            login.isBlank() -> {
                val message = requireContext().getString(R.string.login_blank_error_text)

                viewModel.obtainEvent(AuthEvent.OnInvalidateLoginEvent(message))
            }
            password.isBlank() -> {
                val message = requireContext().getString(R.string.password_blank_error_text)

                viewModel.obtainEvent(AuthEvent.OnInvalidatePasswordEvent(message))
            }
            !login.matches(regex) || login.length != LOGIN_LENGTH_CONSTRAINT -> {
                val message = requireContext().getString(R.string.login_wrong_format_error_text)

                viewModel.obtainEvent(AuthEvent.OnInvalidateLoginEvent(message))
            }
            password.length !in PASSWORD_MIN_LENGTH_CONSTRAINT..PASSWORD_MAN_LENGTH_CONSTRAINT -> {
                val message = requireContext().getString(R.string.password_wrong_format_error_text)

                viewModel.obtainEvent(AuthEvent.OnInvalidatePasswordEvent(message))
            }
            else -> {
                viewModel.obtainEvent(AuthEvent.OnValidateSignInData(login, password))
            }
        }
    }

    companion object {
        private const val PATTERN = "[+]?[78]?[() 0-9-]+"

        private const val LOGIN_LENGTH_CONSTRAINT = 16

        private const val PASSWORD_MIN_LENGTH_CONSTRAINT = 6
        private const val PASSWORD_MAN_LENGTH_CONSTRAINT = 255
    }
}