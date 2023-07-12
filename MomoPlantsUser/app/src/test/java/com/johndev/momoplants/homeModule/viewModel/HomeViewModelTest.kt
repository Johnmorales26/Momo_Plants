package com.johndev.momoplants.homeModule.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.homeModule.model.HomeRepository
import io.mockk.mockk
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HomeViewModelTest {

    private lateinit var homeRepository: HomeRepository
    private lateinit var homeViewModel: HomeViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        homeRepository = mockk()
        homeViewModel = HomeViewModel(homeRepository)
    }

    @Test
    fun addedPlant() {
        val observer = Observer<PlantEntity>{}
        try {
            val plantEntity = PlantEntity()
            homeViewModel.addPlant.observeForever(observer)
            homeViewModel.addedPlant(plantEntity)
            val result = homeViewModel.addPlant.value
            assertThat(result, not(nullValue()))
            assertThat(result, `is`(plantEntity))
        } finally {
            homeViewModel.addPlant.removeObserver(observer)
        }
    }

    @Test
    fun modifierPlant() {
        val observer = Observer<PlantEntity>{}
        try {
            val plantEntity = PlantEntity()
            homeViewModel.updatePlant.observeForever(observer)
            homeViewModel.modifierPlant(plantEntity)
            val result = homeViewModel.updatePlant.value
            assertThat(result, not(nullValue()))
            assertThat(result, `is`(plantEntity))
        } finally {
            homeViewModel.updatePlant.removeObserver(observer)
        }
    }

    @Test
    fun removedPlant() {
        val observer = Observer<PlantEntity>{}
        try {
            val plantEntity = PlantEntity()
            homeViewModel.removedPlant.observeForever(observer)
            homeViewModel.removedPlant(plantEntity)
            val result = homeViewModel.removedPlant.value
            assertThat(result, not(nullValue()))
            assertThat(result, `is`(plantEntity))
        } finally {
            homeViewModel.removedPlant.removeObserver(observer)
        }
    }

}