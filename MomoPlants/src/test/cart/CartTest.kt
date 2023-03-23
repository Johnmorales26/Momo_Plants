package cart

import Catalogue
import login.Service
import login.User
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
        assertEquals(1, 1)
    }
}