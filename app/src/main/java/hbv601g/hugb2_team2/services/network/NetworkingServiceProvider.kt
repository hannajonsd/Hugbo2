package hbv601g.hugb2_team2.services.network

import android.content.Context

object NetworkingServiceProvider {

    @Volatile
    private var networkingService: NetworkingService? = null

    fun getNetworkingService(context: Context): NetworkingService {
        return networkingService ?: synchronized(this) {
            networkingService ?: createNetworkingService(context).also { networkingService = it }
        }
    }

    private fun createNetworkingService(context: Context): NetworkingService {
        return NetworkingServiceImpl(context.applicationContext)
    }
}