package com.johndev.momoplants.onboardingModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import coil.load
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView
import com.johndev.momoplants.R
import com.johndev.momoplants.common.utils.openFragment
import com.johndev.momoplants.common.utils.setupImage
import com.johndev.momoplants.loginModule.view.SignMainFragment

class OnboardingEndFragment : Fragment() {

    private lateinit var tvTitle: MaterialTextView
    private lateinit var tvDescription: MaterialTextView
    private lateinit var fabNext: FloatingActionButton
    private lateinit var fabBack: FloatingActionButton
    private lateinit var btnSkip: MaterialButton
    private lateinit var imgCover: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents(view)
        changeTextViews()
        setupButtons()
        setupImage(imgCover, R.drawable.img_indoor_plant)
    }

    private fun changeTextViews() {
        tvTitle.text = getString(R.string.label_onboarging_title_end)
        tvDescription.text = getString(R.string.label_onboarging_description_end)
    }

    private fun setupButtons() {
        fabNext.setOnClickListener {
            openFragment(fragment = SignMainFragment(), fragmentManager = parentFragmentManager, containerId = R.id.container)
        }
        btnSkip.setOnClickListener {
            openFragment(fragment = SignMainFragment(), fragmentManager = parentFragmentManager, containerId = R.id.container)
        }
        fabBack.setOnClickListener {
            openFragment(fragment = OnboardingStartFragment(), fragmentManager = parentFragmentManager, containerId = R.id.container)
        }
    }

    private fun initComponents(view: View) {
        view.apply {
            tvTitle = findViewById(R.id.tvTitle)
            tvDescription = findViewById(R.id.tvDescription)
            fabNext = findViewById(R.id.fabNext)
            fabBack = findViewById(R.id.fabBack)
            btnSkip = view.findViewById(R.id.btnSkip)
            imgCover = view.findViewById(R.id.imgCover)
        }
    }

}