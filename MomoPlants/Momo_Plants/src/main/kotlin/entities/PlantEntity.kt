package entities

data class PlantEntity(
    val id: Int,
    val name: String,
    val origin: String,
    val weather: String,
    val format: String,
    val price: Int,
    val stock: Int = 0
)
