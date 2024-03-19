package hbv601g.hugb2_team2.ui.activities.establishment


import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.Manifest
import android.location.Location
import android.os.Looper
import android.widget.Toast
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import hbv601g.hugb2_team2.entities.EstablishmentWithDistance
import hbv601g.hugb2_team2.ui.activities.establishment.single_establishment.SingleEstablishmentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NearbyEstablishmentsActivity : AppCompatActivity() {

    private var establishmentService = EstablishmentServiceProvider.getEstablishmentService()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearby_establishments)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewNearbyEstablishments)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val spinnerRadius = findViewById<Spinner>(R.id.spinnerRadius)
        val buttonSearch = findViewById<Button>(R.id.buttonSearch)


        buttonSearch.setOnClickListener {
            val radius = spinnerRadius.selectedItem.toString().toInt()

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                val cancellationTokenSource = CancellationTokenSource()

                val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
                    .setWaitForAccurateLocation(true)
                    .build()


                fusedLocationClient.getCurrentLocation(locationRequest.priority, cancellationTokenSource.token)
                    .addOnSuccessListener { location: Location? ->
                        val latitude = location?.latitude ?: 0.0
                        val longitude = location?.longitude ?: 0.0

                        CoroutineScope(Dispatchers.Main).launch {
                            val nearbyEstablishments = establishmentService.getNearbyEstablishments(
                                latitude,
                                longitude,
                                radius
                            )
                            recyclerView.adapter = NearbyEstablishmentsAdapter(
                                nearbyEstablishments,
                                object : NearbyEstablishmentsAdapter.OnItemClickListener {
                                    override fun onItemClick(establishment: EstablishmentWithDistance) {
                                        // Handle click on establishment
                                        val intent = Intent(
                                            this@NearbyEstablishmentsActivity,
                                            SingleEstablishmentActivity::class.java
                                        ).apply {
                                            putExtra("ESTABLISHMENT_ID", establishment.establishment.id)
                                        }
                                        startActivity(intent)
                                    }
                                })
                        }
                    }.addOnFailureListener {
                        exception ->
                        Toast.makeText(this, "Failed to get location: ${exception.message}", Toast.LENGTH_LONG).show()
                    }
            } else {
                // Request permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            }
        }

    }
}