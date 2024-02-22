package hbv601g.hugb2_team2.services.providers

import android.content.Context
import hbv601g.hugb2_team2.services.EstablishmentService
import hbv601g.hugb2_team2.services.implementation.EstablishmentServiceImpl

object EstablishmentServiceProvider {
    private var establishmentService: EstablishmentService? = null

    fun getEstablishmentService(context: Context): EstablishmentService {
        if (establishmentService == null) {
            establishmentService = EstablishmentServiceImpl()
            establishmentService!!.setContext(context)
        }
        return establishmentService!!
    }
}