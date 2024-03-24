package hbv601g.hugb2_team2.services.implementation

import android.util.Log
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import hbv601g.hugb2_team2.entities.Beverage
import hbv601g.hugb2_team2.entities.DrinkType
import hbv601g.hugb2_team2.services.DrinkTypeService
import hbv601g.hugb2_team2.services.network.NetworkingServiceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject


class DrinkTypeServiceImpl : DrinkTypeService {

    private val baseUrl = "/drinkTypes"
    private var networkingService = NetworkingServiceProvider.getNetworkingService()

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

    override suspend fun getDrinkTypeById(id: Long): DrinkType {
        return withContext(Dispatchers.IO) {
            try {
                val drinkTypeByIdUrl = "$baseUrl/${id}"
                val jsonResponse = networkingService.getRequest(drinkTypeByIdUrl)
                Log.d("getDrinkType", "Response: $jsonResponse")
                val gson = Gson()
                val listType = object : TypeToken<DrinkType>() {}.type
                gson.fromJson<DrinkType>(jsonResponse, listType)
                    ?: throw IllegalStateException("Failed to parse drink type from JSON")
            } catch (e: Exception) {
                Log.e("getDrinkType", "Error fetching drink type by id", e)
                throw e
            }
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

    override suspend fun createDrinkType(drinkType: DrinkType): DrinkType? {
        val reqURL = "$baseUrl/create"
        return try {
            val drinkTypeJson = Gson().toJson(drinkType)
            val response = networkingService.postRequest(reqURL, drinkTypeJson)
            Log.d("drinkTypeJsonServiceImpl", "Response: $response")
            // convert response to establishment
            Gson().fromJson(response.toString(), DrinkType::class.java)
        } catch (e: Exception) {
            Log.d("DrinkTypeServiceImpl", "Exception: $e")
            null
        }
    }

    override suspend fun editDrinkType(drinkType: DrinkType): DrinkType? {
        val reqURL = "$baseUrl/${drinkType.id}"
        return try {
            val estJson = Gson().toJson(drinkType)
            val response = networkingService.putRequest(reqURL, estJson)
            Gson().fromJson(response.toString(), DrinkType::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun deleteDrinkType(id: Long): Boolean {
        val reqURL = "$baseUrl/${id}/delete"
        return try {
            val response = networkingService.deleteRequest(reqURL)
            true
        } catch (e: Exception) {
            Log.d("DrinkTYPEServiceImpl", "Exception: $e")
            false
        }
    }

    override suspend fun getDrinkTypeByName(name: String): DrinkType? {
        val reqURL = "$baseUrl/byName"
        return try {
            val drinkTypeNameJson = Gson().toJson(name)
            val response = networkingService.postRequest(reqURL, drinkTypeNameJson)
            Log.d("drinkTypeJsonServiceImpl", "Response: $response")
            // convert response to establishment
            Gson().fromJson(response.toString(), DrinkType::class.java)
        } catch (e: Exception) {
            Log.d("DrinkTypeServiceImpl", "Exception: $e")
            null
        }
    }

}