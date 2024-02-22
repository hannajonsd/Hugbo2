package hbv601g.hugb2_team2.services

import android.content.Context
import hbv601g.hugb2_team2.entities.Establishment
import hbv601g.hugb2_team2.services.network.NetworkCallback

interface EstablishmentService {

    suspend fun getAllEstablishments(): List<Establishment>
    suspend fun getEstablishmentById(id: Long): Establishment
    suspend fun getEstablishmentTypes(): List<String>
    suspend fun getEstablishmentByType(type: String): List<Establishment>
    suspend fun getEstablishmentByArea(area: String): List<Establishment>
    suspend fun createEstablishment(establishment: Establishment): Establishment
    suspend fun updateEstablishment(establishment: Establishment): Establishment
    suspend fun deleteEstablishment(establishment: Establishment) : Boolean
    suspend fun getNearbyEstablishments(lat: Double, lon: Double, radius: Int): List<Establishment>
    suspend fun ping(callback: NetworkCallback<String>)
}