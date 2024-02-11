package hbv601g.hugb2_team2.entities

data class Beverage(
    val id: Long,
    val price: Int,
    val volume: Int,
    val establishment: Establishment,
    val drinkType: DrinkType
)
