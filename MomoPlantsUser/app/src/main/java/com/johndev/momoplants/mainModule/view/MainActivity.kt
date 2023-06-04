package com.johndev.momoplants.mainModule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.johndev.momoplants.R
import com.johndev.momoplants.cartModule.view.CartFragment
import com.johndev.momoplants.cartModule.viewModel.CartViewModel
import com.johndev.momoplants.common.utils.openFragment
import com.johndev.momoplants.databinding.ActivityMainBinding
import com.johndev.momoplants.mainModule.viewModel.HomeViewModel
import com.johndev.momoplants.profileModule.view.ProfileFragment
import com.johndev.momoplants.profileModule.viewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        lateinit var profileViewModel: ProfileViewModel
        lateinit var homeViewModel: HomeViewModel
        lateinit var cartViewModel: CartViewModel
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
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
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