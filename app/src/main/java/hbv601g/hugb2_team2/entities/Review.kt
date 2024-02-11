package hbv601g.hugb2_team2.entities

import java.time.LocalDate

data class Review(
    val id: Long,
    val establishment: Establishment,
    val user: User,
    val rating: Double,
    val comment: String,
    val date: LocalDate
)
