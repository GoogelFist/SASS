package com.example.sass.presentation.screens.tabs.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.sass.component
import com.example.sass.databinding.MainFragmentBinding
import com.example.sass.presentation.screens.tabs.main.models.MainState
import javax.inject.Inject

class MainFragment : Fragment() {

    // TODO: item decorator
    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding
        get() = _binding!!

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private val viewModel by activityViewModels<MainViewModel> {
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
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.mainState.observe(viewLifecycleOwner) {state ->
            when(state) {
                MainState.Default -> {}
                MainState.Loading -> {}
                MainState.IncorrectToken -> {}
                MainState.ErrorLoaded -> {}
                MainState.Loaded -> {}
                MainState.RefreshedError -> {}
            }
        }

        viewModel.picturesItemList.observe(viewLifecycleOwner) {list ->
            Log.e(this.toString(), list[0].toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}