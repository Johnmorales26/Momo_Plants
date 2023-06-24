package com.johndev.momoplants.ordersModule.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.momoplants.adapters.OrderAdapter
import com.johndev.momoplants.chatModule.view.ChatActivity
import com.johndev.momoplants.common.dataAccess.OnOrderListener
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.common.utils.Constants.CHAT_ID_INTENT
import com.johndev.momoplants.common.utils.Constants.ORDER_ID_INTENT
import com.johndev.momoplants.common.utils.lauchNotification
import com.johndev.momoplants.common.utils.lauchNotificationWithReciber
import com.johndev.momoplants.common.utils.printToastMsg
import com.johndev.momoplants.databinding.FragmentOrdersBinding
import com.johndev.momoplants.ordersModule.viewModel.OrdersViewModel
import com.johndev.momoplants.trackModule.view.TrackActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : Fragment(), OnOrderListener {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var ordersViewModel: OrdersViewModel

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
        setupRecyclerView()
        setupObservers()
        ordersViewModel.onSetupFirestoreRealtime()
    }

    private fun initViewModel() {
        val vm: OrdersViewModel by viewModels()
        ordersViewModel = vm
    }

    private fun setupObservers() {
        ordersViewModel.orderList.observe(viewLifecycleOwner) {
            with(binding) {
                if (it.isEmpty()) {
                    animationView.visibility = VISIBLE
                    recyclerview.visibility = GONE
                } else {
                    animationView.visibility = GONE
                    recyclerview.visibility = VISIBLE
                    it.forEach { order -> orderAdapter.add(order) }
                }
            }
        }
        ordersViewModel.status.observe(viewLifecycleOwner) {
            lauchNotificationWithReciber(requireActivity(), it)
        }
        ordersViewModel.msg.observe(viewLifecycleOwner) {
            printToastMsg(it, requireContext())
        }
        ordersViewModel.orderAdded.observe(viewLifecycleOwner) { orderAdapter.add(it) }
        ordersViewModel.orderModified.observe(viewLifecycleOwner) { orderAdapter.update(it) }
        ordersViewModel.orderRemoved.observe(viewLifecycleOwner) { orderAdapter.delete(it) }
    }

    private fun setupRecyclerView() {
        orderAdapter = OrderAdapter(mutableListOf(), this)
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderAdapter
        }
    }

    override fun onTrack(orderEntity: OrderEntity) {
        val intent = Intent(requireContext(), TrackActivity::class.java).apply {
            putExtra(ORDER_ID_INTENT, orderEntity.id)
        }
        startActivity(intent)
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