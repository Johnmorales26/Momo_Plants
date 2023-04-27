package com.johndev.momoplants.loginModule.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.johndev.momoplants.R
import com.johndev.momoplants.common.entities.UserEntity
import com.johndev.momoplants.loginModule.view.LoginActivity.Companion.userViewModel
import java.lang.StringBuilder

class SignUpFragment : Fragment() {

    private lateinit var etName: TextInputEditText
    private lateinit var tilName: TextInputLayout
    private lateinit var etLastname: TextInputEditText
    private lateinit var tilLastname: TextInputLayout
    private lateinit var etAddress: TextInputEditText
    private lateinit var tilAddress: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var tilEmail: TextInputLayout
    private lateinit var etPassword: TextInputEditText
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etPasswordVerify: TextInputEditText
    private lateinit var tilPasswordVerify: TextInputLayout
    private lateinit var btnSignUp: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVariables(view)
        watchPassword()
        recoverData()
        setupObservers(view)
    }

    private fun setupObservers(view: View) {
        userViewModel.getSnackbarMsg().observe(viewLifecycleOwner) { msg ->
            Snackbar.make(view, getString(msg), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun watchPassword() {
        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {
                updateSignUpButtonEnabled()
            }

            override fun afterTextChanged(text: Editable?) {}
        })

        etPasswordVerify.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {
                updateSignUpButtonEnabled()
            }

            override fun afterTextChanged(text: Editable?) {}
        })
    }

    private fun recoverData() {
        btnSignUp.setOnClickListener {
            if (validFields()) {
                val name = etName.text.toString().trim()
                val lastname = etLastname.text.toString().trim()
                val address = etAddress.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                val userEntity = UserEntity(
                    user_id = System.currentTimeMillis(),
                    name = StringBuilder().append(name).append(" ").append(lastname).toString()
                        .trim(),
                    email = email,
                    password = password,
                    direction = address
                )
                userViewModel.insert(userEntity)
            }
        }
    }

    private fun updateSignUpButtonEnabled() {
        btnSignUp.isEnabled = etPassword.text.toString().trim() == etPasswordVerify.text.toString().trim()
    }

    private fun initVariables(view: View) {
        with(view) {
            etName = findViewById(R.id.etName)
            tilName= findViewById(R.id.tilName)
            etLastname = findViewById(R.id.etLastname)
            tilLastname = findViewById(R.id.tilSurname)
            etAddress = findViewById(R.id.etAddress)
            tilAddress = findViewById(R.id.tilAddress)
            etEmail = findViewById(R.id.etEmail)
            tilEmail = findViewById(R.id.tilEmail)
            etPassword = findViewById(R.id.etPassword)
            tilPassword = findViewById(R.id.tilPassword)
            etPasswordVerify = findViewById(R.id.etPasswordVerify)
            tilPasswordVerify = findViewById(R.id.tilPasswordVerify)
            btnSignUp = findViewById(R.id.btnSignUp)
        }
    }

    private fun validFields(): Boolean {
        var isValid = true
        //  Evaluando Edit Text Name
        if (etName.text.isNullOrEmpty()) {
            tilName.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            tilName.error = null
        }
        //  Evaluando Edit Text Lastname
        if (etLastname.text.isNullOrEmpty()) {
            tilLastname.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            tilLastname.error = null
        }
        //  Evaluando Edit Text Address
        if (etAddress.text.isNullOrEmpty()) {
            tilAddress.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            tilAddress.error = null
        }
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
        //  Evaluando Edit Text Password Verify
        if (etPasswordVerify.text.isNullOrEmpty()) {
            tilPasswordVerify.run {
                error = getString(R.string.alert_required)
                requestFocus()
            }
            isValid = false
        } else {
            tilPasswordVerify.error = null
        }
        return isValid
    }

    private fun String.editable(): Editable = Editable.Factory.getInstance().newEditable(this)

}