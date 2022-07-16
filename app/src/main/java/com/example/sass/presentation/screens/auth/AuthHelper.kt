package com.example.sass.presentation.screens.auth

import com.example.sass.R
import com.example.sass.databinding.SingInFragmentBinding
import com.example.sass.presentation.screens.auth.models.ErrorLoginSubState
import com.example.sass.presentation.screens.auth.models.ErrorPasswordSubState

object AuthHelper {

    fun validateLogin(login: String): ErrorLoginSubState {
        val regex = Regex(PATTERN)

        return when {
            login.isBlank() -> ErrorLoginSubState.IsBlank

            !login.matches(regex) || login.length != LOGIN_LENGTH_CONSTRAINT -> {
                ErrorLoginSubState.Invalidate
            }
            else -> ErrorLoginSubState.Default
        }
    }

    fun validatePassword(password: String): ErrorPasswordSubState {
        return when {
            password.isBlank() -> ErrorPasswordSubState.IsBlank

            password.length !in PASSWORD_MIN_LENGTH_CONSTRAINT..PASSWORD_MAN_LENGTH_CONSTRAINT -> {
                ErrorPasswordSubState.Invalidate
            }
            else -> ErrorPasswordSubState.Default
        }
    }

    fun formatPhone(login: String): String {
        val text = login.replace("[^\\d]".toRegex(), REPLACEMENT)
        return "$PHONE_COUNTRY_CODE$text"
    }

    fun configPasswordTransformationText(binding: SingInFragmentBinding) {
        with(binding) {
            editTextPassword.transformationMethod = BigPointTransformationMethod()
            editTextPassword.letterSpacing = PASSWORD_MASK_LETTER_SPACING

            textInputLayoutPassword.setEndIconOnClickListener {
                if (editTextPassword.transformationMethod is BigPointTransformationMethod) {

                    editTextPassword.transformationMethod = null

                    textInputLayoutPassword.endIconDrawable =
                        binding.root.context.getDrawable(R.drawable.ic_hide_password)

                    editTextPassword.letterSpacing = DEFAULT_PASSWORD_MASK_LETTER_SPACING

                    editTextPassword.text?.let { text ->
                        if (text.isNotEmpty()) {
                            editTextPassword.setSelection(text.length)
                        }
                    }
                } else {
                    editTextPassword.transformationMethod = BigPointTransformationMethod()

                    textInputLayoutPassword.endIconDrawable =
                        binding.root.context.getDrawable(R.drawable.ic_show_password)
                    editTextPassword.letterSpacing =
                        PASSWORD_MASK_LETTER_SPACING

                    editTextPassword.text?.let { text ->
                        if (text.isNotEmpty()) {
                            editTextPassword.setSelection(text.length)
                        }
                    }
                }
            }
        }
    }

    private const val PATTERN = "[+]?[78]?[() 0-9-]+"

    private const val LOGIN_LENGTH_CONSTRAINT = 16

    private const val PASSWORD_MIN_LENGTH_CONSTRAINT = 6
    private const val PASSWORD_MAN_LENGTH_CONSTRAINT = 255

    private const val PHONE_COUNTRY_CODE = "+7"

    private const val REPLACEMENT = ""

    private const val PASSWORD_MASK_LETTER_SPACING = 0.3f
    private const val DEFAULT_PASSWORD_MASK_LETTER_SPACING = 0f
}