package hbv601g.hugb2_team2.services.implementation

import android.util.Log
import com.google.gson.Gson
import hbv601g.hugb2_team2.entities.Establishment
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

    fun calculateLatLon(address : String): List<Double> {
        // TODO: implement google maps api to get lat and lon from address
        val latLon = mutableListOf<Double>()
        val lat = 64.140813
        val lon = -21.94607
        latLon.add(lat)
        latLon.add(lon)
        return latLon
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