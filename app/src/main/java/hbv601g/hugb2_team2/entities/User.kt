package hbv601g.hugb2_team2.entities

data class User(
    val id: Long? = null,
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val isAdmin: Boolean
)
