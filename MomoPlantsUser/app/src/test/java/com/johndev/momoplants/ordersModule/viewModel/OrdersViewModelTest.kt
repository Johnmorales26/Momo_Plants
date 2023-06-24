package com.johndev.momoplants.ordersModule.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.johndev.momoplants.common.entities.OrderEntity
import com.johndev.momoplants.ordersModule.model.OrdersRepository
import io.mockk.mockk
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class OrdersViewModelTest {

    private lateinit var orderReposirory: OrdersRepository
    private lateinit var ordersViewModel: OrdersViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        orderReposirory = mockk()
        ordersViewModel = OrdersViewModel(orderReposirory)
    }

    @Test
    fun setupFirestoreRealtimeRemoved() {
        val observer = Observer<OrderEntity>{}
        try {
            val orderEntity = OrderEntity()
            ordersViewModel.orderRemoved.observeForever(observer)
            ordersViewModel.setupFirestoreRealtimeRemoved(orderEntity)
            val result = ordersViewModel.orderRemoved.value
            MatcherAssert.assertThat(result, not(nullValue()))
            MatcherAssert.assertThat(result, `is`(orderEntity))
        } finally {
            ordersViewModel.orderRemoved.removeObserver(observer)
        }
    }

    @Test
    fun setupFirestoreRealtimeModified() {
        val observer = Observer<OrderEntity>{}
        try {
            val orderEntity = OrderEntity()
            ordersViewModel.orderModified.observeForever(observer)
            ordersViewModel.setupFirestoreRealtimeModified(orderEntity)
            val result = ordersViewModel.orderModified.value
            MatcherAssert.assertThat(result, not(nullValue()))
            MatcherAssert.assertThat(result, `is`(orderEntity))
        } finally {
            ordersViewModel.orderModified.removeObserver(observer)
        }
    }

    @Test
    fun setupFirestoreRealtimeAdded() {
        val observer = Observer<OrderEntity>{}
        try {
            val orderEntity = OrderEntity()
            ordersViewModel.orderAdded.observeForever(observer)
            ordersViewModel.setupFirestoreRealtimeAdded(orderEntity)
            val result = ordersViewModel.orderAdded.value
            MatcherAssert.assertThat(result, not(nullValue()))
            MatcherAssert.assertThat(result, `is`(orderEntity))
        } finally {
            ordersViewModel.orderAdded.removeObserver(observer)
        }
    }

}