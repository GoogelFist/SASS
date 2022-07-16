package com.example.sass.presentation.screens.auth

import android.content.Context
import android.content.res.ColorStateList
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
import com.google.android.material.snackbar.Snackbar
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
        requireActivity().viewModelStore.clear()
        viewModel.obtainEvent(AuthEvent.OnClearUserData)
    }

    private fun configLoginField() {
        binding.editTextLogin.addTextChangedListener(LoginFormatTextWatcher(binding.editTextLogin))
    }

    private fun configPasswordField() {
        configPasswordVisibleEndIcon()
        AuthHelper.configPasswordTransformationText(binding)
    }


    private fun configPasswordVisibleEndIcon() {
        binding.textInputLayoutPassword.isEndIconVisible = false

        binding.editTextPassword.doOnTextChanged { text, _, _, _ ->
            text?.let {
                binding.textInputLayoutPassword.isEndIconVisible = it.isNotEmpty()
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

        viewModel.authState.observe(viewLifecycleOwner) { state ->
            when (state) {
                AuthState.SigningIn -> configSignInState()
                AuthState.SignedIn -> navigateToTabFragment()
                AuthState.SingInError -> configSignInErrorState()
                is AuthState.SingInValidateError -> configInValidateState(state)
            }
        }
    }

    private fun configSignInState() {
        with(binding) {
            textInputLayoutLogin.error = null
            textInputLayoutPassword.error = null

            interfaceBlocker.visibility = View.VISIBLE

            progressBarSignInButton.visibility = View.VISIBLE
            buttonSignIn.text = null

            textInputLayoutLogin.isErrorEnabled = false
            textInputLayoutPassword.isErrorEnabled = false
        }
    }

    private fun navigateToTabFragment() {
        findNavController().navigate(R.id.action_signInFragment_to_tabsFragment)
    }

    private fun configSignInErrorState() {
        with(binding) {
            textInputLayoutLogin.error = getString(R.string.empty_error_message)
            textInputLayoutPassword.error = getString(R.string.empty_error_message)

            textInputLayoutLogin.setErrorTextAppearance(R.style.Text_MontserratRegular_0_Error)
            textInputLayoutPassword.setErrorTextAppearance(R.style.Text_MontserratRegular_0_Error)

            textInputLayoutLogin.isErrorEnabled = true
            textInputLayoutPassword.isErrorEnabled = true

            interfaceBlocker.visibility = View.GONE

            progressBarSignInButton.visibility = View.GONE
            buttonSignIn.text = requireContext().getText(R.string.button_sign_in_text)

            Snackbar.make(root, R.string.sign_in_error_text, Snackbar.LENGTH_LONG)
                .setAnchorView(buttonSignIn).show()
        }
    }

    private fun configInValidateState(state: AuthState.SingInValidateError) {
        with(binding) {
            textInputLayoutPassword.setErrorTextAppearance(R.style.Text_MontserratRegular_12)
            textInputLayoutLogin.setErrorTextAppearance(R.style.Text_MontserratRegular_12)

            textInputLayoutLogin.setErrorTextColor(
                ColorStateList.valueOf(requireContext().getColor(R.color.edit_text_error_text_color))
            )
            textInputLayoutPassword.setErrorTextColor(
                ColorStateList.valueOf(requireContext().getColor(R.color.edit_text_error_text_color))
            )

            textInputLayoutLogin.isErrorEnabled = true
            textInputLayoutPassword.isErrorEnabled = true

            when (state.loginErrorSubState) {
                ErrorLoginSubState.IsBlank -> {
                    textInputLayoutLogin.error =
                        requireContext().getText(R.string.login_blank_error_text)
                }
                ErrorLoginSubState.Invalidate -> {
                    textInputLayoutLogin.error =
                        requireContext().getText(R.string.login_wrong_format_error_text)
                }
                ErrorLoginSubState.Default -> {
                    textInputLayoutLogin.error = null
                    textInputLayoutLogin.isErrorEnabled = false
                }
            }

            when (state.passwordErrorSubState) {
                ErrorPasswordSubState.IsBlank -> {
                    textInputLayoutPassword.error =
                        requireContext().getText(R.string.password_blank_error_text)
                }
                ErrorPasswordSubState.Invalidate -> {
                    textInputLayoutPassword.error =
                        requireContext().getText(R.string.password_wrong_format_error_text)
                }
                ErrorPasswordSubState.Default -> {
                    textInputLayoutPassword.error = null
                    textInputLayoutPassword.isErrorEnabled = false
                }
            }
        }
    }
}