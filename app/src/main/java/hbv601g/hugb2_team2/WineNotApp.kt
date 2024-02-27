package hbv601g.hugb2_team2

import android.app.Application
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import hbv601g.hugb2_team2.services.network.NetworkingServiceProvider

class WineNotApp : Application() {

    override fun onCreate() {
        super.onCreate()
        NetworkingServiceProvider.initialize(this)
    }
}