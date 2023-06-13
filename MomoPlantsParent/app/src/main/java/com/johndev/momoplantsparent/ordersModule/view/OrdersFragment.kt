package com.johndev.momoplantsparent.ordersModule.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.johndev.momoplantsparent.adapter.OrderAdapter
import com.johndev.momoplantsparent.common.dataAccess.OnOrderListener
import com.johndev.momoplantsparent.common.entities.OrderEntity
import com.johndev.momoplantsparent.common.utils.Constants.COLL_REQUESTS
import com.johndev.momoplantsparent.common.utils.Constants.PROP_STATUS
import com.johndev.momoplantsparent.databinding.FragmentOrdersBinding
import com.johndev.momoplantsparent.ordersModule.viewModel.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : Fragment(), OnOrderListener {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!
    private lateinit var ordersViewModel: OrdersViewModel
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setupObservers()
        setupRecyclerView()
        ordersViewModel.setupFirestore(requireContext())
    }

    private fun initViewModel() {
        val vm: OrdersViewModel by viewModels()
        ordersViewModel = vm
    }

    private fun setupObservers() {
        ordersViewModel.orderList.observe(viewLifecycleOwner) {
            it.forEach { orderAdapter.add(it) }
        }
    }

    private fun setupRecyclerView() {
        orderAdapter = OrderAdapter(mutableListOf(), this)
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderAdapter
        }
    }

    override fun onStatusChange(orderEntity: OrderEntity) {
        ordersViewModel.onStatusChange(orderEntity, requireContext())
    }

    override fun onStartChat(orderEntity: OrderEntity) {
        Toast.makeText(requireContext(), "Iniciando Chat", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}