package hbv601g.hugb2_team2.services

import android.content.Context
import hbv601g.hugb2_team2.entities.Establishment
import hbv601g.hugb2_team2.entities.EstablishmentWithDistance
import hbv601g.hugb2_team2.services.network.NetworkCallback

interface EstablishmentService {

    suspend fun getAllEstablishments(): List<Establishment>?
    suspend fun getEstablishmentById(id: Long): Establishment
    suspend fun getEstablishmentTypes(): List<String>
    suspend fun getEstablishmentByType(type: String): List<Establishment>
    suspend fun getEstablishmentByArea(area: String): List<Establishment>
    suspend fun createEstablishment(establishment: Establishment): Establishment?
    suspend fun editEstablishment(establishment: Establishment): Establishment?
    suspend fun deleteEstablishment(id: Long) : Boolean
    suspend fun getNearbyEstablishments(lat: Double, lon: Double, radius: Int): List<EstablishmentWithDistance>
    suspend fun ping(callback: NetworkCallback<String>)
    suspend fun getNearbyEstablishmentsByDrinkType(
        lat: Double,
        lon: Double,
        radius: Int,
        drinkTypeId: Long
    ): List<EstablishmentWithDistance>
}