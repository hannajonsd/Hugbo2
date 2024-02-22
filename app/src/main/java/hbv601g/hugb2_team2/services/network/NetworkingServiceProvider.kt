package hbv601g.hugb2_team2.services.network

import android.content.Context

object NetworkingServiceProvider {

    @Volatile
    private var networkingService: NetworkingService? = null

    fun getNetworkingService(context: Context): NetworkingService {
        if (networkingService == null) {
            networkingService = NetworkingServiceImpl(context)
            networkingService!!.setContent(context)
        }
        return networkingService!!
    }
}