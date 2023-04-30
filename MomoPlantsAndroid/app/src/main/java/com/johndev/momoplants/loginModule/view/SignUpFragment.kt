package com.johndev.momoplants.loginModule.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.johndev.momoplants.R
import com.johndev.momoplants.common.entities.UserEntity
import com.johndev.momoplants.common.utils.Constants
import com.johndev.momoplants.common.utils.printSnackbarMsg
import com.johndev.momoplants.common.utils.printToastMsg
import com.johndev.momoplants.common.utils.setupNavigationTo
import com.johndev.momoplants.common.utils.validFields
import com.johndev.momoplants.loginModule.view.LoginActivity.Companion.sharedPreferences
import com.johndev.momoplants.loginModule.view.LoginActivity.Companion.userViewModel
import com.johndev.momoplants.mainModule.view.MainActivity

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
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

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
        setupToolbar()
        watchPassword()
        recoverData()
        setupObservers(view)
    }

    private fun setupToolbar() {
        toolbar.setupNavigationTo(fragment = SignMainFragment(), fragmentManager = parentFragmentManager)
    }

    private fun setupObservers(view: View) {
        userViewModel.getSnackbarMsg().observe(viewLifecycleOwner) { msg ->
            when (msg) {
                R.string.login_insert_success -> {
                    sharedPreferences.edit {
                        putBoolean(Constants.IS_SESSION_ACTIVE, true)
                        apply()
                    }
                    printToastMsg(msg, requireContext())
                    startActivity(Intent(context, MainActivity::class.java))
                }
                else -> printSnackbarMsg(view, msg, requireContext())
            }
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
        val fields = listOf(
            etName to tilName,
            etLastname to tilLastname,
            etAddress to tilAddress,
            etEmail to tilEmail,
            etPassword to tilPassword,
            etPasswordVerify to tilPasswordVerify
        )
        btnSignUp.setOnClickListener {
            if (validFields(fields, requireContext())) {
                val userEntity = UserEntity(
                    user_id = System.currentTimeMillis(),
                    name = "${etName.text} ${etLastname.text}".trim(),
                    email = etEmail.text.toString().trim(),
                    password = etPassword.text.toString().trim(),
                    direction = etAddress.text.toString().trim()
                )
                userViewModel.insert(userEntity)
            }
        }
    }

    private fun updateSignUpButtonEnabled() {
        btnSignUp.isEnabled = etPassword.text.toString().trim() == etPasswordVerify.text.toString().trim()
    }

    private fun initVariables(view: View) {
        view.apply {
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
            toolbar = findViewById(R.id.toolbar)
        }
    }


}