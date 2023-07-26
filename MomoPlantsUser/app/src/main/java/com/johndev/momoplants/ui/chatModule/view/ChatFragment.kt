package com.johndev.momoplants.ui.chatModule.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.johndev.momoplants.R
import com.johndev.momoplants.adapters.ChatAdapter
import com.johndev.momoplants.common.dataAccess.OnChatListener
import com.johndev.momoplants.common.entities.MessageEntity
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.common.utils.Constants
import com.johndev.momoplants.common.utils.editable
import com.johndev.momoplants.common.utils.printErrorToast
import com.johndev.momoplants.common.utils.printNormalToast
import com.johndev.momoplants.common.utils.printSnackbarMsg
import com.johndev.momoplants.databinding.FragmentChatBinding
import com.johndev.momoplants.ui.chatModule.viewModel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment(), OnChatListener {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var orderEntity: OrderEntity
    private val args : ChatFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
                chatViewModel.setupRealtimeDatabase(
                    chatAdapter = chatAdapter,
                    updateScroll = {
                        binding.recyclerview.scrollToPosition(chatAdapter.itemCount - 1)
                    }, onCancelled = {
                        printErrorToast(R.string.chat_error_load,  context = requireContext())
                    }
                )
            }
        }
        chatViewModel.deleteMessage.observe(viewLifecycleOwner) { isSuccess ->
            if (!isSuccess) {
                printSnackbarMsg(binding.root, R.string.chat_error_deleting_message, requireContext())
            } else {
                printSnackbarMsg(binding.root, R.string.chat_success_deleting_message, requireContext())
            }
        }
        chatViewModel.enableButton.observe(viewLifecycleOwner) {
            binding.ibSend.isEnabled = it
        }
        chatViewModel.msg.observe(viewLifecycleOwner) {
            if (it == R.string.chat_order_not_found) printErrorToast(it, context = requireContext()) else printNormalToast(it, context = requireContext())
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}