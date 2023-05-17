package com.johndev.momoplants.loginModule.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.johndev.momoplants.R
import com.johndev.momoplants.common.utils.Constants.IS_SESSION_ACTIVE
import com.johndev.momoplants.common.utils.Constants.USER_ACTIVE
import com.johndev.momoplants.common.utils.printToastMsg
import com.johndev.momoplants.common.utils.printToastWithStringMsg
import com.johndev.momoplants.common.utils.setupNavigationTo
import com.johndev.momoplants.common.utils.validFields
import com.johndev.momoplants.databinding.FragmentSignInBinding
import com.johndev.momoplants.loginModule.view.LoginActivity.Companion.sharedPreferences
import com.johndev.momoplants.loginModule.view.LoginActivity.Companion.userViewModel
import com.johndev.momoplants.mainModule.view.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupButtons()
        recoveryData()
        setupObservers()
    }

    private fun setupButtons() {
        with(binding) {
            viewButtons.apply {
                btnFacebook.imgButton.load(R.drawable.ic_facebook)
                btnGoogle.imgButton.load(R.drawable.ic_google)
                btnFacebook.imgButton.setOnClickListener {
                    printToastMsg(R.string.soon_available_option, requireContext())
                }
                btnGoogle.imgButton.setOnClickListener {
                    printToastMsg(R.string.soon_available_option, requireContext())
                }
                btnForgotPassword.setOnClickListener {
                    printToastMsg(R.string.soon_available_option, requireContext())
                }
            }
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setupNavigationTo(
            fragment = SignMainFragment(),
            fragmentManager = parentFragmentManager
        )
    }

    private fun setupObservers() {
        userViewModel.getRegisteredUser().observe(viewLifecycleOwner) { user ->
            user?.let {
                printToastWithStringMsg(R.string.msg_welcome_user, user.name, requireContext())
                userViewModel.writeToSharedPrefs(requireContext(), user.user_id)
                sharedPreferences.edit {
                    putBoolean(IS_SESSION_ACTIVE, true)
                    apply()
                }
                startActivity(Intent(context, MainActivity::class.java))
            } ?: printToastMsg(R.string.msg_user_not_found, requireContext())
        }
    }


    private fun recoveryData() {
        val fields = listOf(
            binding.etEmail to binding.tilEmail,
            binding.etPassword to binding.tilPassword
        )
        binding.btnSignIn.setOnClickListener {
            if (validFields(fields, requireContext())) {
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()
                lifecycleScope.launch(Dispatchers.IO) {
                    userViewModel.getUserByEmailAndPassword(email, password)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}