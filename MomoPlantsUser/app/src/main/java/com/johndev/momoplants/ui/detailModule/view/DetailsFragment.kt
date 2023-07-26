package com.johndev.momoplants.ui.detailModule.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.johndev.momoplants.R
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.common.utils.Constants
import com.johndev.momoplants.databinding.FragmentDetailsBinding
import com.johndev.momoplants.ui.detailModule.viewModel.DetailViewModel
import com.johndev.momoplants.ui.trackModule.view.TrackFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailViewModel: DetailViewModel
    private var plantEntity: PlantEntity? = null
    private val args : DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModels()
        setupObservers()
        setupButtons()
        receiveValues()

        var like = false

        //val corazon = findViewById<View>(R.id.likeImageView)
        binding.likeImageView.setOnClickListener {
            like = likeAnimation(binding.likeImageView as LottieAnimationView, R.raw.black_joy, like)
        }
    }

    private fun receiveValues() {
        args.idPlant?.let {
            detailViewModel.onSearchPlantRealtime(it)
        }
    }

    private fun likeAnimation(imageView: LottieAnimationView,
                              animation: Int,
                              like: Boolean) : Boolean {

        if (!like) {
            imageView.setAnimation(animation)
            imageView.playAnimation()
        } else {
            imageView.animate()
                .alpha(0f)
                .setDuration(200)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animator: Animator) {
                        imageView.setImageResource(R.drawable.corazon)
                        imageView.alpha = 1f
                    }
                })

        }

        return !like
    }

    private fun setupViewModels() {
        val vm: DetailViewModel by viewModels()
        detailViewModel = vm
    }

    private fun setupObservers() {
        detailViewModel.plantEntity.observe(viewLifecycleOwner) { plantEntity ->
            plantEntity?.let {
                this.plantEntity = it
                updateUI(this.plantEntity)
            }
        }
    }

    private fun setupButtons() {
        binding.ibReturn.setOnClickListener { findNavController().popBackStack() }
        binding.bottomOptions.fabAddCart.setOnClickListener {
            detailViewModel.onSave(plantEntity!!)
        }
    }

    private fun updateUI(plantEntity: PlantEntity?) {
        plantEntity?.let {
            with(binding) {
                tvPlantName.text = it.name.toString().trim()
                tvDescription.text = it.description.toString().trim()
                bottomOptions.tvPrice.text = it.price.toString().trim()
                if (plantEntity.quantity > 0) {
                    cardStock.containerCard.setBackgroundColor(requireContext().getColor(R.color.md_theme_light_primaryContainer))
                    cardStock.tvStock.text = getString(R.string.msg_stock_with_stock)
                    bottomOptions.fabAddCart.isEnabled = true
                } else {
                    cardStock.containerCard.setBackgroundColor(requireContext().getColor(R.color.md_theme_light_error))
                    cardStock.tvStock.text = getString(R.string.msg_stock_without_stock)
                    bottomOptions.fabAddCart.isEnabled = false
                }
                Glide
                    .with(requireContext())
                    .load(plantEntity.imageUrl)
                    .centerCrop()
                    .circleCrop()
                    .placeholder(R.drawable.ic_broken_image)
                    .into(imgPlant)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}