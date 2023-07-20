package com.johndev.momoplants.ui.trackModule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import coil.load
import com.johndev.momoplants.R
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.common.utils.Constants.ORDER_ID_INTENT
import com.johndev.momoplants.common.utils.lauchNotification
import com.johndev.momoplants.common.utils.printErrorToast
import com.johndev.momoplants.common.utils.printSuccessToast
import com.johndev.momoplants.databinding.ActivityTrackBinding
import com.johndev.momoplants.ui.trackModule.viewModel.TrackViewModel
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
        receiveValues()
        setupObservers()
        trackViewModel.parseScreenInput()
    }

    private fun initViewModel() {
        val vm: TrackViewModel by viewModels()
        trackViewModel = vm
    }

    private fun setupObservers() {
        trackViewModel.orderEntity.observe(this) {
            if (it == null) {
                //binding.viewCompose.visibility = GONE
            } else {
                //binding.viewCompose.visibility = VISIBLE
                updateUI(it)
            }
        }
        trackViewModel.status.observe(this) {
            lauchNotification(this, it)
        }
        trackViewModel.msg.observe(this) {
            if (it == R.string.chat_order_found) printSuccessToast(it, context = this) else printErrorToast(it, context = this)
        }
    }

    private fun updateUI(orderEntity: OrderEntity) {
        val progress = (orderEntity.status * (100 / 3) - 15)
        val image = when (orderEntity.status) {
            1 -> R.drawable.ic_ordered_package
            2 -> R.drawable.ic_preparing_package
            3 -> R.drawable.ic_send_package
            4 -> R.drawable.ic_delivered_package
            else -> R.drawable.ic_unknown_package
        }
        val subtitle = when (orderEntity.status) {
            1 -> getString(R.string.order_status_ordered_subtitle)
            2 -> getString(R.string.order_status_preparing_subtitle)
            3 -> getString(R.string.order_status_send_subtitle)
            4 -> getString(R.string.order_status_delivered_subtitle)
            else -> getString(R.string.order_status_unknown_subtitle)
        }
        val title = when (orderEntity.status) {
            1 -> getString(R.string.order_track_status, getString(R.string.order_status_ordered))
            2 -> getString(R.string.order_track_status, getString(R.string.order_status_preparing))
            3 -> getString(R.string.order_track_status, getString(R.string.order_status_send))
            4 -> getString(R.string.order_track_status, getString(R.string.order_status_delivered))
            else -> getString(R.string.order_track_status, getString(R.string.order_status_unknown))
        }
        binding.imgTrack.load(image) {
            crossfade(true)
            placeholder(R.drawable.ic_image)
            //transformations(CircleCropTransformation())
        }
        binding.tvTitle.text = title
        binding.tvDescription.text = subtitle
        binding.progress.progress = progress
    }

    private fun receiveValues() {
        trackViewModel.apply {
            getOrderById(intent.getStringExtra(ORDER_ID_INTENT).toString())
            getOrderInRealtime(intent.getStringExtra(ORDER_ID_INTENT).toString())
        }

    }

}