package login

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ServiceTest() {
    @Test
    fun login_noUserRegistered_authenticatedUserFalse() {
        //Given
        val user = User("Pablo", "123456")
        val service = Service()

        //When
        service.login(user)
        val result = service.authenticatedUser

        //Then
        assertEquals(false, result)
    }

    @Test
    fun login_userRegistered_authenticatedUserTrue() {
        //Given
        val user = User("Pablo", "123456")
        val service = Service()
        service.registrarUsuario(user)

        //When
        service.login(user)
        val result = service.authenticatedUser

        //Then
        assertEquals(true, result)
    }

}