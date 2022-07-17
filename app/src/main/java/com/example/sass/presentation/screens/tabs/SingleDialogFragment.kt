package com.example.sass.presentation.screens.tabs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.example.sass.databinding.SingleDialogFragmentBinding


class SingleDialogFragment : DialogFragment() {

    private var _binding: SingleDialogFragmentBinding? = null
    private val binding: SingleDialogFragmentBinding
        get() = _binding!!


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val message = requireArguments().getString(KEY_MESSAGE_TEXT).toString()

        _binding = SingleDialogFragmentBinding.inflate(layoutInflater)

        binding.tvTitleSignOutDialog.text = message

        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()

        dialog.setOnShowListener {
            binding.buttonPositiveSignOutDialog.setOnClickListener {
                parentFragmentManager.setFragmentResult(
                    DEFAULT_REQUEST_KEY,
                    bundleOf(KEY_ANSWER_RESPONSE to true)
                )
                dismiss()
            }
            binding.buttonNegativeSignOutDialog.setOnClickListener {
                parentFragmentManager.setFragmentResult(
                    DEFAULT_REQUEST_KEY,
                    bundleOf(KEY_ANSWER_RESPONSE to false)
                )
                dismiss()
            }
        }

        return dialog
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private val TAG = SingleDialogFragment::class.java.simpleName

        private const val KEY_ANSWER_RESPONSE = "KEY_ANSWER_RESPONSE"
        private const val KEY_MESSAGE_TEXT = "KEY_MESSAGE_TEXT"

        private val DEFAULT_REQUEST_KEY = "$TAG:defaultRequestKey"

        fun show(manager: FragmentManager, message: String) {
            val dialogFragment = SingleDialogFragment()
            dialogFragment.arguments = bundleOf(KEY_MESSAGE_TEXT to message)
            dialogFragment.show(manager, TAG)
        }

        fun setupListener(
            manager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            listener: (answer: Boolean) -> Unit
        ) {
            manager.setFragmentResultListener(DEFAULT_REQUEST_KEY, lifecycleOwner) { _, result ->
                listener.invoke(result.getBoolean(KEY_ANSWER_RESPONSE))
            }
        }
    }
}