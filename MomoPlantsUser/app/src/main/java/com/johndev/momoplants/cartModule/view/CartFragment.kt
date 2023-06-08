package com.johndev.momoplants.cartModule.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.momoplants.R
import com.johndev.momoplants.adapter.PlantCartAdapter
import com.johndev.momoplants.common.dataAccess.OnCartListener
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.databinding.FragmentCartBinding
import com.johndev.momoplants.mainModule.view.MainActivity.Companion.cartViewModel

class CartFragment : Fragment(), OnCartListener {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var plantCartAdapter: PlantCartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        updateBottomOptions()
        setupObservers()
    }

    private fun setupObservers() {
        cartViewModel.totalPrice.observe(viewLifecycleOwner) {
            it?.let { price ->
                binding.bottomOptions.tvPrice.text = price.toString().trim()
            }
        }
        cartViewModel.listCart.observe(viewLifecycleOwner) {
            it?.let { it.forEach { plantCartAdapter.add(it) } }
        }
    }

    private fun updateBottomOptions() {
        binding.let {
            it.bottomOptions.fabAddCart.setIconResource(R.drawable.ic_payment)
            it.bottomOptions.fabAddCart.text = "Check Out"
        }
    }

    private fun setupRecyclerView() {
        binding.let {
            plantCartAdapter = PlantCartAdapter(mutableListOf(), this)
            it.recyclerview.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = plantCartAdapter
            }
        }
    }

    override fun incrementQuantity(plantEntity: PlantEntity) {
        cartViewModel.updateQuantity(plantEntity, true, plantCartAdapter)
    }

    override fun decrementQuantity(plantEntity: PlantEntity) {
        cartViewModel.updateQuantity(plantEntity, false, plantCartAdapter)
    }

    override fun showTotal(total: Double) {

    }

    override fun onResume() {
        super.onResume()
        cartViewModel.getCartList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}