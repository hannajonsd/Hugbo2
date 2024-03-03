package hbv601g.hugb2_team2.services

import android.content.Context
import hbv601g.hugb2_team2.entities.DrinkType

interface DrinkTypeService {

    suspend fun getAllDrinkTypes(): List<DrinkType>?
    suspend fun getDrinkTypeById(id: Long): DrinkType?
    suspend fun getDrinkTypesByType(type: String): List<DrinkType>
    suspend fun getDrinkTypesByTypeAndSubtype(type: String, subtype: String): List<DrinkType>
    suspend fun createDrinkType(drinkType: DrinkType): DrinkType
    suspend fun editDrinkType(drinkType: DrinkType): DrinkType
    suspend fun deleteDrinkType(drinkType: DrinkType) : Boolean

}