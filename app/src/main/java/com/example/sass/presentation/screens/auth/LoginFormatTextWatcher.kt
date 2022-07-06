package com.example.sass.presentation.screens.auth

import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.widget.EditText

class LoginFormatTextWatcher(private val editText: EditText) : PhoneNumberFormattingTextWatcher() {

    private var backspacingFlag = false
    private var editedFlag = false
    private var cursorComplement = COMPLEMENT_INIT_VALUE

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        cursorComplement = s.length - editText.selectionStart
        backspacingFlag = count > after
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
        val string = s.toString()

        val phone = string.replace("[^\\d]".toRegex(), "")

        if (!editedFlag) {

            when {
                phone.length >= INDEX_THIRD_PATH_PHONE_NUMBER && !backspacingFlag -> {
                    editedFlag = true

                    val ans = buildString {
                        append(" (")
                        append(phone.substring(INDEX_PHONE_CODE, INDEX_FIRST_PATH_PHONE_NUMBER))
                        append(") ")
                        append(
                            phone.substring(
                                INDEX_FIRST_PATH_PHONE_NUMBER,
                                INDEX_SECOND_PATH_PHONE_NUMBER
                            )
                        )
                        append(" ")
                        append(
                            phone.substring(
                                INDEX_SECOND_PATH_PHONE_NUMBER,
                                INDEX_THIRD_PATH_PHONE_NUMBER
                            )
                        )
                        append(" ")
                        append(phone.substring(INDEX_THIRD_PATH_PHONE_NUMBER))
                    }

                    editText.setText(ans)
                    editText.text.length.let { editText.setSelection((it - cursorComplement)) }
                }
                phone.length >= INDEX_SECOND_PATH_PHONE_NUMBER && !backspacingFlag -> {
                    editedFlag = true

                    val ans = buildString {
                        append(" (")
                        append(phone.substring(INDEX_PHONE_CODE, INDEX_FIRST_PATH_PHONE_NUMBER))
                        append(") ")
                        append(
                            phone.substring(
                                INDEX_FIRST_PATH_PHONE_NUMBER,
                                INDEX_SECOND_PATH_PHONE_NUMBER
                            )
                        )
                        append(" ")
                        append(phone.substring(INDEX_SECOND_PATH_PHONE_NUMBER))
                    }

                    editText.setText(ans)
                    editText.text.length.let { editText.setSelection((it - cursorComplement)) }

                }
                phone.length >= INDEX_FIRST_PATH_PHONE_NUMBER && !backspacingFlag -> {
                    editedFlag = true

                    val ans = buildString {
                        append(" (")
                        append(phone.substring(INDEX_PHONE_CODE, INDEX_FIRST_PATH_PHONE_NUMBER))
                        append(") ")
                        append(phone.substring(INDEX_FIRST_PATH_PHONE_NUMBER))
                    }

                    editText.setText(ans)
                    editText.text.length.let { editText.setSelection((it - cursorComplement)) }
                }
            }
        } else {
            editedFlag = false
        }
    }

    companion object {
        private const val INDEX_PHONE_CODE = 0
        private const val INDEX_FIRST_PATH_PHONE_NUMBER = 3
        private const val INDEX_SECOND_PATH_PHONE_NUMBER = 6
        private const val INDEX_THIRD_PATH_PHONE_NUMBER = 8

        private const val COMPLEMENT_INIT_VALUE = 0
    }
}