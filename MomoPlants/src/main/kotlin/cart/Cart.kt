package cart

import UserInterfaceUtils.Companion.showPlantsCatalogue
class Cart {
    internal val shoppingCart = mutableListOf<Item>()
    internal val Orders=mutableListOf<Item>()
    private val plants = Catalogue.plants

    fun showMenu() {
        val menu = listOf(
            Pair(1,"Agregar planta al carrito"),
            Pair(2,"Buscar planta"),
            Pair(3,"Eliminar planta"),
            Pair(4,"Mostrar plantas del carrito"),
            Pair(5,"Mostrar plantas del catálogo"),
            Pair(6,"Finalizar Pedido"),
            Pair(7,"Ver pedidos anteriores"),
            Pair(8,"Salir")

        )
        return menu.forEach { option-> println("${option.first}.- ${option.second}") }
    }
    fun askPlantToAdd() {
        print("Ingresa el id de la planta: ")
        val id: Int? = readlnOrNull()?.trim()?.toIntOrNull()

        if (id != null && id in 0..(plants.size - 1)) {
            print("Cantidad de plantas que deseas agregar: ")
            val quantity = readLine()?.toIntOrNull()
            if (quantity != null && quantity > 0) {
                val plant = plants[id]
                addItem(Item(plant, quantity))
                println("Se agregaron $quantity planta(s) de ${plant.name} al carrito.")
            } else {
                println("Error: cantidad invalida.")
            }
        } else {
            println("Error: Datos invalidos")
        }
    }

    internal fun addItem(item: Item) {
        shoppingCart.add(item)
    }
    fun askPlantToFind() {
        print("Ingresa el nombre de la planta a buscar: ")
        val nameP = readLine()?.trim()

        if (nameP != null) {
            val item = findItem(nameP)

            if (item != null) {
                println("La planta ${item.plant.name} se encuentra en el carrito.")
            } else {
                println("La planta $nameP no se encuentra en el carrito.")
            }
        } else {
            println("Error: datos ingresados inválidos.")
        }
    }

    internal fun findItem(plantName: String): Item? {
        val item = shoppingCart.find { item -> item.plant.name.lowercase().contains(plantName.lowercase()) }

        return item
    }
    fun askPlantToRemove() {
        print("Ingresa el id de planta que deseas eliminar: ")
        val indexP = readLine()?.trim()?.toIntOrNull()

        if (indexP != null && indexP in 0..(shoppingCart.size - 1)){
            val itemToRemove = shoppingCart[indexP]

            println("¿Seguro que desea eliminar la planta ${itemToRemove.plant.name} del carrito ?")
            println("1.- Sí, eliminar")
            println("2.- Cancelar")
            val resp = readLine()?.trim()?.toIntOrNull()
            if (resp == 1){
                removeItem(indexP)
                println("${itemToRemove.plant.name} se ha eliminado del carrito")
            } else {
                println("Presiona enter para continuar")
            }
        }else{
            println("No existe el id")
        }
    }

    internal fun removeItem(id: Int) {
        shoppingCart.removeAt(id)
    }
    fun showPlantsCart() {
        if (shoppingCart.isEmpty()) {
            println("El carrito está vacío.")
        } else {
            println("Plantas en el carrito:")
            shoppingCart.forEachIndexed {
                    index, elemento -> println("Id: ${index}, Nombre: ${elemento.plant.name}, Cantidad: ${elemento.quantity}")
            }
            println("Total ${shoppingCart.size}")

            menuShow()
        }
    }

    fun showPlantsCatalogue() {
        UserInterfaceUtils.showPlantsCatalogue()
    }

    fun menuShow(){
        var opcion: Int
        do {
            println()
            println("1. Edita una planta del carro")
            println("2. Eliminar una planta del carro")
            //println("3. Aplicar cupón")
            println("3. Salir\n")
            print("Selecciona una opción: ")
            println ()
            opcion = readln().toInt()


            when (opcion) {
                1 -> updatePlant()
                2 -> cart.askPlantToRemove()
                //3 -> println("3!")
                3 -> println("Has regresado al menu principal")
                else -> println("Opción inválida. Inténtalo de nuevo.")
            }
        } while (opcion != 3)
    }


    fun updatePlant(){
        print("Ingresa el id de la planta a cambiar cantidad: ")
        val indexP = readLine()?.trim()?.toIntOrNull()

        if (indexP != null && indexP in 0..(shoppingCart.size - 1)){
            val itemToRemove = shoppingCart[indexP]


            print("Cantidad nueva de plantas que deseas agregar: ")
            val quantity = readLine()?.toIntOrNull()
            if (quantity != null && quantity > 0) {
                val plant = plants[indexP]
                println(plant)
                addItem(Item(plant, quantity))
                println("Se agregaron $quantity planta(s) de ${plant.name} al carrito.")
            } else {
                println("Error: cantidad invalida.")
            }

                //se elimina
                removeItem(indexP)

        }else{
            println("No existe el id")
        }
    }
    fun showOldOrders(){
        if (Orders.isEmpty()) {
            println("No hay ordenes antiguas.")
        } else {
            println("Plantas en el carrito:")
            Orders.forEachIndexed {
                index, elemento -> println("Id: ${index}, Nombre: ${elemento.plant.name}, Cantidad: ${elemento.quantity}")
            }


            menuShow()
        }
    }
    fun checkOut(){
        val total=shoppingCart.sumOf { shoppingCart.size }
        println("Su pedido actualmente tiene {$total} items")
        println("¿Desea finalizar su pedido?")
        println("1.Finalizar Pedido")
        println("2. Volver al menú Principal")
        println("Ingresa una opción")
        val option=readLine()?.toIntOrNull()
        when (option) {
            1 -> saveOrder()
            2 -> showMenu()

            else -> println("Opción inválida. Inténtalo de nuevo.")
        }


    }
    internal fun saveOrder(){
        shoppingCart.forEach{ Orders.add(it) }
        shoppingCart.clear()
        menuShow()

    }






}