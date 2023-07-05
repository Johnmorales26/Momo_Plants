package com.johndev.momoplantsparent.loginModule.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.johndev.momoplantsparent.R
import com.johndev.momoplantsparent.databinding.ActivityLoginBinding
import com.johndev.momoplantsparent.mainModule.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: AuthStateListener
    private lateinit var binding: ActivityLoginBinding
    private val authLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val response = IdpResponse.fromResultIntent(it.data)

            if (it.resultCode == RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    Toast.makeText(this,
                        getString(R.string.login_welcome, user.displayName), Toast.LENGTH_SHORT)
                        .show()
                    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
                        param(FirebaseAnalytics.Param.SUCCESS, 100)
                        param(FirebaseAnalytics.Param.METHOD, "login")
                    }
                    startActivity(Intent(this, MainActivity::class.java))
                }
            } else {
                if (response == null) {
                    Toast.makeText(this, getString(R.string.login_see_you_son), Toast.LENGTH_SHORT).show()
                    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
                        param(FirebaseAnalytics.Param.SUCCESS, 200)
                        param(FirebaseAnalytics.Param.METHOD, "login")
                    }
                    finish()
                } else {
                    response.error?.let {
                        if (it.errorCode == ErrorCodes.NO_NETWORK) {
                            Toast.makeText(this,
                                getString(R.string.login_no_net), Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(
                                this, getString(R.string.login_code_error, it.errorCode.toString()),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
                            param(FirebaseAnalytics.Param.SUCCESS, it.errorCode.toLong())
                            param(FirebaseAnalytics.Param.METHOD, "login")
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configAuth()
    }

    private fun configAuth() {
        firebaseAuth = FirebaseAuth.getInstance()
        authStateListener = FirebaseAuth.AuthStateListener { auth ->
            if (auth.currentUser != null) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                val providers = arrayListOf(
                    AuthUI.IdpConfig.EmailBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build(),
                    AuthUI.IdpConfig.PhoneBuilder().build()
                )

                val loginView = AuthMethodPickerLayout.Builder(R.layout.view_login)
                    .setEmailButtonId(R.id.fabEmail)
                    .setGoogleButtonId(R.id.fabGoogle)
                    .setPhoneButtonId(R.id.fabPhone)
                    .setFacebookButtonId(R.id.fabFacebook)
                    .build()

                authLauncher.launch(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .setAuthMethodPickerLayout(loginView)
                        .setTheme(R.style.FirebaseUITheme)
                        .build()
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    override fun onPause() {
        super.onPause()
        firebaseAuth.removeAuthStateListener(authStateListener)
    }

}