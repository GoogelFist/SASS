package com.example.sass.presentation.screens.tabs.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.sass.component
import com.example.sass.databinding.PictureDetailFragmentBinding
import com.example.sass.presentation.screens.tabs.detail.models.DetailEvent
import javax.inject.Inject

class PictureDetailFragment : Fragment() {

    private var _binding: PictureDetailFragmentBinding? = null
    private val binding: PictureDetailFragmentBinding
        get() = _binding!!

    @Inject
    lateinit var detailViewModelFactory: DetailViewModelFactory

    private val viewModel by activityViewModels<DetailViewModel> {
        detailViewModelFactory
    }

    override fun onAttach(context: Context) {
        context.component.inject(this)
        super.onAttach(context)
    }

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
        viewModel.obtainEvent(DetailEvent.OnGetDetailPicture(pictureId))

        observeViewModel(view)
        setupPopupButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel(view: View) {
        viewModel.pictureDetail.observe(viewLifecycleOwner) { pictureDetail ->
            Glide.with(view.context)
                .load(pictureDetail.photoUrl)
                .centerCrop()
                .into(binding.ivPhotoDetailPost)
            binding.tvDetailTitlePost.text = pictureDetail.title
            binding.tvDetailContentPost.text = pictureDetail.content
            binding.tvDetailPublicationDatePost.text = pictureDetail.publicationDate
        }
    }

    private fun setupPopupButton() {
        binding.ibDetailPopup.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}