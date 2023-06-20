package com.johndev.momoplants.cartModule.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.momoplants.R
import com.johndev.momoplants.adapters.PlantCartAdapter
import com.johndev.momoplants.cartModule.viewModel.CartViewModel
import com.johndev.momoplants.common.dataAccess.OnCartListener
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.common.utils.printToastMsg
import com.johndev.momoplants.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(), OnCartListener {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var plantCartAdapter: PlantCartAdapter
    private lateinit var cartViewModel: CartViewModel
    private var totalPrice = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setupRecyclerView()
        updateBottomOptions()
        setupObservers()
        cartViewModel.getCartList()
    }

    private fun initViewModel() {
        val vmCart: CartViewModel by viewModels()
        cartViewModel = vmCart
    }

    private fun setupObservers() {
        cartViewModel.listCart.observe(viewLifecycleOwner) {
            it?.let {
                with(binding) {
                    if (it.isEmpty()) {
                        plantCartAdapter.clearCart()
                        animationView.visibility = VISIBLE
                        recyclerview.visibility = GONE
                    } else {
                        animationView.visibility = GONE
                        recyclerview.visibility = VISIBLE
                        it.forEach { plant ->
                            plantCartAdapter.add(plant)
                        }
                    }
                }
            }
        }
    }

    private fun updateBottomOptions() {
        binding.let {
            it.bottomOptions.fabAddCart.setIconResource(R.drawable.ic_payment)
            it.bottomOptions.fabAddCart.text = getString(R.string.btn_check_out)
            it.bottomOptions.fabAddCart.setOnClickListener { requestOrder() }
        }
    }

    private fun requestOrder() {
        cartViewModel.onRequestOrder(
            plantCartAdapter = plantCartAdapter,
            totalPrice = totalPrice,
            enableUI = { enableUI(it) },
            onSuccess = { printToastMsg(R.string.cart_purchase_made, requireContext()) },
            onFailure = { printToastMsg(R.string.cart_error_buying, requireContext()) })
    }

    private fun enableUI(isEnable: Boolean) {
        binding.bottomOptions.fabAddCart.isEnabled = isEnable
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
        Log.i("CartFragment", "showTotal: $total")
        totalPrice = total
        if (total == 0.0) {
            binding.bottomOptions.tvPrice.text = getString(R.string.product_empty_cart)
        } else {
            binding.bottomOptions.tvPrice.text = getString(R.string.product_full_cart, totalPrice)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}