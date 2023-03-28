package login

import entities.UserEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ServiceTest() {
    @Test
    fun login_noUserRegistered_authenticatedUserFalse() {
        //Given
        val user = UserEntity("Pablo", "123456")
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
        val username = "Pablo"
        val password = "123456"
        val user = UserEntity(username, password)
        val service = Service()
        service.userRegister(user)

        //When
        service.login(UserEntity(username, password))
        val result = service.authenticatedUser

        //Then
        assertEquals(true, result)
    }

    @Test
    fun existeUsuario_userExists_true() {
        //Given
        val user = UserEntity("Pablo", "123456")
        val service = Service()
        service.userRegister(user)

        //When
        val result = service.userExist(user.username)

        //Then
        assertEquals(true, result)
    }

    @Test
    fun existeUsuario_noUserExists_false() {
        //Given
        val user = UserEntity("Pablo", "123456")
        val service = Service()

        //When
        val result = service.userExist(user.username)

        //Then
        assertEquals(false, result)
    }
}