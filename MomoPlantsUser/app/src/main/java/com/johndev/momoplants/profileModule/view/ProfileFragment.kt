package com.johndev.momoplants.profileModule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.johndev.momoplants.R
import com.johndev.momoplants.common.utils.editable
import com.johndev.momoplants.common.utils.printToastMsg
import com.johndev.momoplants.databinding.FragmentProfileBinding
import com.johndev.momoplants.mainModule.view.MainActivity.Companion.profileViewModel

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
        setupObservers()
        setupViewModel()
        setupButtons()
    }

    private fun setupViewModel() {
        profileViewModel.onGetUserData()
    }

    private fun setupObservers() {
        profileViewModel.userData.observe(viewLifecycleOwner) { firebaseUser ->
            if (firebaseUser != null) {
                with(binding) {
                    etName.text = firebaseUser.displayName?.editable()
                    etEmail.text = firebaseUser.email?.editable()
                    Glide
                        .with(requireContext())
                        .load(firebaseUser.photoUrl)
                        .centerCrop()
                        .circleCrop()
                        .placeholder(R.drawable.ic_broken_image)
                        .into(imgProfile)
                }
            }
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_about -> {
                    printToastMsg(R.string.soon_available_option, requireContext())
                    true
                }

                R.id.action_settings -> {
                    //startActivity(Intent(requireContext(), SettingsActivity::class.java))
                    true
                }

                R.id.action_signout -> {
                    profileViewModel.onSignInAccount(requireContext())
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
            Toast.makeText(requireContext(), R.string.soon_available_option, Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
