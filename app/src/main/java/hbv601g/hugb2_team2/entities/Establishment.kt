package hbv601g.hugb2_team2.entities

data class Establishment(

    // add mapping for GSON to work
    val id: Long,
    val name: String,
    val type: String,
    val address: String,
    // area attribute = location attribute (which is an enum) in spring
    val area: String,
    val priceRating: Double,
    val selectionRating: Double,
    val userRating: Double,
    val openingHours: String

)
