package com.johndev.momoplants.chatModule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.momoplants.R
import com.johndev.momoplants.adapters.ChatAdapter
import com.johndev.momoplants.chatModule.viewModel.ChatViewModel
import com.johndev.momoplants.common.dataAccess.OnChatListener
import com.johndev.momoplants.common.entities.MessageEntity
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.common.utils.Constants.CHAT_ID_INTENT
import com.johndev.momoplants.common.utils.editable
import com.johndev.momoplants.common.utils.printSnackbarMsg
import com.johndev.momoplants.common.utils.printToastMsg
import com.johndev.momoplants.databinding.ActivityChatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : AppCompatActivity(), OnChatListener {

    private lateinit var binding: ActivityChatBinding
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var orderEntity: OrderEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        receiveIdOrder()
        setupObservers()
        setupToolbar()
        setupRecyclerView()
        setupButtons()
    }

    private fun initViewModel() {
        val vm: ChatViewModel by viewModels()
        chatViewModel = vm
    }

    private fun receiveIdOrder() {
        val idOrder = intent.getStringExtra(CHAT_ID_INTENT)
        chatViewModel.getOrder(idOrder)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.title = getString(R.string.chat_title)
        }
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupObservers() {
        chatViewModel.orderEntity.observe(this) { order ->
            order?.let {
                orderEntity = order
                chatViewModel.setupRealtimeDatabase(
                    chatAdapter = chatAdapter,
                    updateScroll = {
                        binding.recyclerview.scrollToPosition(chatAdapter.itemCount - 1)
                    }, onCancelled = {
                        printToastMsg(R.string.chat_error_load, this)
                    }
                )
            }
        }
        chatViewModel.deleteMessage.observe(this) { isSuccess ->
            if (!isSuccess) {
                printSnackbarMsg(binding.root, R.string.chat_error_deleting_message, this)
            } else {
                printSnackbarMsg(binding.root, R.string.chat_success_deleting_message, this)
            }
        }
        chatViewModel.enableButton.observe(this) {
            binding.ibSend.isEnabled = it
        }
        chatViewModel.msg.observe(this) {
            printToastMsg(it, this)
        }
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(mutableListOf(), this)
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity).also {
                it.stackFromEnd = true
            }
            adapter = chatAdapter
        }
    }

    private fun setupButtons() {
        binding.ibSend.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        chatViewModel.onSendMessage(
            message = binding.etMessage.text.toString().trim(),
        )
        binding.etMessage.text = "".editable()
    }

    override fun deleteMessage(messageEntity: MessageEntity) {
        chatViewModel.deleteMessage(messageEntity)
    }

}