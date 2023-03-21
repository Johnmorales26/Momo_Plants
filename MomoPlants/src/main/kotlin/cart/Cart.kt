package cart
import java.util.UUID
data class Plant(val name: String, val id:String)

class Cart {

    fun showMenu(){
        val menu = listOf(
            Pair(1,"Agregar planta al carrito"),
            Pair(2,"Buscar planta"),
            Pair(3,"Eliminar planta"),
            Pair(4,"Mostrar plantas"),
            Pair(5,"Salir")
        )
        return menu.forEach { option-> println("${option.first}.- ${option.second}") }
    }
    fun addPlant(cart: MutableList<Plant>) {
        print("Ingresa el nombre de la planta: ")
        val nameP = readlnOrNull()?.trim()

        if (nameP != null) {
            print("Cantidad de plantas que deseas agregar: ")
            val quantity = readLine()?.toIntOrNull()
            if(quantity!=null && quantity>0){
                for (i in 1..quantity){
                    val plant = Plant(nameP,UUID.randomUUID().toString())
                    cart.add(plant)
                }
                println("Se agregaron $quantity Planta(s) de $nameP al carrito.")
            }else {
                println("Error: cantidad invalida.")
            }
        }else{
            println("Error: Datos invalidos")
        }
    }
    fun findPlant(cart: MutableList<Plant>) {
        print("Ingresa el nombre de la planta a buscar: ")
        val nameP = readLine()?.trim()

        if (nameP != null) {
            val plant = cart.find { plant-> plant.name == nameP }
            if (plant != null) {
                println("La planta $nameP se encuentra en el carrito.")
            } else {
                println("La planta $nameP no se encuentra en el carrito.")
            }
        } else {
            println("Error: datos ingresados inválidos.")
        }
    }
    fun removePlant(cart: MutableList<Plant>) {
        print("Ingresa el índice de planta que deseas eliminar: ")
        val indexP = readLine()?.trim()?.toIntOrNull()

        if(indexP!= null && indexP < cart.size){
            val plantToRemove = cart[indexP]

            println("¿Seguro que desea eliminar la planta $plantToRemove del carrito ?")
            println("1.- Sí, eliminar")
            println("2.- Cancelar")
            val resp = readLine()?.trim()?.toIntOrNull()
            if (resp == 1){
                cart.removeAt(indexP)
                println("$indexP se ha eliminado del carrito $plantToRemove")
            }else{
                println("Presiona enter para continuar")
            }

        }else{
            println("no existe el indice")
        }
    }
    fun showPlants(cart: List<Plant>) {
        if (cart.isEmpty()) {
            println("El carrito está vacío.")
        } else {
            println("Plantas en el carrito:")
            cart.forEachIndexed{
                    index,elemento->println("$index ${UUID.randomUUID().toString().substring(0,8)} ${elemento.name} ")
            }
            println("Total ${cart.size}")

        }
    }
}