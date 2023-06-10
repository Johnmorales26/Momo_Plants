package com.johndev.momoplants.mainModule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.johndev.momoplants.R
import com.johndev.momoplants.cartModule.view.CartFragment
import com.johndev.momoplants.cartModule.viewModel.CartViewModel
import com.johndev.momoplants.common.utils.openFragment
import com.johndev.momoplants.databinding.ActivityMainBinding
import com.johndev.momoplants.detailModule.viewModel.DetailViewModel
import com.johndev.momoplants.mainModule.viewModel.HomeViewModel
import com.johndev.momoplants.ordersModule.view.OrdersFragment
import com.johndev.momoplants.ordersModule.viewModel.OrdersViewModel
import com.johndev.momoplants.profileModule.view.ProfileFragment
import com.johndev.momoplants.profileModule.viewModel.ProfileViewModel
import com.johndev.momoplants.trackModule.viewModel.TrackViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        lateinit var profileViewModel: ProfileViewModel
        lateinit var homeViewModel: HomeViewModel
        lateinit var cartViewModel: CartViewModel
        lateinit var ordersViewModel: OrdersViewModel
        lateinit var trackViewModel: TrackViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
        initFragment()
        setupViewModels()
    }

    private fun setupViewModels() {
        val vmProfile: ProfileViewModel by viewModels()
        profileViewModel = vmProfile
        val vmHome: HomeViewModel by viewModels()
        homeViewModel = vmHome
        val vmCart: CartViewModel by viewModels()
        cartViewModel = vmCart
        val vmOrders: OrdersViewModel by viewModels()
        ordersViewModel = vmOrders
        val vmTrack: TrackViewModel by viewModels()
        trackViewModel = vmTrack
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val homeFragment = HomeFragment()
                    openFragment(fragment = homeFragment, fragmentManager = supportFragmentManager, containerId = R.id.container)
                    true
                }
                R.id.navigation_cart -> {
                    val cartFragment = CartFragment()
                    openFragment(fragment = cartFragment, fragmentManager = supportFragmentManager, containerId = R.id.container)
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