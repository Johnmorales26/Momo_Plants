package com.johndev.momoplants.profileModule.viewModel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.johndev.momoplants.loginModule.view.LoginActivity
import com.johndev.momoplants.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(): ViewModel() {

    private var _userData = MutableLiveData<FirebaseUser?>()
    val userData: LiveData<FirebaseUser?> = _userData

    fun onGetUserData() {
        _userData.value = FirebaseAuth.getInstance().currentUser
    }

    fun onSignInAccount(context: Context) {
        AuthUI.getInstance().signOut(context)
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    context.getString(R.string.menu_option_sign_out),
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(context, intent, null)
            }
    }

}