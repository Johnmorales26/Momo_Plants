package com.johndev.momoplants.detailsModule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.widget.Toast
import com.johndev.momoplants.common.utils.Constants.PLANT_ID
import com.johndev.momoplants.common.utils.getAllPlants
import com.johndev.momoplants.databinding.ActivityDetailsBinding
import java.lang.StringBuilder

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        searchPlant(intent.getIntExtra(PLANT_ID, 0))
        setupButtons()
    }

    private fun setupButtons() {
        binding.btnAdd.setOnClickListener {
            Toast.makeText(this, "Soon available this option", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun searchPlant(idPlant: Int) {
        val plant = getAllPlants().find { item -> item.plant_id == idPlant }
        plant?.let {
            binding.apply {
                progressCircular.visibility = GONE
                tvName.text = plant.name.trim()
                tvDescription.text = plant.description.trim()
                tvFormat.text = plant.format.trim()
                tvOrigin.text = plant.origin.trim()
                tvWeather.text = plant.weather.trim()
                tvPrice.text = StringBuilder().append("$").append(plant.price.toString().trim()).toString()
            }
        } ?: run {
            binding.apply {
                cardview.visibility = GONE
                imgCover.visibility = GONE
            }
        }
    }
}