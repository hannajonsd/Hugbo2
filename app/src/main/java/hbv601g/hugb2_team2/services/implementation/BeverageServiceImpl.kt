package hbv601g.hugb2_team2.services.implementation

import hbv601g.hugb2_team2.entities.Beverage
import hbv601g.hugb2_team2.entities.DrinkType
import hbv601g.hugb2_team2.entities.Establishment
import hbv601g.hugb2_team2.services.BeverageService
import hbv601g.hugb2_team2.services.network.NetworkingServiceProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.withContext
import android.util.Log
import kotlinx.coroutines.Dispatchers


class BeverageServiceImpl : BeverageService {
    private var networkingService = NetworkingServiceProvider.getNetworkingService()
    private var baseUrl = "/drinks"
    override suspend fun createBeverage(beverage: Beverage): Beverage? {
        val reqURL = "$baseUrl/create"
        return try {
            val estJson = Gson().toJson(beverage)
            val response = networkingService.postRequest(reqURL, estJson)
            Gson().fromJson(response.toString(), Beverage::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun editBeverage(beverage: Beverage): Beverage? {
        val reqURL = "$baseUrl/${beverage.id}"
        return try {
            val estJson = Gson().toJson(beverage)
            val response = networkingService.putRequest(reqURL, estJson)
            Gson().fromJson(response.toString(), Beverage::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun deleteBeverage(beverage: Beverage): Beverage? {
        val reqURL = "$baseUrl/${beverage.id}"
        return try {
            val response = networkingService.deleteRequest(reqURL)
            Gson().fromJson(response.toString(), Beverage::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getMenu(establishment: Establishment): List<Beverage> {
        return withContext(Dispatchers.IO) {
            try {
                val menuUrl = "$baseUrl/menu/${establishment.id}"
                val jsonResponse = networkingService.getRequest(menuUrl)
                Log.d("getMenu", "Response: $jsonResponse")
                val gson = Gson()
                val listType = object : TypeToken<List<Beverage>>() {}.type
                gson.fromJson<List<Beverage>>(jsonResponse, listType)
            } catch (e: Exception) {
                Log.e("getMenu", "Error fetching menu for establishment ${establishment.name}", e)
                emptyList<Beverage>()
            }
        }
    }

    override suspend fun getAllBeveragesByDrinkType(drinkType: DrinkType): List<Beverage> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllBeveragesByDrinkTypeId(drinkTypeId: Long): List<Beverage>? {
        val reqURL = "$baseUrl/types/$drinkTypeId"
        return try {
            val response = networkingService.getRequest(reqURL)
            Log.d("BeverageServiceImpl", "Response: $response")
            // response is JSON element, convert to list of establishments
            val gson = Gson()
            gson.fromJson(response, Array<Beverage>::class.java).toList()
        } catch (e: Exception) {
            Log.d("DrinkTypeServiceImpl", "Exception: $e")
            null
        }

    }

    override suspend fun getAllBeveragesByDrinkTypeSortByPriceDesc(drinkType: DrinkType): List<Beverage> {
        val reqURL = "$baseUrl/types/${drinkType.id}/price/desc"
        return try {
            val response = networkingService.getRequest(reqURL)
            Log.d("BeverageServiceImpl", "Response: $response")
            val gson = Gson()
            gson.fromJson(response, Array<Beverage>::class.java).toList()
        } catch (e: Exception) {
            Log.e("BeverageServiceImpl", "Exception: $e")
            emptyList()
        }
    }

    override suspend fun getAllBeveragesByDrinkTypeSortByPriceAsc(drinkType: DrinkType): List<Beverage> {
        val reqURL = "$baseUrl/types/${drinkType.id}/price/asc"
        return try {
            val response = networkingService.getRequest(reqURL)
            Log.d("BeverageServiceImpl", "Response: $response")
            val gson = Gson()
            gson.fromJson(response, Array<Beverage>::class.java).toList()
        } catch (e: Exception) {
            Log.e("BeverageServiceImpl", "Exception: $e")
            emptyList()
        }
    }


    override suspend fun getBeverageById(id: Long): Beverage {
        return withContext(Dispatchers.IO) {
            try {
                val jsonResponse = networkingService.getRequest("$baseUrl/${id}")
                val gson = Gson()
                val listType = object : TypeToken<Beverage>() {}.type
                gson.fromJson<Beverage>(jsonResponse, listType)
                    ?: throw IllegalStateException("Failed to beverage from JSON")
            } catch (e: Exception) {
                Log.e("DrinkTypeServiceImpl", "Error fetching beverage by id", e)
                throw e
            }
        }
    }

    override suspend fun findDrinksByDrinkTypeAndVolumeAndEstablishment(drinkType: DrinkType, volume: Int, establishment: Establishment): List<Beverage>? {
        val reqURL = "$baseUrl/types/${drinkType.id}/volume/${volume}/establishment/${establishment.id}"
        return try {
            val response = networkingService.getRequest(reqURL)
            val gson = Gson()
            gson.fromJson(response, Array<Beverage>::class.java).toList()
        } catch (e: Exception) {
            Log.e("DrinkTypeServiceImpl", "Error fetching beverage by drinktype, volume and establishment", e)
            null
        }
    }
}
