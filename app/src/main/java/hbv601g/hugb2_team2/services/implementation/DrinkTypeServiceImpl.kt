package hbv601g.hugb2_team2.services.implementation

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import hbv601g.hugb2_team2.entities.DrinkType
import hbv601g.hugb2_team2.entities.Establishment
import hbv601g.hugb2_team2.services.DrinkTypeService
import hbv601g.hugb2_team2.services.network.NetworkingServiceProvider

class DrinkTypeServiceImpl : DrinkTypeService {

    private var networkingService = NetworkingServiceProvider.getNetworkingService()
    private var baseUrl = "/drinkTypes"

    override suspend fun getAllDrinkTypes(): List<DrinkType>? {
        val reqURL = "$baseUrl/"
        try {
            val response = networkingService.getRequest(reqURL)
            Log.d("DrinkTypeServiceImpl", "Response: $response")
            // response is JSON element, convert to list of establishments
            val gson = Gson()
            return gson.fromJson(response, Array<DrinkType>::class.java).toList()
        } catch (e: Exception) {
            Log.d("DrinkTypeServiceImpl", "Exception: $e")

            return null
        }

    }

    override suspend fun getDrinkTypeById(id: Long): DrinkType? {
        val reqURL = "$baseUrl/$id"
        try {
            val response = networkingService.getRequest(reqURL)
            Log.d("DrinkTypeServiceImpl", "Response: $response")
            // response is JSON element, convert to list of establishments
            val gson = Gson()
            return gson.fromJson(response, DrinkType::class.java)
        } catch (e: Exception) {
            Log.d("DrinkTypeServiceImpl", "Exception: $e")

            return null
        }
    }

    override suspend fun getDrinkTypesByType(type: String): List<DrinkType> {
        TODO("Not yet implemented")
    }

    override suspend fun getDrinkTypesByTypeAndSubtype(
        type: String,
        subtype: String
    ): List<DrinkType> {
        TODO("Not yet implemented")
    }

    override suspend fun createDrinkType(drinkType: DrinkType): DrinkType {
        TODO("Not yet implemented")
    }

    override suspend fun editDrinkType(drinkType: DrinkType): DrinkType {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDrinkType(drinkType: DrinkType): Boolean {
        TODO("Not yet implemented")
    }

}