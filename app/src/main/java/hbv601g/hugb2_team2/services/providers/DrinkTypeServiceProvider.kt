package hbv601g.hugb2_team2.services.providers

import android.content.Context
import hbv601g.hugb2_team2.services.DrinkTypeService
import hbv601g.hugb2_team2.services.implementation.DrinkTypeServiceImpl

object DrinkTypeServiceProvider {
    private var drinkTypeService: DrinkTypeService? = null

    fun getDrinkTypeService(context: Context): DrinkTypeService {
        return drinkTypeService ?: synchronized(this) {
            drinkTypeService ?: createDrinkTypeService(context).also { drinkTypeService = it }
        }
    }

    private fun createDrinkTypeService(context: Context): DrinkTypeService {
        val service = DrinkTypeServiceImpl(context)
        service.setContext(context)
        return service
    }
}