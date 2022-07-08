package com.example.sass.presentation.screens.auth

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import com.example.sass.R
import com.example.sass.databinding.SingInFragmentBinding
import com.google.android.material.textfield.TextInputLayout

class AuthHelper(private val binding: SingInFragmentBinding,private val context: Context) {

    fun configSigningState() {
        with(binding) {
            tvErrorSnack.visibility = View.GONE

            textInputLayoutLogin.defaultHintTextColor = ColorStateList.valueOf(
                context.getColor(R.color.edit_text_hint_color)
            )
            textInputLayoutPassword.defaultHintTextColor = ColorStateList.valueOf(
                context.getColor(R.color.edit_text_hint_color)
            )

            buttonSignIn.text = null
            buttonSignIn.isClickable = false

            progressBarSignInButton.visibility = View.VISIBLE

            textInputLayoutLogin.isEnabled = false
            textInputLayoutLogin.error = null

            textInputLayoutPassword.isEnabled = false
            textInputLayoutPassword.error = null
        }
    }

    fun configErrorState() {
        with(binding) {
            tvErrorSnack.visibility = View.VISIBLE

            textInputLayoutLogin.defaultHintTextColor = ColorStateList.valueOf(
                context.getColor(R.color.edit_text_error_hint_color)
            )
            textInputLayoutPassword.defaultHintTextColor = ColorStateList.valueOf(
                context.getColor(R.color.edit_text_error_hint_color)
            )

            textInputLayoutLogin.boxStrokeErrorColor = ColorStateList.valueOf(
                context.getColor(R.color.edit_text_error_bottom_stroke_color)
            )
            textInputLayoutPassword.boxStrokeErrorColor = ColorStateList.valueOf(
                context.getColor(R.color.edit_text_error_bottom_stroke_color)
            )

            textInputLayoutPassword.error = EMPTY_STRING
            textInputLayoutLogin.error = EMPTY_STRING

            buttonSignIn.text = context.getText(R.string.button_sign_in_text)
            buttonSignIn.isClickable = true

            progressBarSignInButton.visibility = View.GONE

            textInputLayoutLogin.isEnabled = true
            textInputLayoutPassword.isEnabled = true
        }
    }

    fun configInitErrorState(textInputLayout: TextInputLayout) {
        textInputLayout.defaultHintTextColor =
            ColorStateList.valueOf(context.getColor(R.color.edit_text_hint_color))
        textInputLayout.boxStrokeErrorColor = ColorStateList.valueOf(
            context.getColor(R.color.edit_text_init_error_bottom_stroke_color)
        )
        textInputLayout.error = null
    }

    fun formatLogin(login: String): String {
        val text = login.replace("[^\\d]".toRegex(), "")
        return "${PHONE_COUNTRY_CODE}$text"
    }

    companion object {
        private const val EMPTY_STRING = " "

        private const val PHONE_COUNTRY_CODE = "+7"
    }
}