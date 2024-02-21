package hbv601g.hugb2_team2.services.providers

import android.content.Context
import hbv601g.hugb2_team2.services.EstablishmentService
import hbv601g.hugb2_team2.services.implementation.EstablishmentServiceImpl

object EstablishmentServiceProvider {
    private var establishmentService: EstablishmentService? = null

    fun getEstablishmentService(context: Context): EstablishmentService {
        return establishmentService ?: synchronized(this) {
            establishmentService ?: createEstablishmentService(context).also { establishmentService = it }
        }
    }

    private fun createEstablishmentService(context: Context): EstablishmentService {
        val service = EstablishmentServiceImpl(context)
        service.setContext(context)
        return service
    }
}