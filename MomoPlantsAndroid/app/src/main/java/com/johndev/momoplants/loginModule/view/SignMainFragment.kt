package com.johndev.momoplants.loginModule.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import com.johndev.momoplants.R
import com.johndev.momoplants.common.utils.openFragment

class SignMainFragment : Fragment() {

    private lateinit var btnSignIn: MaterialButton
    private lateinit var btnSignUp: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUIComponets(view)
        setupButtons()
    }

    private fun setupButtons() {
        btnSignIn.setOnClickListener {
            val signInFragment = SignInFragment()
            openFragment(fragment = signInFragment, fragmentManager = parentFragmentManager, containerId = R.id.container)
        }
        btnSignUp.setOnClickListener {
            val signUpFragment = SignUpFragment()
            openFragment(fragment = signUpFragment, fragmentManager = parentFragmentManager, containerId = R.id.container)
        }
    }

    private fun initUIComponets(view: View) {
        with(view) {
            btnSignIn = findViewById(R.id.btnSignIn)
            btnSignUp = findViewById(R.id.btnSignUp)
        }
    }

}