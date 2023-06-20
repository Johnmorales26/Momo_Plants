package com.johndev.momoplantsparent.chatModule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.johndev.momoplantsparent.chatModule.viewModel.ChatViewModel
import com.johndev.momoplantsparent.R
import com.johndev.momoplantsparent.adapter.ChatAdapter
import com.johndev.momoplantsparent.common.dataAccess.OnChatListener
import com.johndev.momoplantsparent.common.entities.MessageEntity
import com.johndev.momoplantsparent.common.entities.OrderEntity
import com.johndev.momoplantsparent.common.utils.Constants.CHAT_ID_INTENT
import com.johndev.momoplantsparent.common.utils.Constants.PATH_CHATS
import com.johndev.momoplantsparent.common.utils.editable
import com.johndev.momoplantsparent.common.utils.printToastMsg
import com.johndev.momoplantsparent.databinding.ActivityChatBinding
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
        recibeIdOrder()
        setupObservers()
        setupToolbar()
        setupRecyclerView()
        setupButtons()
    }

    private fun initViewModel() {
        val vm: ChatViewModel by viewModels()
        chatViewModel = vm
    }

    private fun recibeIdOrder() {
        intent.getStringExtra(CHAT_ID_INTENT)?.let {
            chatViewModel.getOrderById(it)
        }
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
                chatViewModel.setupRealtimeDatabase(chatAdapter) {
                    binding.recyclerview.scrollToPosition(chatAdapter.itemCount - 1)
                }
            }
        }
        chatViewModel.msg.observe(this) {
            it?.let { msg -> printToastMsg(msg, this) }
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
            chatViewModel.sendMessage(idOrder = orderEntity.id,
                message = binding.etMessage.text.toString().trim(),
                onClearMessage = { binding.etMessage.text = "".editable() },
                onButtonEnable = { binding.ibSend.isEnabled = it })
        }
    }

    override fun deleteMessage(messageEntity: MessageEntity) {
        val database = Firebase.database
        val messageRef =
            database.getReference(PATH_CHATS).child(orderEntity.id).child(messageEntity.id)
        messageRef.removeValue { error, ref ->
            if (error != null) {
                Snackbar.make(binding.root, "Error al eliminar mensaje.", Snackbar.LENGTH_LONG)
                    .show()
            } else {
                Snackbar.make(binding.root, "Mensaje eliminado.", Snackbar.LENGTH_LONG).show()
            }
        }
    }

}