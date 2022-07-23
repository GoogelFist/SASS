package com.example.sass.presentation.screens.tabs.favorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.sass.R
import com.example.sass.component
import com.example.sass.databinding.FavoriteTabFragmentBinding
import com.example.sass.presentation.screens.tabs.SingleDialogFragment
import com.example.sass.presentation.screens.tabs.TabsFragmentDirections
import com.example.sass.presentation.screens.tabs.favorite.models.FavoriteEvent
import com.example.sass.presentation.screens.tabs.favorite.models.FavoriteState
import com.example.sass.presentation.screens.tabs.favorite.recycler.FavoritePicsAdapter
import javax.inject.Inject

class FavoriteTabFragment : Fragment() {

    private var _binding: FavoriteTabFragmentBinding? = null
    private val binding: FavoriteTabFragmentBinding
        get() = _binding!!

    @Inject
    lateinit var favoriteViewModelFactory: FavoriteViewModelFactory

    private val viewModel by activityViewModels<FavoriteViewModel> { favoriteViewModelFactory }

    lateinit var favoritePicsAdapter: FavoritePicsAdapter
    lateinit var dataObserver: RecyclerView.AdapterDataObserver

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
        favoritePicsAdapter.unregisterAdapterDataObserver(dataObserver)
    }

    private fun setupRecycler() {
        val recyclerView = binding.recyclerFavorite
        favoritePicsAdapter = FavoritePicsAdapter()

        recyclerView.adapter = favoritePicsAdapter

        dataObserver = object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                recyclerView.smoothScrollToPosition(positionStart)
            }
        }
        favoritePicsAdapter.registerAdapterDataObserver(dataObserver)
    }

    private fun observeViewModel() {
        viewModel.favoritePictureItems.observe(viewLifecycleOwner) {
            favoritePicsAdapter.submitList(it)

            viewModel.favoriteState.observe(viewLifecycleOwner) { state ->
                when (state) {
                    FavoriteState.Default -> configDefaultState()
                    FavoriteState.Empty -> configEmptyState()
                }
            }
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
        favoritePicsAdapter.onPictureClickListener = { id ->
            val direction = TabsFragmentDirections.actionTabsFragmentToPictureDetailFragment(id)
            getRootNavController().navigate(direction)
        }
    }

    private fun configDefaultState() {
        binding.recyclerFavorite.visibility = View.VISIBLE
        binding.llEmptyFavoritesMessage.visibility = View.GONE
    }

    private fun configEmptyState() {
        binding.recyclerFavorite.visibility = View.GONE
        binding.llEmptyFavoritesMessage.visibility = View.VISIBLE
    }

    private fun getRootNavController(): NavController {
        val navHost = requireActivity()
            .supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        return navHost.navController
    }
}