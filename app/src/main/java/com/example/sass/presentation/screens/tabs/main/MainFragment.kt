package com.example.sass.presentation.screens.tabs.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.sass.R
import com.example.sass.component
import com.example.sass.databinding.MainFragmentBinding
import com.example.sass.presentation.screens.tabs.main.models.MainEvent
import com.example.sass.presentation.screens.tabs.main.models.MainState
import com.example.sass.presentation.screens.tabs.main.recycler.PicturesMainAdapter
import javax.inject.Inject

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    private val viewModel by activityViewModels<MainViewModel> {
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
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.obtainEvent(MainEvent.OnUpdateUi)

        observeViewModel()
        setupRecycler()
        configButtons()
        setupSwipeRefreshLayout()
        setPicturesItemClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.mainState.observe(viewLifecycleOwner) { state ->
            when (state) {
                MainState.Loading -> configLoadingState()
                MainState.ErrorLoaded -> configErrorLoadedState()
                MainState.Loaded -> configLoadedState()

                MainState.Refreshing -> configRefreshingState()
                MainState.RefreshedError -> configErrorRefreshState()

                MainState.EmptyList -> configEmptyListState()

                MainState.IncorrectToken -> navigateToSignInFragment()
            }
        }

        viewModel.picturesItems.observe(viewLifecycleOwner) { picturesList ->
            picturesMainAdapter.submitList(picturesList)
        }
    }

    private fun configLoadingState() {
        with(binding) {
            ibSearchMain.visibility = View.GONE
            swipeRefreshLayout.visibility = View.GONE
            progressBarMainScreen.visibility = View.VISIBLE
            llMainErrorMessage.visibility = View.GONE
            buttonTryAgain.visibility = View.GONE
            tvErrorSnack.visibility = View.GONE
        }
    }

    private fun configErrorLoadedState() {
        with(binding) {
            ibSearchMain.visibility = View.GONE
            swipeRefreshLayout.visibility = View.GONE
            progressBarMainScreen.visibility = View.GONE
            llMainErrorMessage.visibility = View.VISIBLE
            buttonTryAgain.visibility = View.VISIBLE
            tvErrorSnack.visibility = View.GONE
        }
    }

    private fun configLoadedState() {
        with(binding) {
            ibSearchMain.visibility = View.VISIBLE

            swipeRefreshLayout.visibility = View.VISIBLE
            swipeRefreshLayout.isEnabled = true
            swipeRefreshLayout.isRefreshing = false

            progressBarMainScreen.visibility = View.GONE
            llMainErrorMessage.visibility = View.GONE
            buttonTryAgain.visibility = View.GONE
            tvErrorSnack.visibility = View.GONE
        }
    }

    private fun configErrorRefreshState() {
        with(binding) {
            ibSearchMain.visibility = View.VISIBLE

            swipeRefreshLayout.visibility = View.VISIBLE
            swipeRefreshLayout.isEnabled = true
            swipeRefreshLayout.isRefreshing = false

            progressBarMainScreen.visibility = View.GONE
            llMainErrorMessage.visibility = View.GONE
            buttonTryAgain.visibility = View.GONE
            tvErrorSnack.visibility = View.VISIBLE
        }
    }

    private fun configEmptyListState() {
        with(binding) {
            ibSearchMain.visibility = View.GONE

            swipeRefreshLayout.visibility = View.VISIBLE
            swipeRefreshLayout.isEnabled = false
            swipeRefreshLayout.isRefreshing = false

            progressBarMainScreen.visibility = View.GONE
            llMainErrorMessage.visibility = View.GONE
            buttonTryAgain.visibility = View.GONE
            tvErrorSnack.visibility = View.GONE
        }
    }

    private fun configRefreshingState() {
        with(binding) {
            ibSearchMain.visibility = View.VISIBLE

            swipeRefreshLayout.visibility = View.VISIBLE
            swipeRefreshLayout.isEnabled = true
            swipeRefreshLayout.isRefreshing = true

            progressBarMainScreen.visibility = View.GONE
            llMainErrorMessage.visibility = View.GONE
            buttonTryAgain.visibility = View.GONE
            tvErrorSnack.visibility = View.GONE
        }
    }

    private fun navigateToSignInFragment() {
        getRootNavController().navigate(R.id.action_tabsFragment_to_signInFragment)
    }

    private fun getRootNavController(): NavController {
        val navHost = requireActivity()
            .supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        return navHost.navController
    }

    private fun configButtons() {
        binding.buttonTryAgain.setOnClickListener {
            viewModel.obtainEvent(MainEvent.OnLoadPictures)
        }

        binding.ibSearchMain.setOnClickListener {
            val direction = MainFragmentDirections.actionMainTabFragmentToSearchFragment()
            findNavController().navigate(direction)
        }

        picturesMainAdapter.onFavoriteButtonClickListener = { pictureId, isFavorite ->
            if (isFavorite) {
                viewModel.obtainEvent(MainEvent.OnRemoveFromFavorite(pictureId))

            } else {
                viewModel.obtainEvent(MainEvent.OnAddToFavorite(pictureId))
            }
        }
    }

    private fun setupRecycler() {
        val recycler = binding.recyclerMain

        picturesMainAdapter = PicturesMainAdapter()

        recycler.adapter = picturesMainAdapter
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.obtainEvent(MainEvent.OnRefresh)
        }
    }

    private fun setPicturesItemClickListener() {
        picturesMainAdapter.onPictureClickListener = { pictureId ->
            val direction =
                MainFragmentDirections.actionMainTabFragmentToPictureDetailFragment(pictureId)
            findNavController().navigate(direction)
        }
    }
}