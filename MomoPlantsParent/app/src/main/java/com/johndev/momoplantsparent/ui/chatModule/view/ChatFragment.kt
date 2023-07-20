package com.johndev.momoplantsparent.ui.chatModule.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.momoplantsparent.R
import com.johndev.momoplantsparent.adapter.ChatAdapter
import com.johndev.momoplantsparent.ui.chatModule.viewModel.ChatViewModel
import com.johndev.momoplantsparent.common.dataAccess.OnChatListener
import com.johndev.momoplantsparent.common.entities.MessageEntity
import com.johndev.momoplantsparent.common.entities.OrderEntity
import com.johndev.momoplantsparent.common.utils.editable
import com.johndev.momoplantsparent.common.utils.printMessage
import com.johndev.momoplantsparent.databinding.FragmentChatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment(), OnChatListener {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val args : ChatFragmentArgs by navArgs()
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var orderEntity: OrderEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        receiveOrder()
        setupObservers()
        setupToolbar()
        setupRecyclerView()
        setupButtons()
    }

    private fun initViewModel() {
        val vm: ChatViewModel by viewModels()
        chatViewModel = vm
    }

    private fun receiveOrder() {
        args.id0rder?.let {
            chatViewModel.getOrderById(it)
        }
    }

    private fun setupToolbar() {
        binding.toolbar.title = getString(R.string.chat_title)
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    private fun setupObservers() {
        chatViewModel.orderEntity.observe(viewLifecycleOwner) { order ->
            order?.let {
                orderEntity = order
                chatViewModel.setupRealtimeDatabase(chatAdapter) {
                    binding.recyclerview.scrollToPosition(chatAdapter.itemCount - 1)
                }
            }
        }
        chatViewModel.msg.observe(viewLifecycleOwner) {
            it?.let { msg ->
                printMessage(msg, context = requireContext())
            }
        }
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(mutableListOf(), this)
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext()).also {
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
        chatViewModel.deleteMessage(
            orderId = orderEntity.id,
            messageId = messageEntity.id
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}