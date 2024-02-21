package hbv601g.hugb2_team2.services.providers

import android.content.Context
import hbv601g.hugb2_team2.services.BeverageService
import hbv601g.hugb2_team2.services.implementation.BeverageServiceImpl

object BeverageServiceProvider {


        private var beverageService: BeverageService? = null

        fun getBeverageService(context: Context): BeverageService {
            return beverageService ?: synchronized(this) {
                beverageService ?: createBeverageService(context).also { beverageService = it }
            }
        }

        private fun createBeverageService(context: Context): BeverageService {
            val service = BeverageServiceImpl(context)
            service.setContext(context)
            return service
        }
}