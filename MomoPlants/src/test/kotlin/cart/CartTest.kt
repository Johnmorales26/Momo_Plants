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
        }

        //When
        cart.updateItem(PlantsDatabase.getAllPlants()[0], 1)

        //Then
        assertEquals(ItemEntity(PlantsDatabase.getAllPlants()[0], 15), cart.shoppingCart[1])
        assertEquals(3, cart.shoppingCart.size)
    }

    @Test
    fun findItemByID_itemExists_returnsItem() {
        //Given
        val cart = Cart()
        cart.getPlantsByFlow()

        //When
        val item = cart.findItemByID(15)

        //Then
        assertEquals(PlantsDatabase.getAllPlants()[14], item)
    }

    @Test
    fun increaseQuantity_incrementByFive_quantityIncreasedFive() {
        //Given
        val cart = Cart()

        val plant1 = PlantsDatabase.getAllPlants()[5]
        val plant2 = PlantsDatabase.getAllPlants()[0]
        val plant3 = PlantsDatabase.getAllPlants()[10]

        plant1.quantity = 1
        plant2.quantity = 2
        plant3.quantity = 3

        cart.apply {
            addItem(plant1)
            addItem(plant2)
            addItem(plant3)
        }

        //When
        cart.increaseQuantity(PlantsDatabase.getAllPlants()[0], 5)

        //Then
        val plantExpected = PlantsDatabase.getAllPlants()[0]
        plantExpected.quantity = 7
        assertEquals(plantExpected, cart.shoppingCart[1])
    }

    @Test
    fun decreaseQuantity_decreaseByOne_quantityDecreasedOne() {
        //Given
        val cart = Cart()

        val plant1 = PlantsDatabase.getAllPlants()[5]
        val plant2 = PlantsDatabase.getAllPlants()[0]
        val plant3 = PlantsDatabase.getAllPlants()[10]

        plant1.quantity = 1
        plant2.quantity = 2
        plant3.quantity = 3

        cart.apply {
            addItem(plant1)
            addItem(plant2)
            addItem(plant3)
        }

        //When
        cart.decreaseQuantity(plant2, 1)

        //Then
        val plantExpected = PlantsDatabase.getAllPlants()[0]
        plantExpected.quantity = 1
        assertEquals(plantExpected, cart.shoppingCart[1])
    }

    @Test
    fun calcTotal_addFiveItems_getCostFiveItems() {
        //Given
        val cart = Cart()

        val plant1 = PlantsDatabase.getAllPlants()[5]
        val plant2 = PlantsDatabase.getAllPlants()[0]
        val plant3 = PlantsDatabase.getAllPlants()[10]

        plant1.quantity = 1
        plant2.quantity = 2
        plant3.quantity = 3

        cart.apply {
            addItem(plant1)
            addItem(plant2)
            addItem(plant3)
        }

        //When
        val result = cart.calcTotal()

        //Then
        val cost1 = plant1.quantity * plant1.price
        val cost2 = plant2.quantity * plant2.price
        val cost3 = plant3.quantity * plant3.price
        val totalCost = cost1 + cost2 + cost3
        assertEquals(totalCost.toDouble(), result)
    }
}