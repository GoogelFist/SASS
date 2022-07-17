package com.example.sass.presentation.screens.auth

import com.example.sass.R
import com.example.sass.databinding.AuthFragmentBinding

object AuthHelper {

    fun configPasswordTransformationText(binding: AuthFragmentBinding) {
        with(binding) {
            editTextPassword.transformationMethod = BigPointTransformationMethod()
            editTextPassword.letterSpacing = PASSWORD_MASK_LETTER_SPACING

            txtInpLayoutPassword.setEndIconOnClickListener {
                if (editTextPassword.transformationMethod is BigPointTransformationMethod) {

                    editTextPassword.transformationMethod = null

                    txtInpLayoutPassword.endIconDrawable =
                        binding.root.context.getDrawable(R.drawable.ic_hide_password)

                    editTextPassword.letterSpacing = DEFAULT_PASSWORD_MASK_LETTER_SPACING

                    editTextPassword.text?.let { text ->
                        if (text.isNotEmpty()) {
                            editTextPassword.setSelection(text.length)
                        }
                    }
                } else {
                    editTextPassword.transformationMethod = BigPointTransformationMethod()

                    txtInpLayoutPassword.endIconDrawable =
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