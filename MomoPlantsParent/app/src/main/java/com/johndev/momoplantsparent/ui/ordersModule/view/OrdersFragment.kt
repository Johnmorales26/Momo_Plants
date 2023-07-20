package com.johndev.momoplantsparent.ui.ordersModule.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.johndev.momoplantsparent.R
import com.johndev.momoplantsparent.adapter.OrderAdapter
import com.johndev.momoplantsparent.common.dataAccess.OnOrderListener
import com.johndev.momoplantsparent.common.entities.OrderEntity
import com.johndev.momoplantsparent.common.utils.printMessage
import com.johndev.momoplantsparent.common.utils.printToastMsg
import com.johndev.momoplantsparent.databinding.FragmentOrdersBinding
import com.johndev.momoplantsparent.ui.ordersModule.viewModel.OrdersViewModel
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
            printMessage(it, context = requireContext())
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
        val action = OrdersFragmentDirections.actionOrdersToNavigationChat(orderEntity.id)
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.fab)
        val bottomNavigation = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        findNavController().addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_chat) {
                fab.hide()
                bottomNavigation.visibility = GONE
            } else {
                fab.show()
                bottomNavigation.visibility = VISIBLE
            }
        }
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}