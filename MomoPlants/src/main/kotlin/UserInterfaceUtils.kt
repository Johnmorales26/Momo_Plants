class UserInterfaceUtils {
    companion object {
        public fun cleanScreen() {
            repeat(100) {
                print("\n")
            }
        }

        public fun sleep() {
            Thread.sleep(2000)
        }

        public fun showPlantsCatalogue() {
            Catalogue.plants.forEachIndexed { index, plant ->
                println("Id: $index, ${plant.stock} x ${plant.name} - Precio unitario: ${plant.price}")
            }
        }
    }
}