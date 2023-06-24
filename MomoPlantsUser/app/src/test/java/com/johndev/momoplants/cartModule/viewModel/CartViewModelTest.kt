package com.johndev.momoplants.cartModule.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.johndev.momoplants.cartModule.model.CartRepository
import com.johndev.momoplants.common.entities.PlantEntity
import io.mockk.mockk
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class CartViewModelTest {

    private lateinit var cartRepository: CartRepository
    private lateinit var cartViewModel: CartViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        cartRepository = mockk()
        cartViewModel = CartViewModel(cartRepository)
    }

    @Test
    fun getCartList() {
        val observer = Observer<List<PlantEntity>>{}
        try {
            cartViewModel.listCart.observeForever(observer)
            cartViewModel.getCartList()
            val result = cartViewModel.listCart.value
            assertThat(result, not(nullValue()))
        } finally {
            cartViewModel.listCart.removeObserver(observer)
        }
    }

}