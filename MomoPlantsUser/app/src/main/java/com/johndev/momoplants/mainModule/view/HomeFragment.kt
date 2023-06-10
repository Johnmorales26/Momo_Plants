package com.johndev.momoplants.mainModule.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johndev.momoplants.R
import com.johndev.momoplants.adapters.PlantAdapter
import com.johndev.momoplants.common.dataAccess.OnProductListener
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.common.utils.Constants.PLANT_ID_INTENT
import com.johndev.momoplants.databinding.FragmentHomeBinding
import com.johndev.momoplants.detailModule.view.DetailsActivity
import com.johndev.momoplants.mainModule.view.MainActivity.Companion.homeViewModel

class HomeFragment : Fragment(), OnProductListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var plantAdapter: PlantAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
    }

    private fun initRecyclerView(view: View) {
        plantAdapter = PlantAdapter(mutableListOf(), this)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerPlant)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = plantAdapter
        }
    }

    override fun onClick(plantEntity: PlantEntity) {
        val intent = Intent(requireContext(), DetailsActivity::class.java).apply {
            putExtra(PLANT_ID_INTENT, plantEntity.plantId)
        }
        startActivity(intent)
    }

    override fun onClickAdd(plantEntity: PlantEntity) {
        homeViewModel.onSave(plantEntity)
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.configFirestoreRealtime(requireContext(), plantAdapter)
    }

    override fun onPause() {
        super.onPause()
        homeViewModel.firestoreListener.remove()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}