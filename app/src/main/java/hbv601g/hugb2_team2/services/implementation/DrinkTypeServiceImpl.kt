package hbv601g.hugb2_team2.services.implementation

import android.content.Context
import hbv601g.hugb2_team2.entities.DrinkType
import hbv601g.hugb2_team2.services.DrinkTypeService
import hbv601g.hugb2_team2.services.network.NetworkingServiceProvider

class DrinkTypeServiceImpl : DrinkTypeService {

    private var networkingService = NetworkingServiceProvider.getNetworkingService()

    override suspend fun getAllDrinkTypes(): List<DrinkType> {
        TODO("Not yet implemented")
    }

    override suspend fun getDrinkTypeById(id: Long): DrinkType {
        TODO("Not yet implemented")
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