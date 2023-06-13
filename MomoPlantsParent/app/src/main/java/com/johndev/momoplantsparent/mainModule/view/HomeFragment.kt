package com.johndev.momoplantsparent.mainModule.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johndev.momoplantsparent.R
import com.johndev.momoplantsparent.adapter.PlantAdapter
import com.johndev.momoplantsparent.addModule.view.AddDialogFragment
import com.johndev.momoplantsparent.common.dataAccess.OnProductListener
import com.johndev.momoplantsparent.common.entities.PlantEntity
import com.johndev.momoplantsparent.databinding.FragmentHomeBinding
import com.johndev.momoplantsparent.mainModule.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OnProductListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var plantAdapter: PlantAdapter

    companion object {
        lateinit var homeViewModel: HomeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        initRecyclerView(view)
    }

    private fun setupViewModel() {
        val vm: HomeViewModel by viewModels()
        homeViewModel = vm
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
        homeViewModel.onPlantSelected(plantEntity)
        AddDialogFragment().show(parentFragmentManager, AddDialogFragment::class.java.simpleName)
    }

    override fun onLongClick(plantEntity: PlantEntity) {
        homeViewModel.onDelete(plantEntity, requireContext())
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