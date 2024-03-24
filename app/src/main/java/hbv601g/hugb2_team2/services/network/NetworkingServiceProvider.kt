package hbv601g.hugb2_team2.services.network

import android.content.Context

object NetworkingServiceProvider {

    private lateinit var NetworkingService: NetworkingService

    fun getNetworkingService(): NetworkingService {
        return NetworkingService
    }

    fun initialize(context: Context) {
        NetworkingService = NetworkingServiceImpl(context)
    }
}