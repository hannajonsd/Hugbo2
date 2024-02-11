package hbv601g.hugb2_team2.entities

data class Establishment(

    // add mapping for GSON to work
    val id: Long,
    val name: String,
    val type: String,
    val address: String,
    // area attribute = location attribute (which is an enum) in spring
    val location: Location,
    val priceRating: Double,
    val selectionRating: Double,
    val userRating: Double,
    val openingHours: String,
    val lat: Double,
    val lon: Double,
) {
    enum class Location(val value: Int) {
        Miðbærinn(0),
        Höfuðborgarsvæðið(1),
        Norðurland(2),
        Suðurland(3),
        Suðvesturland(4),
        Austurland(5),
        Vesturland(6),
        Vestfirðir(7)
    }
}
