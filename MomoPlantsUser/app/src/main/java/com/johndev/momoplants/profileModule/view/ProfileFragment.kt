package com.johndev.momoplants.profileModule.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.johndev.momoplants.R
import com.johndev.momoplants.common.utils.editable
import com.johndev.momoplants.common.utils.printToastMsg
import com.johndev.momoplants.databinding.FragmentProfileBinding
import com.johndev.momoplants.mainModule.view.MainActivity.Companion.profileViewModel
import java.util.UUID


class ProfileFragment : Fragment() {

    private lateinit var bindingg: FragmentProfileBinding

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        bindingg = FragmentProfileBinding.inflate(layoutInflater)

        return binding.root


    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    pickPhotoFromGallery()
                }
                else -> requestPermissionLauncher.launch(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        } else {
            pickPhotoFromGallery()
        }
    }


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                pickPhotoFromGallery()
            } else {
                // Permiso denegado
            }
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

                   val sharedPreferences = requireContext().getSharedPreferences("ProfileFragment", Context.MODE_PRIVATE)
                   val key = "imageUri"

                    if (data != null) {
                        val uriString = data.toString()
                        val editor = sharedPreferences.edit()
                        editor.putString(key, uriString)
                        editor.apply()
                    }
                } else {
                    // Manejar el caso cuando la URI de la imagen es nula
                }

            }
        }

    private fun pickPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startForActivityGallery.launch(intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Leer el valor guardado en SharedPreferences para la clave "imageUri"
        val sharedPreferences = requireContext().getSharedPreferences("ProfileFragment", Context.MODE_PRIVATE)
        val key = "imageUri"
        val savedUriString = sharedPreferences.getString(key, null)
        val savedUri = savedUriString?.let { Uri.parse(it) }

        // Mostrar la imagen si existe una URI guardada
        savedUri?.let { uri ->
            Glide.with(requireContext())
                .load(uri)
                .centerCrop()
                .transform(CircleCrop())
                .placeholder(R.drawable.ic_broken_image)
                .into(binding.btnSelectImg)
        }

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
           requestPermissions()
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
