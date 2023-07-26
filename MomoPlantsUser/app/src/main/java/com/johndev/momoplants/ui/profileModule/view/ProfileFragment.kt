package com.johndev.momoplants.ui.profileModule.view

import android.Manifest
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
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
import com.johndev.momoplants.common.utils.Constants.PERMISSION_REQUEST_STORAGE
import com.johndev.momoplants.common.utils.DownloadController
import com.johndev.momoplants.common.utils.checkSelfPermissionCompat
import com.johndev.momoplants.common.utils.editable
import com.johndev.momoplants.common.utils.printInfoToast
import com.johndev.momoplants.databinding.FragmentProfileBinding
import com.johndev.momoplants.ui.mainModule.MainActivity
import com.johndev.momoplants.ui.profileModule.viewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
                    printInfoToast(R.string.soon_available_option, context = requireContext())
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
            printInfoToast(
                R.string.soon_available_option,
                context = requireContext()
            )
        }
        binding.btnChangePassword.setOnClickListener {
            printInfoToast(
                R.string.soon_available_option,
                context = requireContext()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
