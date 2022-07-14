package com.example.sass.presentation.screens.tabs.favorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.sass.R
import com.example.sass.component
import com.example.sass.databinding.FavoriteTabFragmentBinding
import com.example.sass.presentation.screens.tabs.SingleDialogFragment
import com.example.sass.presentation.screens.tabs.favorite.models.FavoriteEvent
import com.example.sass.presentation.screens.tabs.favorite.recycler.FavoritePicsAdapter
import javax.inject.Inject

class FavoriteTabFragment : Fragment() {

    private var _binding: FavoriteTabFragmentBinding? = null
    private val binding: FavoriteTabFragmentBinding
        get() = _binding!!

    @Inject
    lateinit var favoriteViewModelFactory: FavoriteViewModelFactory

    private val viewModel by activityViewModels<FavoriteViewModel> {
        favoriteViewModelFactory
    }

    lateinit var favoritePicsAdapter: FavoritePicsAdapter

    override fun onAttach(context: Context) {
        context.component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavoriteTabFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.obtainEvent(FavoriteEvent.OnUpdateData)

        setupRecycler()
        observeViewModel()
        setFavoriteButtonClickListener()
        setPicturesItemClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerFavorite.adapter = null
        _binding = null
    }

    private fun setupRecycler() {
        val recyclerView = binding.recyclerFavorite
        favoritePicsAdapter = FavoritePicsAdapter()

        recyclerView.adapter = favoritePicsAdapter
    }

    private fun observeViewModel() {
        viewModel.favoritePicItems.observe(viewLifecycleOwner) {
            favoritePicsAdapter.submitList(it)
        }
    }

    private fun setFavoriteButtonClickListener() {
        favoritePicsAdapter.onRemoveFavoriteButtonClickListener = { pictureId ->
            setFavoriteButtonDialog {
                viewModel.obtainEvent(FavoriteEvent.OnRemoveFromFavorite(pictureId))
            }
        }
    }

    private fun setFavoriteButtonDialog(event: () -> Unit) {
        val message = requireContext().getText(R.string.dialog_remove_favorite_message_text)

        SingleDialogFragment.show(parentFragmentManager, message.toString())
        SingleDialogFragment.setupListener(parentFragmentManager, this) { answer ->
            if (answer) {
                event.invoke()
            }
        }
    }

    private fun setPicturesItemClickListener() {
        favoritePicsAdapter.onPictureClickListener = { pictureId ->
            val direction =
                FavoriteTabFragmentDirections.actionFavoriteTabFragmentToPictureDetailFragment(
                    pictureId
                )
            findNavController().navigate(direction)
        }
    }

}