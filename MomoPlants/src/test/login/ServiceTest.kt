package login

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ServiceTest() {
    @Test
    fun login_noUserRegistered_authenticatedUserFalse() {
        //Given
        val user = User("Pablo", "123456")

        //When
        val service = Service()
        service.login(user.username, user.password)
        val result = service.authenticatedUser

        //Then
        assertEquals(false, result)
    }

}