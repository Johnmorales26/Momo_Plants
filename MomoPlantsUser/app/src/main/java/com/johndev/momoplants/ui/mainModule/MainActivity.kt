package com.johndev.momoplants.ui.mainModule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.johndev.momoplants.R
import com.johndev.momoplants.common.utils.openFragment
import com.johndev.momoplants.databinding.ActivityMainBinding
import com.johndev.momoplants.ui.cartModule.view.CartFragment
import com.johndev.momoplants.ui.homeModule.view.HomeFragment
import com.johndev.momoplants.ui.ordersModule.view.OrdersFragment
import com.johndev.momoplants.ui.profileModule.view.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
        initFragment()
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