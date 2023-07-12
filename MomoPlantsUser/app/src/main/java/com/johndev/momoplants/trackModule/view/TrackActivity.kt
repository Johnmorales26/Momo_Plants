package com.johndev.momoplants.trackModule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.google.android.material.composethemeadapter.MdcTheme
import com.johndev.momoplants.R
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.common.utils.Constants.ORDER_ID_INTENT
import com.johndev.momoplants.common.utils.lauchNotification
import com.johndev.momoplants.common.utils.printToastMsg
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
                binding.viewCompose.visibility = GONE
            } else {
                binding.viewCompose.visibility = VISIBLE
                updateUI(it)
            }
        }
        trackViewModel.status.observe(this) {
            lauchNotification(this, it)
        }
        trackViewModel.msg.observe(this) {
            printToastMsg(it, this)
        }
    }

    private fun updateUI(orderEntity: OrderEntity) {
        val progress = (orderEntity.status * (100 / 3) - 15).toFloat()
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
        binding.viewCompose.setContent {
            MdcTheme {
                Scaffold(
                    topBar = {
                        Header()
                    },
                    content = {
                        Body(
                            paddingValues = it,
                            progress = progress,
                            image = image,
                            subtitle = subtitle,
                            title = title
                        )
                    }
                )
            }
        }
    }

    @Composable
    private fun Header() {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { finish() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = null
                    )
                }
            },
            title = { Text(text = stringResource(R.string.order_purchase_status)) }
        )
    }

    @Composable
    private fun Body(
        paddingValues: PaddingValues,
        progress: Float,
        image: Int,
        subtitle: String,
        title: String
    ) {
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.common_padding_default))
            ) {
                Box(
                    modifier = Modifier
                        .weight(2f)
                ) {
                    Image(
                        painter = painterResource(id = image),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h4,
                        color = colorResource(id = R.color.md_theme_light_primary),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = subtitle,
                        textAlign = TextAlign.Center
                    )
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        progress = progress
                    )
                }
            }
        }
    }

    private fun receiveValues() {
        trackViewModel.apply {
            getOrderById(intent.getStringExtra(ORDER_ID_INTENT).toString())
            getOrderInRealtime(intent.getStringExtra(ORDER_ID_INTENT).toString())
        }

    }

}