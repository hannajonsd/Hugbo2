package hbv601g.hugb2_team2.services.network

object NetworkingServiceProvider {

    private var networkingService: NetworkingService? = null

    fun getNetworkingService(): NetworkingService {
        if (networkingService == null) {
            networkingService = NetworkingServiceImpl()
        }
        return networkingService!!
    }
}