package com.example.sass.presentation.screens.tabs.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sass.databinding.PictureDetailFragmentBinding

class PictureDetailFragment : Fragment() {

    private var _binding: PictureDetailFragmentBinding? = null
    private val binding: PictureDetailFragmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PictureDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pictureId = PictureDetailFragmentArgs.fromBundle(requireArguments()).pictureId
        binding.textView.text = "Picture detail fragment, picture id: $pictureId"

        setupPopupButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupPopupButton() {
        binding.ibDetailPicturePopup.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}