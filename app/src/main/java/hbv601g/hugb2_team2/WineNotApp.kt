package hbv601g.hugb2_team2

import android.app.Application
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import hbv601g.hugb2_team2.services.network.NetworkingServiceProvider

class WineNotApp : Application() {

    companion object {
        lateinit var requestQueue: RequestQueue
    }

    override fun onCreate() {
        super.onCreate()
        requestQueue = Volley.newRequestQueue(this)
        NetworkingServiceProvider.initialize(this)
    }
}