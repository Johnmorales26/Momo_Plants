package com.johndev.momoplants.detailModule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
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
        detailViewModel.onSearchPlant(intent.getStringExtra(PLANT_ID_INTENT), this)
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
            binding.tvPlantName.text = it.name.toString().trim()
            binding.tvDescription.text = it.description.toString().trim()
            binding.bottomOptions.tvPrice.text = it.price.toString().trim()
            if (plantEntity.quantity > 0) {
                binding.cardStock.containerCard.setBackgroundColor(getColor(R.color.md_theme_light_primaryContainer))
                binding.cardStock.tvStock.text = getString(R.string.msg_stock_with_stock)
                binding.bottomOptions.fabAddCart.isEnabled = true
            } else {
                binding.cardStock.containerCard.setBackgroundColor(getColor(R.color.md_theme_light_error))
                binding.cardStock.tvStock.text = getString(R.string.msg_stock_without_stock)
                binding.bottomOptions.fabAddCart.isEnabled = false
            }
            Glide
                .with(this)
                .load(plantEntity.imageUrl)
                .centerCrop()
                .circleCrop()
                .placeholder(R.drawable.ic_broken_image)
                .into(binding.imgPlant)
        }
    }
}