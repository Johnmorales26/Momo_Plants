package login

class Service {
    var authenticatedUser: Boolean = false
    val registredUsers = mutableListOf<User>()

    fun login(username: String, password: String) {
        val autenticacion = Authentication(username, password)
        if (autenticacion.authenticate(username, password)) {
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
        registredUsers.add(usuario)
    }

    fun existeUsuario(username: String): Boolean {
        return registredUsers.any { it.username == username }
    }
}