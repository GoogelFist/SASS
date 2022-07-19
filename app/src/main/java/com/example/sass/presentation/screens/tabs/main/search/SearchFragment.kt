package com.example.sass.presentation.screens.tabs.main.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.sass.R
import com.example.sass.component
import com.example.sass.databinding.SearchFragmentBinding
import com.example.sass.presentation.screens.tabs.main.recycler.PicturesMainAdapter
import com.example.sass.presentation.screens.tabs.main.search.SearchHelper.hideKeyboard
import com.example.sass.presentation.screens.tabs.main.search.SearchHelper.showKeyboard
import com.example.sass.presentation.screens.tabs.main.search.models.SearchEvent
import com.example.sass.presentation.screens.tabs.main.search.models.SearchState
import javax.inject.Inject

class SearchFragment : Fragment() {

    private var _binding: SearchFragmentBinding? = null
    private val binding: SearchFragmentBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: SearchViewModelFactory

    private val viewModel by activityViewModels<SearchViewModel> {
        viewModelFactory
    }

    private lateinit var picturesMainAdapter: PicturesMainAdapter

    override fun onAttach(context: Context) {
        context.component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.obtainEvent(SearchEvent.OnSetDefaultState)

        setupRecycler()
        observeViewModel()
        setupButtons()
        setFavoriteButtonClickListener()
        setPicturesItemClickListener()
        configSearchField()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerSearch.adapter = null
        _binding = null
    }

    private fun setupRecycler() {
        val recycler = binding.recyclerSearch

        picturesMainAdapter = PicturesMainAdapter()

        recycler.adapter = picturesMainAdapter

        recycler.setOnTouchListener { v, event ->
            binding.textInputEditTextSearch.clearFocus()
            v.performClick()
            v.hideKeyboard()
            return@setOnTouchListener false
        }
    }

    private fun observeViewModel() {
        viewModel.picturesItems.observe(viewLifecycleOwner) {
            picturesMainAdapter.submitList(it)
        }

        viewModel.searchState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SearchState.Default -> configDefaultState()
                SearchState.Found -> configFoundState()
                SearchState.NotFound -> configNotFoundState()
            }
        }
    }

    private fun configDefaultState() {
        binding.recyclerSearch.visibility = View.GONE
        binding.llInitMessage.visibility = View.VISIBLE
        binding.llEmptyResultMessage.visibility = View.GONE
    }

    private fun configFoundState() {
        binding.recyclerSearch.visibility = View.VISIBLE
        binding.llInitMessage.visibility = View.GONE
        binding.llEmptyResultMessage.visibility = View.GONE
    }

    private fun configNotFoundState() {
        binding.recyclerSearch.visibility = View.GONE
        binding.llInitMessage.visibility = View.GONE
        binding.llEmptyResultMessage.visibility = View.VISIBLE
    }

    private fun setupButtons() {
        binding.ibSearchPopup.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setFavoriteButtonClickListener() {
        picturesMainAdapter.onFavoriteButtonClickListener = { pictureId, isFavorite ->
            if (isFavorite) {
                viewModel.obtainEvent(SearchEvent.OnRemoveFromFavorite(pictureId))

            } else {
                viewModel.obtainEvent(SearchEvent.OnAddToFavorite(pictureId))
            }
        }
    }

    private fun getRootNavController(): NavController {
        val navHost = requireActivity()
            .supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        return navHost.navController
    }

    private fun setPicturesItemClickListener() {
        picturesMainAdapter.onPictureClickListener = { pictureId ->
            val direction =
                SearchFragmentDirections.actionSearchFragmentToPictureDetailFragment(pictureId)
            getRootNavController().navigate(direction)
        }
    }

    private fun configSearchField() {
        binding.textInputEditTextSearch.showKeyboard()
        binding.textInputEditTextSearch.doOnTextChanged { text, start, before, count ->
            viewModel.obtainEvent(SearchEvent.OnSearchPictures(text.toString()))
        }
    }
}
