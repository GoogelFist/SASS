package com.example.sass.presentation.screens.tabs.favorite

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sass.component
import com.example.sass.databinding.FavoriteTabFragmentBinding
import com.example.sass.presentation.screens.tabs.main.MainViewModel
import com.example.sass.presentation.screens.tabs.main.MainViewModelFactory
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

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private val mainViewModel by activityViewModels<MainViewModel> {
        mainViewModelFactory
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
        _binding = FavoriteTabFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.favoritePicturesItems.observe(viewLifecycleOwner) {
            Log.e(this.toString(), it.size.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}