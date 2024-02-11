package hbv601g.hugb2_team2.ui.activities.establishment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider

class NearbyEstablishmentsActivity : AppCompatActivity() {

    private var establishmentService = EstablishmentServiceProvider.getEstablishmentService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearby_establishments)
    }
}