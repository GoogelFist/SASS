package com.example.sass.presentation.screens.auth

import com.example.sass.R
import com.example.sass.databinding.SingInFragmentBinding

object AuthHelper {

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

    private const val PASSWORD_MASK_LETTER_SPACING = 0.3f
    private const val DEFAULT_PASSWORD_MASK_LETTER_SPACING = 0f
}