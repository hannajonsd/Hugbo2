package hbv601g.hugb2_team2.services.providers

import android.content.Context
import hbv601g.hugb2_team2.services.DrinkTypeService
import hbv601g.hugb2_team2.services.implementation.DrinkTypeServiceImpl

object DrinkTypeServiceProvider {

    private lateinit var drinkTypeService: DrinkTypeService

    fun getDrinkTypeService() : DrinkTypeService {
        if (!::drinkTypeService.isInitialized) {
            drinkTypeService = DrinkTypeServiceImpl()
        }
        return drinkTypeService
    }
}