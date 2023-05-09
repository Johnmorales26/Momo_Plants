package com.johndev.momoplants.profileModule.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.johndev.momoplants.R
import com.johndev.momoplants.common.utils.ViewModels.getInstanceProfileVM
import com.johndev.momoplants.common.utils.editable

class ProfileFragment : Fragment() {
    private lateinit var etName:TextInputEditText
    private lateinit var etEmail:TextInputEditText
    private lateinit var etPassword:TextInputEditText
    private lateinit var etAddress:TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents(view)
        setupViewModel()
        setupObservers()
    }

    private fun initComponents(view: View) {
       view.apply {
           etName = findViewById(R.id.etName)
           etEmail = findViewById(R.id.etEmail)
           etPassword = findViewById(R.id.etPassword)
           etAddress = findViewById(R.id.etAddress)
       }

    }

    private fun setupViewModel() {
        getInstanceProfileVM().getUserById(requireContext())
    }

    private fun setupObservers() {
        getInstanceProfileVM().user.observe(viewLifecycleOwner){
            it?.let { userEntity ->
                userEntity.apply {
                    etName.text = name.trim().editable()
                    etEmail.text = email.trim().editable()
                    etPassword.text = password.trim().editable()
                    etAddress.text  =direction.trim().editable()
                }
            }
        }
    }

}
