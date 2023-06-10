package com.johndev.momoplants.ordersModule.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.johndev.momoplants.adapters.OrderAdapter
import com.johndev.momoplants.common.dataAccess.OnOrderListener
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.common.utils.Constants.COLL_REQUESTS
import com.johndev.momoplants.databinding.FragmentOrdersBinding
import com.johndev.momoplants.mainModule.view.MainActivity.Companion.trackViewModel
import com.johndev.momoplants.trackModule.view.TrackDialogFragment

class OrdersFragment : Fragment(), OnOrderListener {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!
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
        setupRecyclerView()
        setupFirestore()
    }

    private fun setupRecyclerView() {
        orderAdapter = OrderAdapter(mutableListOf(), this)
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderAdapter
        }
    }

    private fun setupFirestore() {
        val user = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()
        db.collection(COLL_REQUESTS).get()
            .addOnSuccessListener {
                for (document in it) {
                    val order = document.toObject(OrderEntity::class.java)
                    user?.let {
                        if (user.uid == order.clientId) {
                            order.id = document.id
                            orderAdapter.add(order)
                        }
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error al consultar los datos", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onTrack(orderEntity: OrderEntity) {
        /*val intent = Intent(requireContext(), TrackActivity::class.java).apply {
            putExtra(ORDER_ID_INTENT, orderEntity.id)
        }
        startActivity(intent)*/
        trackViewModel.onGetIdOrder(orderEntity.id)
        trackViewModel.onGetStatusOrder(orderEntity.status)
        TrackDialogFragment().show(parentFragmentManager, TrackDialogFragment::class.java.simpleName)
    }

    override fun onStartChat(orderEntity: OrderEntity) {
        Toast.makeText(requireContext(), "Iniciando Chat", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}