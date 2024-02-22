package hbv601g.hugb2_team2.services.providers

import android.content.Context
import hbv601g.hugb2_team2.services.EstablishmentService
import hbv601g.hugb2_team2.services.implementation.EstablishmentServiceImpl

object EstablishmentServiceProvider {

    private lateinit var establishmentService: EstablishmentService
    fun getEstablishmentService() : EstablishmentService {
        if (!::establishmentService.isInitialized) {
            establishmentService = EstablishmentServiceImpl()
        }
        return establishmentService
    }
}