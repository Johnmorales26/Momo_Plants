package com.johndev.momoplants.detailModule.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.johndev.momoplants.common.entities.PlantEntity
import com.johndev.momoplants.detailModule.model.DetailsRepository
import io.mockk.mockk
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailViewModelTest {

    private lateinit var detailRepository: DetailsRepository
    private lateinit var detailViewModel: DetailViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        detailRepository = mockk()
        detailViewModel = DetailViewModel(detailRepository)
    }

    @Test
    fun searchPlantRealtimeSuccess() {
        val observer = Observer<PlantEntity>{}
        try {
            val plantEntity = PlantEntity()
            detailViewModel.plantEntity.observeForever(observer)
            detailViewModel.searchPlantRealtimeSuccess(plantEntity)
            val result = detailViewModel.plantEntity.value
            MatcherAssert.assertThat(result, CoreMatchers.not(CoreMatchers.nullValue()))
        } finally {
            detailViewModel.plantEntity.removeObserver(observer)
        }
    }

}