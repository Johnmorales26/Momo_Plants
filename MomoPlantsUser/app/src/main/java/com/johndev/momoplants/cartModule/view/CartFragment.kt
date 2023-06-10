package com.johndev.momoplants.cartModule.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firestore.v1.StructuredQuery.Order
import com.johndev.momoplants.R
import com.johndev.momoplants.adapters.PlantCartAdapter
import com.johndev.momoplants.common.dataAccess.OnCartListener
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.common.entities.PlantOrderEntity
import com.johndev.momoplants.common.utils.Constants
import com.johndev.momoplants.databinding.FragmentCartBinding
import com.johndev.momoplants.mainModule.view.MainActivity.Companion.cartViewModel

class CartFragment : Fragment(), OnCartListener {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var plantCartAdapter: PlantCartAdapter
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
        setupRecyclerView()
        updateBottomOptions()
        setupObservers()
        cartViewModel.getCartList()
    }

    private fun setupObservers() {
        cartViewModel.listCart.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isEmpty()) {
                    plantCartAdapter.clearCart()
                } else {
                    it.forEach {
                        plantCartAdapter.add(it)
                    }
                }
            }
        }
    }

    private fun updateBottomOptions() {
        binding.let {
            it.bottomOptions.fabAddCart.setIconResource(R.drawable.ic_payment)
            it.bottomOptions.fabAddCart.text = "Check Out"
            it.bottomOptions.fabAddCart.setOnClickListener { requestOrder() }
        }
    }

    private fun requestOrder() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {  myUser ->
            enableUI(false)
            val plants = hashMapOf<String, PlantOrderEntity>()
            plantCartAdapter.getPlants().forEach { plant ->
                plants[plant.plantId] = PlantOrderEntity(plant.plantId, plant.name!!, plant.quantity)
            }
            val order = OrderEntity(
                clientId = myUser.uid,
                products = plants,
                totalPrice = totalPrice,
                status = 1
            )
            val db = FirebaseFirestore.getInstance()
            db.collection(Constants.COLL_REQUESTS)
                .add(order)
                .addOnSuccessListener {
                    plantCartAdapter.clearCart()
                    cartViewModel.clearCart()
                    Toast.makeText(requireContext(), "Compra realizada", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Error al comprar", Toast.LENGTH_SHORT).show()
                }
                .addOnCompleteListener {
                    enableUI(true)
                }
        }
    }

    private fun enableUI(isEnable: Boolean) {
        binding.let {
            it.bottomOptions.fabAddCart.isExtended = isEnable
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
        Log.i("CartFragment", "showTotal: $total")
        totalPrice = total
        if (total == 0.0){
            binding.bottomOptions.tvPrice.text = getString(R.string.product_empty_cart)
        } else {
            binding.bottomOptions.tvPrice.text = getString(R.string.product_full_cart, totalPrice)
        }
    }

    override fun onResume() {
        super.onResume()
        //cartViewModel.getCartList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}