package com.example.sass.presentation.screens

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.sass.R
import com.example.sass.databinding.LoadingButtonBinding

class LoadingButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.loadingButtonStyle,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    var isLoading: Boolean = false
        set(value) {
            if (value) {
                binding.progressBarLoadingButton.visibility = View.VISIBLE
                binding.progressBarLoadingButton.isEnabled = false
                binding.tvLoadingButton.text = ""
            } else {
                binding.progressBarLoadingButton.visibility = View.GONE
                binding.progressBarLoadingButton.isEnabled = true
                binding.tvLoadingButton.text = loadingButtonText
            }
            field = value
        }

    var loadingButtonText: String = ""
        set(value) {
            field = value
            binding.tvLoadingButton.text = value
        }

    private val binding: LoadingButtonBinding

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.loading_button, this, true)
        binding = LoadingButtonBinding.bind(this)
        initAttributes(attrs, defStyleAttr, defStyleRes)
    }

    private fun initAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return

        val typeArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.LoadingButtonView,
            defStyleAttr,
            defStyleRes
        )

        loadingButtonText = typeArray.getString(R.styleable.LoadingButtonView_buttonText) ?: ""
        binding.tvLoadingButton.text = loadingButtonText

        isLoading = typeArray.getBoolean(R.styleable.LoadingButtonView_isLoading, false)

        typeArray.recycle()
    }
}