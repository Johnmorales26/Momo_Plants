package com.johndev.momoplants.ui.ordersModule.view

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
import com.johndev.momoplants.adapters.OrderAdapter
import com.johndev.momoplants.common.dataAccess.OnOrderListener
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.common.utils.launchNotificationWithReceiver
import com.johndev.momoplants.common.utils.openFragment
import com.johndev.momoplants.common.utils.printErrorToast
import com.johndev.momoplants.databinding.FragmentOrdersBinding
import com.johndev.momoplants.ui.ordersModule.viewModel.OrdersViewModel
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
            launchNotificationWithReceiver(requireActivity(), it)
        }
        ordersViewModel.msg.observe(viewLifecycleOwner) {
            printErrorToast(it,  context = requireContext())
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
        val action = OrdersFragmentDirections.actionOrdersToNavigationTrack(orderEntity.id)
        openFragment(action, requireActivity(), findNavController())
    }

    override fun onStartChat(orderEntity: OrderEntity) {
        val action = OrdersFragmentDirections.actionOrdersToNavigationChat(orderEntity.id)
        openFragment(action, requireActivity(), findNavController())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}