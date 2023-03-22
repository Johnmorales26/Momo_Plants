package login

class Service {
    var authenticatedUser: Boolean = false
    val registeredUsers = mutableListOf<User>()

    fun login(user: User) {
        if (registeredUsers.contains(user)) {
            authenticatedUser = true
        } else {
            println("Credenciales incorrectas")
        }
    }

    fun logout() {
        authenticatedUser = false
    }

    fun registrarUsuario(username: String, password: String) {
        val usuario = User(username, password)
        registeredUsers.add(usuario)
    }

    fun existeUsuario(username: String): Boolean {
        return registeredUsers.any { it.username == username }
    }
}