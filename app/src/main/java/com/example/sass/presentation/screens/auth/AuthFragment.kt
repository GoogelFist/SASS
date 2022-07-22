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
import com.example.sass.databinding.AuthFragmentBinding
import com.example.sass.presentation.screens.auth.models.AuthEvent
import com.example.sass.presentation.screens.auth.models.AuthState
import com.example.sass.presentation.screens.auth.models.ErrorLoginSubState
import com.example.sass.presentation.screens.auth.models.ErrorPasswordSubState
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class AuthFragment : Fragment() {

    private var _binding: AuthFragmentBinding? = null
    private val binding: AuthFragmentBinding
        get() = _binding!!

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory

    private val viewModel by activityViewModels<AuthViewModel> { authViewModelFactory }

    override fun onAttach(context: Context) {
        context.component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AuthFragmentBinding.inflate(inflater, container, false)
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
        binding.txtInpLayoutPassword.isEndIconVisible = false

        binding.editTextPassword.doOnTextChanged { text, _, _, _ ->
            text?.let {
                binding.txtInpLayoutPassword.isEndIconVisible = it.isNotEmpty()
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
                AuthState.SigningIn -> configSigningInState()
                AuthState.SignedIn -> navigateToTabFragment()
                AuthState.SingInError -> configSignInErrorState()
                is AuthState.SingInValidateError -> configInValidateState(state)
                AuthState.Error -> configErrorState()
            }
        }
    }

    private fun configSigningInState() {
        with(binding) {
            txtInpLayoutLogin.error = null
            txtInpLayoutPassword.error = null

            interfaceBlocker.visibility = View.VISIBLE

            buttonSignIn.isLoading = true

            txtInpLayoutLogin.isErrorEnabled = false
            txtInpLayoutPassword.isErrorEnabled = false
        }
    }

    private fun navigateToTabFragment() {
        findNavController().navigate(R.id.action_authFragment_to_tabsFragment)
    }

    private fun configSignInErrorState() {
        with(binding) {
            txtInpLayoutLogin.error = getString(R.string.empty_error_message)
            txtInpLayoutPassword.error = getString(R.string.empty_error_message)

            txtInpLayoutLogin.setErrorTextAppearance(R.style.Text_MontserratRegular_0_Error)
            txtInpLayoutPassword.setErrorTextAppearance(R.style.Text_MontserratRegular_0_Error)

            txtInpLayoutLogin.isErrorEnabled = true
            txtInpLayoutPassword.isErrorEnabled = true

            interfaceBlocker.visibility = View.GONE

            buttonSignIn.isLoading = false

            Snackbar.make(root, R.string.sign_in_error_text, Snackbar.LENGTH_LONG)
                .setAnchorView(buttonSignIn).show()
        }
    }

    private fun configInValidateState(state: AuthState.SingInValidateError) {
        with(binding) {
            txtInpLayoutPassword.setErrorTextAppearance(R.style.Text_MontserratRegular_12)
            txtInpLayoutLogin.setErrorTextAppearance(R.style.Text_MontserratRegular_12)

            val errorTextColor = requireContext().getColor(R.color.edit_text_error_text_color)

            txtInpLayoutLogin.setErrorTextColor(ColorStateList.valueOf(errorTextColor))
            txtInpLayoutPassword.setErrorTextColor(ColorStateList.valueOf(errorTextColor))

            txtInpLayoutLogin.isErrorEnabled = true
            txtInpLayoutPassword.isErrorEnabled = true

            when (state.loginErrorSubState) {
                ErrorLoginSubState.IsBlank -> {
                    txtInpLayoutLogin.error = getText(R.string.login_blank_error_text)
                }
                ErrorLoginSubState.Invalidate -> {
                    txtInpLayoutLogin.error = getText(R.string.login_wrong_format_error_text)
                }
                ErrorLoginSubState.Default -> {
                    txtInpLayoutLogin.error = null
                    txtInpLayoutLogin.isErrorEnabled = false
                }
            }

            when (state.passwordErrorSubState) {
                ErrorPasswordSubState.IsBlank -> {
                    txtInpLayoutPassword.error = getText(R.string.password_blank_error_text)
                }
                ErrorPasswordSubState.Invalidate -> {
                    txtInpLayoutPassword.error = getText(R.string.password_wrong_format_error_text)
                }
                ErrorPasswordSubState.Default -> {
                    txtInpLayoutPassword.error = null
                    txtInpLayoutPassword.isErrorEnabled = false
                }
            }
        }
    }

    private fun configErrorState() {
        with(binding) {
            txtInpLayoutPassword.error = null
            txtInpLayoutPassword.isErrorEnabled = false

            txtInpLayoutLogin.error = null
            txtInpLayoutLogin.isErrorEnabled = false

            interfaceBlocker.visibility = View.GONE

            buttonSignIn.isLoading = false

            Snackbar.make(root, R.string.auth_error_text, Snackbar.LENGTH_LONG)
                .setAnchorView(buttonSignIn).show()
        }
    }
}