package com.johndev.momoplants.trackModule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import com.johndev.momoplants.R
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.common.utils.Constants.ORDER_ID_INTENT
import com.johndev.momoplants.common.utils.executeOrRequestPermission
import com.johndev.momoplants.common.utils.lauchNotification
import com.johndev.momoplants.common.utils.simpleNotification
import com.johndev.momoplants.databinding.ActivityTrackBinding
import com.johndev.momoplants.trackModule.viewModel.TrackViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrackBinding
    private lateinit var trackViewModel: TrackViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        setupObservers()
        setupToolbar()
        recibeValues()
    }

    private fun initViewModel() {
        val vm: TrackViewModel by viewModels()
        trackViewModel = vm
    }

    private fun setupObservers() {
        trackViewModel.orderEntity.observe(this) {
            if (it == null) {
                binding.animationView.visibility = VISIBLE
                binding.content.container.visibility = GONE
            } else {
                binding.animationView.visibility = GONE
                binding.content.container.visibility = VISIBLE
                updateIU(it)
            }
        }
        trackViewModel.status.observe(this) {
            lauchNotification(this, it)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.title = "Estado de la compra"
        }
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun recibeValues() {
        trackViewModel.apply {
            onGetOrder(intent.getStringExtra(ORDER_ID_INTENT).toString())
            getOrderInRealtime(intent.getStringExtra(ORDER_ID_INTENT).toString())
        }

    }
    private fun updateIU(orderEntity: OrderEntity) {
        with(binding) {
            content.let{
                it.tvOrder.text = getString(R.string.order_id, orderEntity.id)
                it.progressBar.progress = orderEntity.status * (100 / 3) - 15
                it.cbOrdered.isChecked = orderEntity.status > 0
                it.cbPreparing.isChecked = orderEntity.status > 1
                it.cbSend.isChecked = orderEntity.status > 2
                it.cbDelivered.isChecked = orderEntity.status > 3
            }
        }
    }

}