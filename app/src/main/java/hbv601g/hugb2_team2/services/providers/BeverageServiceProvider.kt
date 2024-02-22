package hbv601g.hugb2_team2.services.providers

import android.content.Context
import hbv601g.hugb2_team2.services.BeverageService
import hbv601g.hugb2_team2.services.implementation.BeverageServiceImpl

object BeverageServiceProvider {

    private lateinit var beverageService: BeverageService

    fun getBeverageService() : BeverageService {
        if (!::beverageService.isInitialized) {
            beverageService = BeverageServiceImpl()
        }
        return beverageService
    }

}