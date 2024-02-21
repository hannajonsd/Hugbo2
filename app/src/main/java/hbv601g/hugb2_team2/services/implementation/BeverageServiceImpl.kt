package hbv601g.hugb2_team2.services.implementation

import android.content.Context
import hbv601g.hugb2_team2.entities.Beverage
import hbv601g.hugb2_team2.entities.DrinkType
import hbv601g.hugb2_team2.entities.Establishment
import hbv601g.hugb2_team2.services.BeverageService
import hbv601g.hugb2_team2.services.network.NetworkingService
import hbv601g.hugb2_team2.services.network.NetworkingServiceProvider

class BeverageServiceImpl(context: Context) : BeverageService
{
    private lateinit var context: Context
    private var networkingService = NetworkingServiceProvider.getNetworkingService(context)
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

    override suspend fun getAllBeveragesByDrinkType(drinkType: DrinkType): List<Beverage> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllBeveragesByDrinkTypeSortByPriceDesc(drinkType: DrinkType): List<Beverage> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllBeveragesByDrinkTypeSortByPriceAsc(drinkType: DrinkType): List<Beverage> {
        TODO("Not yet implemented")
    }

    override fun setContext(context: Context) {
        this.context = context
    }

}