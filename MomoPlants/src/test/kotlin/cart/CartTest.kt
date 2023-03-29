package cart

import dataAccess.PlantsDatabase
import entities.ItemEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CartTest {
    @Test
    fun addItem_oneItemAdded_oneItemSize() {
        //Given
        val cart = Cart()
        val item = ItemEntity(PlantsDatabase.getAllPlants()[0], 5)
        cart.addItem(item)
        //When
        val result = cart.shoppingCart.items.size
        //Then
        assertEquals(1, result)
    }

    @Test
    fun findItem_existsItem_returnsItem() {
        //Given
        val cart = Cart()
        val plantName = "Galatea"
        cart.apply {
            addItem(ItemEntity(PlantsDatabase.getAllPlants()[8], 5))
            addItem(ItemEntity(PlantsDatabase.getAllPlants()[5], 5))
            addItem(ItemEntity(PlantsDatabase.getAllPlants()[10], 5))
            addItem(ItemEntity(PlantsDatabase.getAllPlants()[15], 5))
            addItem(ItemEntity(PlantsDatabase.getAllPlants()[20], 5))
            addItem(ItemEntity(PlantsDatabase.getAllPlants()[25], 5))
            addItem(ItemEntity(PlantsDatabase.getAllPlants()[30], 5))
        }
        //When
        val result = cart.findItem(plantName)

        //Then
        assertEquals(ItemEntity(PlantsDatabase.getAllPlants()[8], 5), result)
    }

    @Test
    fun findItem_noExistsItem_returnsNull() {
        //Given
        val cart = Cart()
        val plantName = "Parangaricutirimicuaro"
        cart.apply {
            addItem(ItemEntity(PlantsDatabase.getAllPlants()[8], 5))
            addItem(ItemEntity(PlantsDatabase.getAllPlants()[5], 5))
            addItem(ItemEntity(PlantsDatabase.getAllPlants()[10], 5))
            addItem(ItemEntity(PlantsDatabase.getAllPlants()[15], 5))
            addItem(ItemEntity(PlantsDatabase.getAllPlants()[20], 5))
            addItem(ItemEntity(PlantsDatabase.getAllPlants()[25], 5))
            addItem(ItemEntity(PlantsDatabase.getAllPlants()[30], 5))
        }
        //When
        val result = cart.findItem(plantName)
        //Then
        assertEquals(null, result)
    }

    @Test
    fun updateItem_itemExists_itemUpdated() {
        //Given
        val cart = Cart()
        cart.apply {
            addItem(ItemEntity(PlantsDatabase.getAllPlants()[5], 10))
            addItem(ItemEntity(PlantsDatabase.getAllPlants()[0], 5))
            addItem(ItemEntity(PlantsDatabase.getAllPlants()[10], 15))
            //When
            updateItem(ItemEntity(PlantsDatabase.getAllPlants()[0], 15), 1)
        }
        //Then
        assertEquals(ItemEntity(PlantsDatabase.getAllPlants()[0], 15), cart.shoppingCart.items[1])
        assertEquals(3, cart.shoppingCart.items.size)
    }
}