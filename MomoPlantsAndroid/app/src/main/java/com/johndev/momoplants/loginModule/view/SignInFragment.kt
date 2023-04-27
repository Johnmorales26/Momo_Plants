package com.johndev.momoplants.loginModule.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.johndev.momoplants.R
import com.johndev.momoplants.loginModule.view.LoginActivity.Companion.userViewModel
import com.johndev.momoplants.mainModule.view.MainActivity

class SignInFragment : Fragment() {

    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnSignIn: MaterialButton

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
        recoveryData()
        setupObservers()
    }

    private fun setupObservers() {
        userViewModel.getRegisteredUser().observe(viewLifecycleOwner) { user ->
            if (user != null) {
                Toast.makeText(requireContext(), "Bienvenido ${user.name}", Toast.LENGTH_SHORT).show()
                startActivity(Intent(context, MainActivity::class.java))
            }
        }
    }

    private fun recoveryData() {
        btnSignIn.setOnClickListener {
            if (validFields()) {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                userViewModel.getUserByEmailAndPassword(email, password)
            }
        }
    }

    private fun initComponents(view: View) {
        with(view){
            tilEmail = findViewById(R.id.tilEmail)
            tilPassword = findViewById(R.id.tilPassword)
            etEmail = findViewById(R.id.etEmail)
            etPassword = findViewById(R.id.etPassword)
            btnSignIn = findViewById(R.id.btnSignIn)
        }
    }

    private fun validFields(): Boolean {
        var isValid = true
        //  Evaluando Edit Text Email
        if (etEmail.text.isNullOrEmpty()) {
            tilEmail.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            tilEmail.error = null
        }
        //  Evaluando Edit Text Password
        if (etPassword.text.isNullOrEmpty()) {
            tilPassword.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            tilPassword.error = null
        }
        return isValid
    }

}