package com.johndev.momoplantsparent.mainModule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.johndev.momoplantsparent.R
import com.johndev.momoplantsparent.addModule.view.AddDialogFragment
import com.johndev.momoplantsparent.common.utils.openFragment
import com.johndev.momoplantsparent.databinding.ActivityMainBinding
import com.johndev.momoplantsparent.mainModule.viewModel.HomeViewModel
import com.johndev.momoplantsparent.ordersModule.view.OrdersFragment
import com.johndev.momoplantsparent.profileModule.view.ProfileFragment
import com.johndev.momoplantsparent.profileModule.viewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        lateinit var profileViewModel: ProfileViewModel
        lateinit var homeViewModel: HomeViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
        initFragment()
        setupViewModels()
        setupFab()
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            homeViewModel.onPlantSelected(null)
            AddDialogFragment().show(supportFragmentManager, AddDialogFragment::class.java.simpleName)
        }
    }

    private fun setupViewModels() {
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val homeFragment = HomeFragment()
                    openFragment(fragment = homeFragment, fragmentManager = supportFragmentManager, containerId = R.id.container)
                    true
                }
                R.id.navigation_orders -> {
                    val ordersFragment = OrdersFragment()
                    openFragment(fragment = ordersFragment, fragmentManager = supportFragmentManager, containerId = R.id.container)
                    true
                }
                R.id.navigation_profile -> {
                    val profileFragment = ProfileFragment()
                    openFragment(fragment = profileFragment, fragmentManager = supportFragmentManager, containerId = R.id.container)
                    true
                }
                else -> false
            }
        }
    }

    private fun initFragment() {
        val homeFragment = HomeFragment()
        openFragment(fragment = homeFragment, fragmentManager = supportFragmentManager, containerId = R.id.container)
    }

}