package hbv601g.hugb2_team2.services.implementation

import android.util.Log
import com.google.gson.Gson
import hbv601g.hugb2_team2.entities.Beverage
import hbv601g.hugb2_team2.entities.Establishment
import hbv601g.hugb2_team2.entities.EstablishmentWithDistance
import hbv601g.hugb2_team2.services.EstablishmentService
import hbv601g.hugb2_team2.services.network.NetworkCallback
import hbv601g.hugb2_team2.services.network.NetworkingServiceProvider

class EstablishmentServiceImpl : EstablishmentService {

    private var networkingService = NetworkingServiceProvider.getNetworkingService()
    private var baseUrl = "/establishments"
    private var mockEstablishments = mutableListOf<Establishment>()

    override suspend fun getAllEstablishments(): List<Establishment>? {
        val reqURL = "$baseUrl/"
        try {
            val response = networkingService.getRequest(reqURL)
            Log.d("EstablishmentServiceImpl", "Response: $response")
            // response is JSON element, convert to list of establishments
            val gson = Gson()
            return gson.fromJson(response, Array<Establishment>::class.java).toList()
        } catch (e: Exception) {
            Log.d("EstablishmentServiceImpl", "Exception: $e")
            return null
        }
    }

    override suspend fun getEstablishmentById(id: Long): Establishment {
        val reqURL = "$baseUrl/$id"
        try {
            val response = networkingService.getRequest(reqURL)
            Log.d("EstablishmentServiceImpl", "Response: $response")
            val gson = Gson()
            return gson.fromJson(response, Establishment::class.java)
                ?: throw Exception("Failed to parse establishment from response")
        } catch (e: Exception) {
            Log.e("EstablishmentServiceImpl", "Error fetching establishment by ID", e)
            throw e
        }
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

    override suspend fun createEstablishment(establishment: Establishment): Establishment? {
        val reqURL = "$baseUrl/create"
        return try {
            val estJson = Gson().toJson(establishment)
            val response = networkingService.postRequest(reqURL, estJson)
            Log.d("EstablishmentServiceImpl", "Response: $response")
            // convert response to establishment
            Gson().fromJson(response.toString(), Establishment::class.java)
        } catch (e: Exception) {
            Log.d("EstablishmentServiceImpl", "Exception: $e")
            null
        }
    }

    override suspend fun editEstablishment(establishment: Establishment): Establishment {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEstablishment(id: Long): Boolean {
        val reqURL = "$baseUrl/${id}/delete"
        return try {
            val response = networkingService.deleteRequest(reqURL)
            true
        } catch (e: Exception) {
            Log.d("EstablishmentServiceImpl", "Exception: $e")
            false
        }
    }

    /**
     * Calls the networking service to get nearby establishments
     * Endpoint: /api/establishments/nearby/{latidude}/{longitude}/{radius}
     * Radius is in kilometers
     */
    override suspend fun getNearbyEstablishments(
        lat: Double,
        lon: Double,
        radius: Int
    ): List<EstablishmentWithDistance> {
        val reqUrl = "$baseUrl/nearby/$lat/$lon/$radius"
        return try {
            Log.d("EstablishmentServiceImpl", "Request URL: $reqUrl")
            val response = networkingService.getRequest(reqUrl)
            Log.d("EstablishmentServiceImpl", "Response: $response")
            val gson = Gson()
            gson.fromJson(response, Array<EstablishmentWithDistance>::class.java).toList()
        } catch (e: Exception) {
            Log.d("EstablishmentServiceImpl", "Exception: $e")
            emptyList()
        }
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

    fun createMockEstablishmentsIfNotExist()
    {
        if (mockEstablishments.isEmpty()) {
            mockEstablishments.add(Establishment(1, "Kaffi Mokka", "Cafe", "Skólavörðustígur 3A", Establishment.Location.Miðbærinn, 0.0, 0.0, 0.0, "08:00-18:00", 64.140813, -21.94607))
            mockEstablishments.add(Establishment(2, "Kaffi Loki", "Cafe", "Lokastígur 28", Establishment.Location.Miðbærinn, 0.0, 0.0, 0.0, "08:00-18:00", 64.140813, -21.94607))
            mockEstablishments.add(Establishment(3, "Kaffi Reykjavík", "Cafe", "Bankastræti 5", Establishment.Location.Miðbærinn, 0.0, 0.0, 0.0, "08:00-18:00", 64.140813, -21.94607))
            mockEstablishments.add(Establishment(4, "Kaffi Paris", "Cafe", "Austurstræti 14", Establishment.Location.Miðbærinn, 0.0, 0.0, 0.0, "08:00-18:00", 64.140813, -21.94607))
            mockEstablishments.add(Establishment(5, "Kaffi Austurland", "Cafe", "Austurland 121", Establishment.Location.Austurland, 0.0, 0.0, 0.0, "08:00-18:00", 64.140813, -21.94607))
            mockEstablishments.add(Establishment(6, "Kaffi Vesturland", "Cafe", "Vesturland 121", Establishment.Location.Vesturland, 0.0, 0.0, 0.0, "08:00-18:00", 64.140813, -21.94607))
            mockEstablishments.add(Establishment(7, "Kaffi Norðurland", "Cafe", "Norðurland 121", Establishment.Location.Norðurland, 0.0, 0.0, 0.0, "08:00-18:00", 64.140813, -21.94607))
            mockEstablishments.add(Establishment(8, "Kaffi Suðurland", "Cafe", "Suðurland 121", Establishment.Location.Suðurland, 0.0, 0.0, 0.0, "08:00-18:00", 64.140813, -21.94607))
        }
    }

}