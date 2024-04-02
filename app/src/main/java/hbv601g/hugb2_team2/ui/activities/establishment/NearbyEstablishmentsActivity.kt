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
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import hbv601g.hugb2_team2.entities.DrinkType
import hbv601g.hugb2_team2.entities.EstablishmentWithDistance
import hbv601g.hugb2_team2.services.providers.DrinkTypeServiceProvider
import hbv601g.hugb2_team2.ui.activities.establishment.single_establishment.SingleEstablishmentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NearbyEstablishmentsActivity : AppCompatActivity() {

    private var establishmentService = EstablishmentServiceProvider.getEstablishmentService()
    private var drinkTypeService = DrinkTypeServiceProvider.getDrinkTypeService()
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
        val spinnerDrinkType: Spinner = findViewById(R.id.spinnerDrinkType)
        val buttonSearch = findViewById<Button>(R.id.buttonSearch)
        val searchTempText = findViewById<TextView>(R.id.searchTempText)
        val noEstablishmentsFound = findViewById<TextView>(R.id.noEstablishmentsFound)
        // searchTempText and noEstablishmentsFound are initially not visible
        searchTempText.visibility = TextView.GONE
        noEstablishmentsFound.visibility = TextView.GONE
        val searchInstructions = findViewById<TextView>(R.id.searchInstructions)

        var allDrinkTypes: List<DrinkType>? = null
        CoroutineScope(Dispatchers.Main).launch {
            try {
                allDrinkTypes = drinkTypeService.getAllDrinkTypes()
                val drinkTypeNames = allDrinkTypes?.map { it.name } ?.toMutableList() ?: mutableListOf()

                // add an "All" option to the list
                drinkTypeNames.add(0, "All")

                val adapter = ArrayAdapter(this@NearbyEstablishmentsActivity, android.R.layout.simple_spinner_item, drinkTypeNames)

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerDrinkType.adapter = adapter
            } catch (e: Exception) {
                Log.e("AddMenuDrinkActivity", "Error fetching drink types", e)
            }
        }


        buttonSearch.setOnClickListener {
            buttonSearch.isEnabled = false
            // hide the search instructions
            searchInstructions.visibility = TextView.GONE
            noEstablishmentsFound.visibility = TextView.GONE

            val radius = spinnerRadius.selectedItem.toString().toInt()
            val drinkType = spinnerDrinkType.selectedItem.toString()
            // extract the drink type id. if "Any" is selected, set to -1
            val drinkTypeId = allDrinkTypes?.find { it.name == drinkType }?.id ?: -1L
            if (drinkTypeId == -1L) {
                searchTempText.text =
                    getString(R.string.searching_for_all_establishments_within_km, radius.toString())
            } else {
                searchTempText.text = getString(
                    R.string.searching_for_establishments_serving_within_km,
                    drinkType,
                    radius.toString()
                )
            }
            searchTempText.visibility = TextView.VISIBLE

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
                            // check to see if a drink type was selected
                            val nearbyEstablishments = if (drinkTypeId == -1L) establishmentService.getNearbyEstablishments(
                                latitude,
                                longitude,
                                radius,
                            ) else establishmentService.getNearbyEstablishmentsByDrinkType(
                                latitude,
                                longitude,
                                radius,
                                drinkTypeId
                            )
                            if (nearbyEstablishments.isEmpty()) {
                                noEstablishmentsFound.visibility = TextView.VISIBLE
                            }
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
                            // clear the search text
                            searchTempText.text = ""
                            searchTempText.visibility = TextView.GONE
                            buttonSearch.isEnabled = true
                        }
                    }.addOnFailureListener {
                        exception ->
                        Toast.makeText(this, "Failed to get location: ${exception.message}", Toast.LENGTH_LONG).show()
                        // clear the search text
                        searchTempText.text = ""
                        searchTempText.visibility = TextView.GONE
                        buttonSearch.isEnabled = true
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