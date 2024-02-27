package hbv601g.hugb2_team2.ui.activities.establishment

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.entities.Establishment
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class CreateEstablishmentActivity : AppCompatActivity() {

    private var establishmentService = EstablishmentServiceProvider.getEstablishmentService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_establishment)

        // Get references to the EditText and Button views
        val nameInput: EditText = findViewById(R.id.create_est_name_input)
        val addressInput: EditText = findViewById(R.id.create_est_address_input)
        val typeInput: EditText = findViewById(R.id.create_est_type_input)
        val locationInput: Spinner = findViewById(R.id.create_est_location_input)
        val openingHoursInput: EditText = findViewById(R.id.create_est_opening_hours_input)
        val createButton: Button = findViewById(R.id.create_est_button)
        val cancelButton: Button = findViewById(R.id.cancel_est_button)

        // Set up click listener for the create button
        createButton.setOnClickListener {
            // Validate the input
            val name = nameInput.text.toString()
            val address = addressInput.text.toString()
            val type = typeInput.text.toString()
            val location = locationInput.selectedItem.toString()
            val openingHours = openingHoursInput.text.toString()

            if (name.isBlank() || address.isBlank() || type.isBlank() || location.isBlank() || openingHours.isBlank()) {
                // Show an error message if any field is blank
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
            } else {
                // create a new establishment
                val newEstablishment : Establishment = Establishment(
                    0,
                    name,
                    type,
                    address,
                    Establishment.Location.valueOf(location),
                    0.0,
                    0.0,
                    0.0,
                    openingHours,
                    0.0,
                    0.0
                )
                createEstablishment(newEstablishment)
            }
        }

        // Set up click listener for the cancel button
        cancelButton.setOnClickListener {
            // navigate back to the establishment list
            finish()
        }
    }

    fun createEstablishment(newEstablishment: Establishment) {
        // coroutine to create the establishment
        CoroutineScope(Dispatchers.Main).launch {
            val establishment: Establishment? =
                establishmentService.createEstablishment(newEstablishment)
            if (establishment != null) {
                // Show a success message if the establishment was created
                Toast.makeText(
                    this@CreateEstablishmentActivity,
                    "Establishment created",
                    Toast.LENGTH_SHORT
                ).show()
                // print the new establishment
                Log.d("CreateEstablishmentActivity", establishment.toString())
                // navigate back to the establishment list
                finish()
            } else {
                // Show an error message if the establishment was not created
                Toast.makeText(
                    this@CreateEstablishmentActivity,
                    "Failed to create establishment",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}