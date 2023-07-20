package com.johndev.momoplants.ui.homeModule.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johndev.momoplants.R
import com.johndev.momoplants.adapters.PlantAdapter
import com.johndev.momoplants.common.dataAccess.OnProductListener
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.common.utils.Constants.PLANT_ID_INTENT
import com.johndev.momoplants.common.utils.printSnackbarMsg
import com.johndev.momoplants.databinding.FragmentHomeBinding
import com.johndev.momoplants.ui.detailModule.view.DetailsActivity
import com.johndev.momoplants.ui.homeModule.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OnProductListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var plantAdapter: PlantAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecyclerView(view)
        setupObservers()
    }

    private fun initViewModel() {
        val vmHome: HomeViewModel by viewModels()
        homeViewModel = vmHome
    }

    private fun setupObservers() {
        homeViewModel.run {
            addPlant.observe(viewLifecycleOwner) {
                it?.let { plant -> plantAdapter.add(plant) }
            }
            updatePlant.observe(viewLifecycleOwner) {
                it?.let { plant -> plantAdapter.update(plant) }
            }
            removedPlant.observe(viewLifecycleOwner) {
                it?.let { plant -> plantAdapter.delete(plant) }
            }
        }
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
        homeViewModel.configFirestoreRealtime(
            onQueryError = { msg ->
                printSnackbarMsg(binding.root, msg, requireContext())
            }
        )
    }

    override fun onPause() {
        super.onPause()
        homeViewModel.onRemoveListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}