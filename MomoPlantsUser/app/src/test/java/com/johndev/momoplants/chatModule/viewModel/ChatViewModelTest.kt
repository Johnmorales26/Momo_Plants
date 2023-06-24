package com.johndev.momoplants.chatModule.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.johndev.momoplants.chatModule.model.ChatRepository
import com.johndev.momoplants.common.entities.OrderEntity
import io.mockk.mockk
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChatViewModelTest {

    private lateinit var chatRepository: ChatRepository
    private lateinit var chatViewModel: ChatViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        chatRepository = mockk()
        chatViewModel = ChatViewModel(chatRepository)
    }

    @Test
    fun getOrderSuccess() {
        val observer = Observer<OrderEntity>{}
        try {
            val orderEntity = OrderEntity(
                id = "2Em1ur72Uzp8AEAISQ4x",
                clientId = "fDGw6vcC4YZAVb0LLWJETyRMzmV2",
                products = mapOf(),
                totalPrice = 52.0,
                status = 0
            )
            chatViewModel.orderEntity.observeForever(observer)
            chatViewModel.getOrderSuccess(orderEntity)
            val result = chatViewModel.orderEntity.value
            MatcherAssert.assertThat(result, CoreMatchers.not(CoreMatchers.nullValue()))
        } finally {
            chatViewModel.orderEntity.removeObserver(observer)
        }
    }

    @Test
    fun getOrderError() {
        val observer = Observer<Boolean>{}
        try {
            chatViewModel.lostOrder.observeForever(observer)
            chatViewModel.getOrderError(true)
            val result = chatViewModel.lostOrder.value
            MatcherAssert.assertThat(result, CoreMatchers.not(CoreMatchers.nullValue()))
            MatcherAssert.assertThat(result, `is`(true))
        } finally {
            chatViewModel.lostOrder.removeObserver(observer)
        }
    }

    @Test
    fun deleteMessageSuccess() {
        val observer = Observer<Boolean>{}
        try {
            chatViewModel.deleteMessage.observeForever(observer)
            chatViewModel.deleteMessageSuccess(true)
            val result = chatViewModel.deleteMessage.value
            MatcherAssert.assertThat(result, CoreMatchers.not(CoreMatchers.nullValue()))
            MatcherAssert.assertThat(result, `is`(true))
        } finally {
            chatViewModel.deleteMessage.removeObserver(observer)
        }
    }

    @Test
    fun sendMessageEnabled() {
        val observer = Observer<Boolean>{}
        try {
            chatViewModel.enableButton.observeForever(observer)
            chatViewModel.sendMessageEnabled(true)
            val result = chatViewModel.enableButton.value
            MatcherAssert.assertThat(result, CoreMatchers.not(CoreMatchers.nullValue()))
            MatcherAssert.assertThat(result, `is`(true))
        } finally {
            chatViewModel.enableButton.removeObserver(observer)
        }
    }

}