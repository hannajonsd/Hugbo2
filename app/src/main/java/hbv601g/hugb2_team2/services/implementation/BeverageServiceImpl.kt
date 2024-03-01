package hbv601g.hugb2_team2.services.implementation

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import hbv601g.hugb2_team2.entities.Beverage
import hbv601g.hugb2_team2.entities.DrinkType
import hbv601g.hugb2_team2.entities.Establishment
import hbv601g.hugb2_team2.services.BeverageService
import hbv601g.hugb2_team2.services.network.NetworkingService
import hbv601g.hugb2_team2.services.network.NetworkingServiceProvider

class BeverageServiceImpl : BeverageService
{
    private var networkingService = NetworkingServiceProvider.getNetworkingService()
    private var baseUrl = "/drinks"
    override suspend fun createBeverage(beverage: Beverage): Beverage {
        TODO("Not yet implemented")
    }

    override suspend fun editBeverage(beverage: Beverage): Beverage {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBeverage(beverage: Beverage): Beverage {
        TODO("Not yet implemented")
    }

    override suspend fun getMenu(establishment: Establishment): List<Beverage> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllBeveragesByDrinkTypeId(drinkTypeId: Long): List<Beverage>? {
        val reqURL = "$baseUrl/types/$drinkTypeId"
        try {
            val response = networkingService.getRequest(reqURL)
            Log.d("BeverageServiceImpl", "Response: $response")
            // response is JSON element, convert to list of establishments
            val gson = Gson()
            return gson.fromJson(response, Array<Beverage>::class.java).toList()
        } catch (e: Exception) {
            Log.d("DrinkTypeServiceImpl", "Exception: $e")

            return null
        }
    }

    override suspend fun getAllBeveragesByDrinkTypeSortByPriceDesc(drinkType: DrinkType): List<Beverage> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllBeveragesByDrinkTypeSortByPriceAsc(drinkType: DrinkType): List<Beverage> {
        TODO("Not yet implemented")
    }


}