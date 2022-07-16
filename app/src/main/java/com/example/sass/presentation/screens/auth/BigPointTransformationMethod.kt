package com.example.sass.presentation.screens.auth

import android.text.method.PasswordTransformationMethod
import android.view.View

class BigPointTransformationMethod: PasswordTransformationMethod() {

    override fun getTransformation(source: CharSequence, view: View): CharSequence {
        return PasswordCharSequence(source)
    }

    inner class PasswordCharSequence(private val source: CharSequence) : CharSequence {

        override val length: Int
            get() = source.length

        override fun get(index: Int): Char = PASSWORD_MASK

        override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
            return source.subSequence(startIndex, endIndex)
        }
    }

    companion object {
        private const val PASSWORD_MASK = '⬤' //U+2B24
    }
}