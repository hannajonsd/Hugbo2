package hbv601g.hugb2_team2.services.providers

import android.content.Context
import hbv601g.hugb2_team2.services.BeverageService
import hbv601g.hugb2_team2.services.implementation.BeverageServiceImpl

object BeverageServiceProvider {


        private var beverageService: BeverageService? = null

    fun getBeverageService(context: Context): BeverageService {
        if (beverageService == null) {
            beverageService = BeverageServiceImpl(context)
        }
        return beverageService!!
    }
}