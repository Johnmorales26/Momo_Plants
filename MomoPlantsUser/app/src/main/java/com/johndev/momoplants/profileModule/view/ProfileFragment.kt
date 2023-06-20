package com.johndev.momoplants.profileModule.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.johndev.momoplants.R
import com.johndev.momoplants.common.utils.editable
import com.johndev.momoplants.common.utils.printToastMsg
import com.johndev.momoplants.databinding.FragmentProfileBinding
import com.johndev.momoplants.profileModule.viewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val startForActivityGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Uri? = result.data?.data
                if (data != null) {
                    Glide.with(requireContext())
                        .load(data)
                        .centerCrop()
                        .transform(CircleCrop())
                        .placeholder(R.drawable.ic_broken_image)
                        .into(binding.btnSelectImg)
                    profileViewModel.saveUriImage(uri = data)
                }
            }
        }

    private fun pickPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/jpeg"
        }
        startForActivityGallery.launch(intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupToolbar()
        setupObservers()
        setupButtons()
        profileViewModel.onGetUserData()
        profileViewModel.getUriImage()
    }

    private fun setupViewModel() {
        val vm: ProfileViewModel by viewModels()
        profileViewModel = vm
    }

    private fun setupObservers() {
        profileViewModel.userData.observe(viewLifecycleOwner) { firebaseUser ->
            if (firebaseUser != null) {
                with(binding) {
                    etName.text = firebaseUser.displayName?.editable()
                    etEmail.text = firebaseUser.email?.editable()
                    etPhoneNumber.text = firebaseUser.phoneNumber?.editable()
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
        profileViewModel.imageUri.observe(viewLifecycleOwner) {
            it?.let { imgUri ->
                Glide.with(requireContext())
                    .load(imgUri)
                    .centerCrop()
                    .transform(CircleCrop())
                    .placeholder(R.drawable.ic_broken_image)
                    .into(binding.btnSelectImg)
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
            pickPhotoFromGallery()
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
