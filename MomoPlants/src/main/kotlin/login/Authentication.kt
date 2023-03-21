package login

class Authentication(private val username: String, private val password: String) {
    
    fun authenticate(username: String, password: String): Boolean {
        return this.username == username && this.password == password
    }
    
}