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
import coil.load
import com.johndev.momoplants.R
import com.johndev.momoplants.common.entities.UserEntity
import com.johndev.momoplants.common.utils.Constants
import com.johndev.momoplants.common.utils.printSnackbarMsg
import com.johndev.momoplants.common.utils.printToastMsg
import com.johndev.momoplants.common.utils.setupNavigationTo
import com.johndev.momoplants.common.utils.validFields
import com.johndev.momoplants.databinding.FragmentSignUpBinding
import com.johndev.momoplants.loginModule.view.LoginActivity.Companion.sharedPreferences
import com.johndev.momoplants.loginModule.view.LoginActivity.Companion.userViewModel
import com.johndev.momoplants.mainModule.view.MainActivity

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupToolbar()
        watchPassword()
        recoverData()
        setupObservers(view)
    }

    private fun setupToolbar() {
        binding.toolbar.setupNavigationTo(
            fragment = SignMainFragment(),
            fragmentManager = parentFragmentManager
        )
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
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                text: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {
                updateSignUpButtonEnabled()
            }

            override fun afterTextChanged(text: Editable?) {}
        })

        binding.etPasswordVerify.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                text: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {
                updateSignUpButtonEnabled()
            }

            override fun afterTextChanged(text: Editable?) {}
        })
    }

    private fun recoverData() {
        val fields = listOf(
            binding.etName to binding.tilName,
            binding.etSurname to binding.tilSurname,
            binding.etAddress to binding.tilAddress,
            binding.etEmail to binding.tilEmail,
            binding.etPassword to binding.tilPassword,
            binding.etPasswordVerify to binding.tilPasswordVerify
        )
        binding.btnSignUp.setOnClickListener {
            if (validFields(fields, requireContext())) {
                val userEntity = UserEntity(
                    user_id = System.currentTimeMillis(),
                    name = "${binding.etName.text} ${binding.etSurname.text}".trim(),
                    email = binding.etEmail.text.toString().trim(),
                    password = binding.etPassword.text.toString().trim(),
                    direction = binding.etAddress.text.toString().trim()
                )
                userViewModel.insert(userEntity, requireContext())
            }
        }
    }

    private fun updateSignUpButtonEnabled() {
        binding.btnSignUp.isEnabled =
            binding.etPassword.text.toString().trim() == binding.etPasswordVerify.text.toString()
                .trim()
    }

    private fun setupButtons() {
        with(binding) {
            btnsSocialMedia.apply {
                btnFacebook.imgButton.load(R.drawable.ic_facebook)
                btnGoogle.imgButton.load(R.drawable.ic_google)
                btnFacebook.imgButton.setOnClickListener {
                    printToastMsg(R.string.soon_available_option, requireContext())
                }
                btnGoogle.imgButton.setOnClickListener {
                    printToastMsg(R.string.soon_available_option, requireContext())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}