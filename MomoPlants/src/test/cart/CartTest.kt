package cart

import Catalogue
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CartTest {
    @Test
    fun addItem_oneItemAdded_oneItemSize() {
        //Given
        val cart = Cart()
        val item = Item(Catalogue.plants[0], 5)
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

        cart.addItem(Item(Catalogue.plants[8], 5))
        cart.addItem(Item(Catalogue.plants[5], 5))
        cart.addItem(Item(Catalogue.plants[10], 5))
        cart.addItem(Item(Catalogue.plants[15], 5))
        cart.addItem(Item(Catalogue.plants[20], 5))
        cart.addItem(Item(Catalogue.plants[25], 5))
        cart.addItem(Item(Catalogue.plants[30], 5))

        //When
        val result = cart.findItem(plantName)

        //Then
        assertEquals(Item(Catalogue.plants[8], 5), result)
    }

    @Test
    fun findItem_noExistsItem_returnsNull() {
        //Given
        val cart = Cart()
        val plantName = "Parangaricutirimicuaro"

        cart.addItem(Item(Catalogue.plants[8], 5))
        cart.addItem(Item(Catalogue.plants[5], 5))
        cart.addItem(Item(Catalogue.plants[10], 5))
        cart.addItem(Item(Catalogue.plants[15], 5))
        cart.addItem(Item(Catalogue.plants[20], 5))
        cart.addItem(Item(Catalogue.plants[25], 5))
        cart.addItem(Item(Catalogue.plants[30], 5))

        //When
        val result = cart.findItem(plantName)

        //Then
        assertEquals(null, result)
    }
}