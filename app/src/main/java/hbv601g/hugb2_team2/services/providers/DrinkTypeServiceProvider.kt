package hbv601g.hugb2_team2.services.providers

import hbv601g.hugb2_team2.services.DrinkTypeService
import hbv601g.hugb2_team2.services.implementation.DrinkTypeServiceImpl

object DrinkTypeServiceProvider {

    private var drinkTypeService: DrinkTypeService? = null

    fun getDrinkTypeService(): DrinkTypeService {
        if (drinkTypeService == null) {
            drinkTypeService = DrinkTypeServiceImpl()
        }
        return drinkTypeService!!
    }
}