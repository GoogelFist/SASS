package com.example.sass.presentation.screens.tabs.main

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.sass.R
import com.example.sass.component
import com.example.sass.databinding.MainFragmentBinding
import com.example.sass.presentation.screens.tabs.TabsFragmentDirections
import com.example.sass.presentation.screens.tabs.main.models.MainEvent
import com.example.sass.presentation.screens.tabs.main.models.MainState
import com.example.sass.presentation.screens.tabs.main.recycler.PicturesMainAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
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

        initFragment()

        observeViewModel()
        setupRecycler()
        configButtons()
        setupSwipeRefreshLayout()
        setFavoriteButtonClickListener()
        setPicturesItemClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerMain.adapter = null
        _binding = null
    }

    private fun initFragment() {
        viewModel.obtainEvent(MainEvent.OnUpdateData)
        viewModel.obtainEvent(MainEvent.OnInit)
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

                MainState.IncorrectToken -> navigateToAuthFragment()
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
        }
    }

    private fun configErrorLoadedState() {
        with(binding) {
            ibSearchMain.visibility = View.GONE
            swipeRefreshLayout.visibility = View.GONE
            progressBarMainScreen.visibility = View.GONE
            llMainErrorMessage.visibility = View.VISIBLE
            buttonTryAgain.visibility = View.VISIBLE
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
        }
    }

    private fun configErrorRefreshState() {
        configLoadedState()

        getErrorSnackBar().show()
    }

    private fun configEmptyListState() {
        with(binding) {
            ibSearchMain.visibility = View.GONE

            swipeRefreshLayout.visibility = View.GONE
            swipeRefreshLayout.isEnabled = false
            swipeRefreshLayout.isRefreshing = false

            progressBarMainScreen.visibility = View.GONE
            llMainErrorMessage.visibility = View.GONE
            buttonTryAgain.visibility = View.GONE
        }
    }

    private fun setupRecycler() {
        val recycler = binding.recyclerMain

        picturesMainAdapter = PicturesMainAdapter()

        recycler.adapter = picturesMainAdapter
    }

    private fun navigateToAuthFragment() {
        getRootNavController().navigate(R.id.action_tabsFragment_to_authFragment)
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
    }

    private fun getErrorSnackBar(): Snackbar {
        val bottomNav = requireActivity()
            .findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val snack = Snackbar
            .make(binding.root, R.string.main_refresh_error_text, Snackbar.LENGTH_LONG)
            .setAnchorView(bottomNav)
        val textView = snack.view
            .findViewById<TextView>(com.google.android.material.R.id.snackbar_text)

        textView.gravity = Gravity.START
        textView.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        textView.setTextAppearance(R.style.Text_RobotoRegular_14_White)

        return snack
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.obtainEvent(MainEvent.OnRefresh)
        }
    }

    private fun setFavoriteButtonClickListener() {
        picturesMainAdapter.onFavoriteButtonClickListener = { pictureId, isFavorite ->
            if (isFavorite) {
                viewModel.obtainEvent(MainEvent.OnRemoveFromFavorite(pictureId))

            } else {
                viewModel.obtainEvent(MainEvent.OnAddToFavorite(pictureId))
            }
        }
    }

    private fun setPicturesItemClickListener() {
        picturesMainAdapter.onPictureClickListener = { pictureId ->
            val direction =
                TabsFragmentDirections.actionTabsFragmentToPictureDetailFragment(pictureId)
            getRootNavController().navigate(direction)
        }
    }
}