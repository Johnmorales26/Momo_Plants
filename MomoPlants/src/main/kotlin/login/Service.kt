package login

class Service {
    var authenticatedUser: Boolean = false
    val registeredUsers = mutableListOf<User>()

    fun login(username: String, password: String) {
        /*val autenticacion = Authentication(username, password)
        if (autenticacion.authenticate(username, password)) {
            authenticatedUser = true
        } else {
            println("Credenciales incorrectas")
        }*/
        val tempUser = User(username, password)

        if (registeredUsers.contains(tempUser)) {
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