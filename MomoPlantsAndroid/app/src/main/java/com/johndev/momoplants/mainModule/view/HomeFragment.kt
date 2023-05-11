package com.johndev.momoplants.mainModule.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johndev.momoplants.common.dataAccess.OnClickListener
import com.johndev.momoplants.R
import com.johndev.momoplants.adapter.PlantAdapter
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.common.utils.Constants.PLANT_ID
import com.johndev.momoplants.common.utils.getAllPlants
import com.johndev.momoplants.detailsModule.view.DetailsActivity

class HomeFragment : Fragment(), OnClickListener {

    private lateinit var plantAdapter: PlantAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
    }

    private fun initRecyclerView(view: View) {
        plantAdapter = PlantAdapter(getAllPlants(), this)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerPlant)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = plantAdapter
        }
    }

    override fun onClicListener(plantEntity: PlantEntity) {
        val intent = Intent(requireContext(), DetailsActivity::class.java).apply {
            putExtra(PLANT_ID, plantEntity.plant_id)
        }
        startActivity(intent)
    }
}