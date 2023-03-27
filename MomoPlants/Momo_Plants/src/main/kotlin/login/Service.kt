package login

import entities.UserEntity

class Service {
    var authenticatedUser: Boolean = false
    private val registeredUsers = mutableListOf<UserEntity>()

    fun login(user: UserEntity) {
        if (registeredUsers.contains(user)) {
            authenticatedUser = true
        } else {
            println("Credenciales incorrectas")
        }
    }

    fun logout() {
        authenticatedUser = false
    }

    fun userRegister(user: UserEntity) {
        registeredUsers.add(user)
    }

    fun userExist(username: String): Boolean = registeredUsers.any { it.username == username }
}