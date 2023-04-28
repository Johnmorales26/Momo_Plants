package com.johndev.momoplants.loginModule.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.johndev.momoplants.R
import com.johndev.momoplants.common.utils.Constants.IS_SESSION_ACTIVE
import com.johndev.momoplants.common.utils.Constants.USER_ACTIVE
import com.johndev.momoplants.common.utils.printToastMsg
import com.johndev.momoplants.common.utils.printToastWithStringMsg
import com.johndev.momoplants.common.utils.setupNavigationTo
import com.johndev.momoplants.common.utils.validFields
import com.johndev.momoplants.loginModule.view.LoginActivity.Companion.sharedPreferences
import com.johndev.momoplants.loginModule.view.LoginActivity.Companion.userViewModel
import com.johndev.momoplants.mainModule.view.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {

    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnSignIn: MaterialButton
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents(view)
        setupToolbar()
        recoveryData()
        setupObservers()
    }

    private fun setupToolbar() {
        toolbar.setupNavigationTo(fragment = SignMainFragment(), fragmentManager = parentFragmentManager)
    }

    private fun setupObservers() {
        userViewModel.getRegisteredUser().observe(viewLifecycleOwner) { user ->
            user?.let {
                printToastWithStringMsg(R.string.msg_welcome_user, user.name, requireContext())
                sharedPreferences.edit {
                    putBoolean(IS_SESSION_ACTIVE, true)
                    putLong(USER_ACTIVE, user.user_id)
                    apply()
                }
                startActivity(Intent(context, MainActivity::class.java))
            } ?: printToastMsg(R.string.msg_user_not_found, requireContext())
        }
    }


    private fun recoveryData() {
        val fields = listOf(
            etEmail to tilEmail,
            etPassword to tilPassword
        )
        btnSignIn.setOnClickListener {
            if (validFields(fields, requireContext())) {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                lifecycleScope.launch(Dispatchers.IO) {
                    userViewModel.getUserByEmailAndPassword(email, password)
                }
            }
        }
    }

    private fun initComponents(view: View) {
        view.apply {
            tilEmail = findViewById(R.id.tilEmail)
            tilPassword = findViewById(R.id.tilPassword)
            etEmail = findViewById(R.id.etEmail)
            etPassword = findViewById(R.id.etPassword)
            btnSignIn = findViewById(R.id.btnSignIn)
            toolbar = findViewById(R.id.toolbar)
        }
    }

}