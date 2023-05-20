package com.johndev.momoplants.profileModule.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.johndev.momoplants.R
import com.johndev.momoplants.common.utils.Constants
import com.johndev.momoplants.common.utils.ViewModels.getInstanceProfileVM
import com.johndev.momoplants.common.utils.editable
import com.johndev.momoplants.common.utils.printToastMsg
import com.johndev.momoplants.databinding.FragmentProfileBinding
import com.johndev.momoplants.loginModule.view.LoginActivity
import com.johndev.momoplants.loginModule.view.LoginActivity.Companion.sharedPreferences
import com.johndev.momoplants.settingsModule.view.SettingsActivity

class ProfileFragment : Fragment() {



    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupViewModel()
        setupObservers()
        setupButtons()
    }

    private fun setupToolbar() {
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_about -> {
                    printToastMsg(R.string.soon_available_option, requireContext())
                    true
                }

                R.id.action_settings -> {
                    startActivity(Intent(requireContext(), SettingsActivity::class.java))
                    true
                }
                R.id.action_signout -> {
                    getInstanceProfileVM().onLogOut()
                    sharedPreferences.edit {
                        putBoolean(Constants.IS_SESSION_ACTIVE, false)
                        apply()
                    }
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun setupButtons() {
        binding.btnSelectImg.setOnClickListener {
            printToastMsg(R.string.soon_available_option, requireContext())
        }
        binding.btnEditProfile.setOnClickListener {
            printToastMsg(
                R.string.soon_available_option,
                requireContext()
            )
        }
        binding.btnChangePassword.setOnClickListener {
            printToastMsg(
                R.string.soon_available_option,
                requireContext()
            )
            Toast.makeText(requireContext(), R.string.soon_available_option, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupViewModel() {
        getInstanceProfileVM().getUserById(requireContext())
    }

    private fun setupObservers() {
        getInstanceProfileVM().user.observe(viewLifecycleOwner) {
            it?.let { userEntity ->
                userEntity.apply {
                    with(binding) {
                        etName.text = name.trim().editable()
                        etEmail.text = email.trim().editable()
                        etPassword.text = password.trim().editable()
                        etAddress.text = direction.trim().editable()
                    }
                }
            } ?: run {
                with(binding) {
                    etName.text = getString(R.string.msg_user_not_found).editable()
                    etEmail.text = getString(R.string.msg_user_not_found).editable()
                    etPassword.text = getString(R.string.msg_user_not_found).editable()
                    etAddress.text = getString(R.string.msg_user_not_found).editable()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
