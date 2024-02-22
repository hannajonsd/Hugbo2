package hbv601g.hugb2_team2.services.implementation

import android.content.Context
import android.util.Log
import hbv601g.hugb2_team2.entities.Establishment
import hbv601g.hugb2_team2.services.EstablishmentService
import hbv601g.hugb2_team2.services.network.NetworkCallback
import hbv601g.hugb2_team2.services.network.NetworkingServiceProvider

class EstablishmentServiceImpl : EstablishmentService {

    private var networkingService = NetworkingServiceProvider.getNetworkingService()


    override suspend fun getAllEstablishments(): List<Establishment> {
        TODO("Not yet implemented")
    }

    override suspend fun getEstablishmentById(id: Long): Establishment {
        TODO("Not yet implemented")
    }

    override suspend fun getEstablishmentTypes(): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getEstablishmentByType(type: String): List<Establishment> {
        TODO("Not yet implemented")
    }

    override suspend fun getEstablishmentByArea(area: String): List<Establishment> {
        TODO("Not yet implemented")
    }

    override suspend fun createEstablishment(establishment: Establishment): Establishment {
        TODO("Not yet implemented")
    }

    override suspend fun updateEstablishment(establishment: Establishment): Establishment {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEstablishment(establishment: Establishment): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getNearbyEstablishments(
        lat: Double,
        lon: Double,
        radius: Int
    ): List<Establishment> {
        TODO("Not yet implemented")
    }

    override suspend fun ping(callback: NetworkCallback<String>) {
        val reqURL = "/ping"
        try {
            val response = networkingService.getRequest(reqURL)
            callback.onSuccess(response.toString())
        } catch (e: Exception) {
            Log.d("EstablishmentServiceImpl", "Exception: $e")
            callback.onFailure(e.message ?: "Unknown error")
        }
    }

}