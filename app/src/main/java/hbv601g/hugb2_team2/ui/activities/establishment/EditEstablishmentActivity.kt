package hbv601g.hugb2_team2.ui.activities.establishment

import android.os.Bundle
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditEstablishmentActivity : AppCompatActivity() {

    private var establishmentService = EstablishmentServiceProvider.getEstablishmentService()
    private lateinit var currentEstablishment: Establishment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_establishment)

        val establishmentId = intent.getLongExtra("ESTABLISHMENT_ID", -1L)
        if (establishmentId == -1L) {
            Toast.makeText(this, "Invalid establishment ID", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val nameInput: EditText = findViewById(R.id.edit_est_name_input)
        val addressInput: EditText = findViewById(R.id.edit_est_address_input)
        val locationInput: Spinner = findViewById(R.id.edit_est_location_input)
        val saveChangesButton: Button = findViewById(R.id.edit_est_button)
        val cancelButton: Button = findViewById(R.id.cancel_est_button)
        val openingHoursInput: EditText = findViewById(R.id.edit_est_opening_hours_input)
        val typeInput: EditText = findViewById(R.id.edit_est_type_input)

        fetchEstablishmentDetails(establishmentId) { establishment ->
            nameInput.setText(establishment.name)
            addressInput.setText(establishment.address)
            locationInput.setSelection(establishment.location.ordinal)
            openingHoursInput.setText(establishment.openingHours)
        }

        saveChangesButton.setOnClickListener {
            val updatedEstablishment = Establishment(
                id = currentEstablishment.id,
                name = nameInput.text.toString(),
                type = typeInput.text.toString(),
                address = addressInput.text.toString(),
                location = Establishment.Location.values()[locationInput.selectedItemPosition],
                openingHours = openingHoursInput.text.toString(),
                lat = currentEstablishment.lat,
                lon = currentEstablishment.lon,
                priceRating = currentEstablishment.priceRating,
                selectionRating = currentEstablishment.selectionRating,
                userRating = currentEstablishment.userRating
            )
            updateEstablishment(updatedEstablishment)
        }
        cancelButton.setOnClickListener { finish() }
    }

    private fun fetchEstablishmentDetails(id: Long, onResult: (Establishment) -> Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                val establishment = establishmentService.getEstablishmentById(id)
                withContext(Dispatchers.Main) {
                    establishment?.let {
                        currentEstablishment = it
                        findViewById<EditText>(R.id.edit_est_name_input).setText(it.name)
                        findViewById<EditText>(R.id.edit_est_address_input).setText(it.address)
                        findViewById<EditText>(R.id.edit_est_type_input).setText(it.type)
                        findViewById<EditText>(R.id.edit_est_opening_hours_input).setText(it.openingHours)
                        val locationPosition = Establishment.Location.values().indexOf(it.location)
                        findViewById<Spinner>(R.id.edit_est_location_input).setSelection(locationPosition)
                    } ?: run {
                        Toast.makeText(this@EditEstablishmentActivity, "Establishment not found", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }
        }

        private fun updateEstablishment(updatedEstablishment: Establishment) {
            CoroutineScope(Dispatchers.IO).launch {
                val result = establishmentService.editEstablishment(updatedEstablishment)
                withContext(Dispatchers.Main) {
                    if (result != null) {
                        Toast.makeText(this@EditEstablishmentActivity, "Establishment updated", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@EditEstablishmentActivity, "Failed to update establishment", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
