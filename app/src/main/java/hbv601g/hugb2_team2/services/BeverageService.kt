package hbv601g.hugb2_team2.services

import android.content.Context
import hbv601g.hugb2_team2.entities.Beverage
import hbv601g.hugb2_team2.entities.DrinkType
import hbv601g.hugb2_team2.entities.Establishment

interface BeverageService {

    suspend fun createBeverage(beverage: Beverage): Beverage
    suspend fun editBeverage(beverage: Beverage): Beverage
    suspend fun deleteBeverage(beverage: Beverage): Beverage
    suspend fun getMenu(establishment: Establishment): List<Beverage>
    suspend fun getAllBeveragesByDrinkTypeId(drinkTypeId: Long ): List<Beverage>?
    suspend fun getAllBeveragesByDrinkTypeSortByPriceDesc(drinkType: DrinkType): List<Beverage>
    suspend fun getAllBeveragesByDrinkTypeSortByPriceAsc(drinkType: DrinkType): List<Beverage>


    suspend fun getAllBeveragesByDrinkType(drinkType: DrinkType): List<Beverage>
}