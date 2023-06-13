package com.johndev.momoplants.detailModule.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.johndev.momoplants.R
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.common.utils.Constants.PLANT_ID_INTENT
import com.johndev.momoplants.databinding.ActivityDetailsBinding
import com.johndev.momoplants.detailModule.viewModel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var detailViewModel: DetailViewModel
    private var plantEntity: PlantEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModels()
        setupObservers()
        setupButtons()
        detailViewModel.onSearchPlantRealtime(intent.getStringExtra(PLANT_ID_INTENT).toString())

        var like = false

        val corazon = findViewById<View>(R.id.likeImageView)
        corazon.setOnClickListener {
            like = likeAnimation(corazon as LottieAnimationView, R.raw.black_joy, like)
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
        detailViewModel.plantEntity.observe(this) { plantEntity ->
            plantEntity?.let {
                this.plantEntity = it
                updateUI(this.plantEntity)
            }
        }
    }

    private fun setupButtons() {
        binding.ibReturn.setOnClickListener { finish() }
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
                    cardStock.containerCard.setBackgroundColor(getColor(R.color.md_theme_light_primaryContainer))
                    cardStock.tvStock.text = getString(R.string.msg_stock_with_stock)
                    bottomOptions.fabAddCart.isEnabled = true
                } else {
                    cardStock.containerCard.setBackgroundColor(getColor(R.color.md_theme_light_error))
                    cardStock.tvStock.text = getString(R.string.msg_stock_without_stock)
                    bottomOptions.fabAddCart.isEnabled = false
                }
                Glide
                    .with(this@DetailsActivity)
                    .load(plantEntity.imageUrl)
                    .centerCrop()
                    .circleCrop()
                    .placeholder(R.drawable.ic_broken_image)
                    .into(imgPlant)
            }
        }
    }
}