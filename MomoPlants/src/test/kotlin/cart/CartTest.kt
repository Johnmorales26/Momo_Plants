package cart

import dataAccess.PlantsDatabase
import entities.ItemEntity
import entities.PlantEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CartTest {
    @Test
    fun addItem_oneItemAdded_oneItemSize() {
        //Given
        val cart = Cart()
        val item = PlantsDatabase.getAllPlants()[0]
        cart.addItem(item)
        //When
        val result = cart.shoppingCart.size
        //Then
        assertEquals(1, result)
    }

    @Test
    fun findItem_existsItem_returnsItem() {
        //Given
        val cart = Cart()
        val plantName = "Galatea"
        cart.apply {
            addItem(PlantsDatabase.getAllPlants()[8])
            addItem(PlantsDatabase.getAllPlants()[5])
            addItem(PlantsDatabase.getAllPlants()[10])
            addItem(PlantsDatabase.getAllPlants()[15])
            addItem(PlantsDatabase.getAllPlants()[20])
            addItem(PlantsDatabase.getAllPlants()[25])
            addItem(PlantsDatabase.getAllPlants()[30])
        }
        //When
        val result = cart.findItemByName(plantName)
        //Then
        assertEquals(ItemEntity(PlantsDatabase.getAllPlants()[8], 1), ItemEntity(result!!, 1))
    }

    @Test
    fun findItem_noExistsItem_returnsNull() {
        //Given
        val cart = Cart()
        val plantName = "Parangaricutirimicuaro"
        cart.apply {
            addItem(PlantsDatabase.getAllPlants()[8])
            addItem(PlantsDatabase.getAllPlants()[5])
            addItem(PlantsDatabase.getAllPlants()[10])
            addItem(PlantsDatabase.getAllPlants()[15])
            addItem(PlantsDatabase.getAllPlants()[20])
            addItem(PlantsDatabase.getAllPlants()[25])
            addItem(PlantsDatabase.getAllPlants()[30])
        }
        //When
        val result = cart.findItemByName(plantName)
        //Then
        assertEquals(null, result)
    }

    @Test
    fun updateItem_itemExists_itemUpdated() {
        //Given
        val cart = Cart()
        cart.apply {
            addItem(PlantsDatabase.getAllPlants()[5])
            addItem(PlantsDatabase.getAllPlants()[0])
            addItem(PlantsDatabase.getAllPlants()[10])
            //When
            updateItem(PlantsDatabase.getAllPlants()[0], 1)
        }
        //Then
        assertEquals(ItemEntity(PlantsDatabase.getAllPlants()[0], 15), cart.shoppingCart[1])
        assertEquals(3, cart.shoppingCart.size)
    }
}