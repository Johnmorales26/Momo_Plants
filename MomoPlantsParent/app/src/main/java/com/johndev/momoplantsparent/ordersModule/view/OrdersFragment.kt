package com.johndev.momoplantsparent.ordersModule.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.momoplantsparent.adapter.OrderAdapter
import com.johndev.momoplantsparent.chatModule.view.ChatActivity
import com.johndev.momoplantsparent.common.dataAccess.OnOrderListener
import com.johndev.momoplantsparent.common.entities.OrderEntity
import com.johndev.momoplantsparent.common.utils.Constants.CHAT_ID_INTENT
import com.johndev.momoplantsparent.common.utils.printToastMsg
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
        ordersViewModel.setupFirestore()
    }

    private fun initViewModel() {
        val vm: OrdersViewModel by viewModels()
        ordersViewModel = vm
    }

    private fun setupObservers() {
        ordersViewModel.orderList.observe(viewLifecycleOwner) {
            it.forEach { orderAdapter.add(it) }
        }
        ordersViewModel.msg.observe(viewLifecycleOwner) {
            printToastMsg(it, requireContext())
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
        ordersViewModel.statusChange(orderEntity, requireContext())
    }

    override fun onStartChat(orderEntity: OrderEntity) {
        val intent = Intent(requireContext(), ChatActivity::class.java).apply {
            putExtra(CHAT_ID_INTENT, orderEntity.id)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}