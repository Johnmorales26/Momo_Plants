package com.johndev.momoplants.mainModule.view

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.johndev.momoplants.R
import com.johndev.momoplants.cartModule.view.CartFragment
import com.johndev.momoplants.common.utils.Constants
import com.johndev.momoplants.common.utils.openFragment
import com.johndev.momoplants.profileModule.view.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        setupBottomNavigation()
        initFragment()
        getIdUser()
    }

    private fun setupBottomNavigation() {
        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
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

    private fun getIdUser() {
        val idUser = sharedPreferences.getLong(Constants.USER_ACTIVE, 0)
        //Toast.makeText(this, idUser.toString(), Toast.LENGTH_SHORT).show()
    }

    companion object {
        lateinit var sharedPreferences: SharedPreferences
    }

}