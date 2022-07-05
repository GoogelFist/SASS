package com.example.sass.presentation.screens.tabs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.sass.R
import com.example.sass.databinding.TabsFragmentBinding

class TabsFragment : Fragment(R.layout.tabs_fragment) {

    private lateinit var binding: TabsFragmentBinding

    // TODO: will need think about icons color
    // TODO: will need think about hardcoded text, text size and font on menu
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = TabsFragmentBinding.bind(view)

        val navHost = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
        val navController = navHost.navController

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }
}