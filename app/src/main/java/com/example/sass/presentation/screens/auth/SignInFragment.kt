package com.example.sass.presentation.screens.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.sass.R
import com.example.sass.databinding.SingInFragmentBinding
import com.google.android.material.snackbar.Snackbar

class SignInFragment : Fragment(R.layout.sing_in_fragment) {

    private lateinit var binding: SingInFragmentBinding

    private val regex = Regex(PATTERN)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SingInFragmentBinding.bind(view)

        binding.editTextLogin.addTextChangedListener(LoginFormatTextWatcher(binding.editTextLogin))
        binding.textInputLayoutPassword.isEndIconVisible = false

        binding.editTextPassword.setOnFocusChangeListener { v, hasFocus ->
            binding.textInputLayoutPassword.isEndIconVisible = hasFocus
        }

        setupButton()

    }

    private fun setupButton() {
        binding.buttonSignIn.setOnClickListener {
            val login = binding.editTextLogin.text.toString()
            val password = binding.editTextPassword.text.toString()

            validateSignInData(login, password)

            val formatLogin = formatLogin(login)

//            eventRequest (login, password)

            binding.editTextPassword.clearFocus()
            binding.editTextLogin.clearFocus()
        }
    }

    private fun validateSignInData(login: String, password: String) {
        when {
            login.isBlank() -> {
//                blank field error event
                Snackbar.make(binding.root, "login is blank", Snackbar.LENGTH_SHORT).show()
            }
            password.isBlank() -> {
//                blank field error event
                Snackbar.make(binding.root, "password is blank", Snackbar.LENGTH_SHORT).show()
            }
            login.length != 16 -> {
                Snackbar.make(binding.root, "login length not 12", Snackbar.LENGTH_SHORT).show()
            }
            !login.matches(regex)  -> {
//                error event
                Snackbar.make(binding.root, "not much regex", Snackbar.LENGTH_SHORT).show()
            }

            password.length !in 6..255 -> {
//                error event
                Snackbar.make(binding.root, "password length < 6 or > 255", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun formatLogin(login: String): String {
        val text = login.replace("[^\\d]".toRegex(), "")
        return "+7$text"
    }


    companion object {
        private const val PATTERN = "[+]?[78]?[() 0-9-]+"
    }
}