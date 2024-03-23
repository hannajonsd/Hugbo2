package hbv601g.hugb2_team2.ui.activities.drinktype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.entities.DrinkType
import hbv601g.hugb2_team2.entities.Establishment
import hbv601g.hugb2_team2.services.providers.DrinkTypeServiceProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateDrinkTypeActivity : AppCompatActivity() {

    private var drinkTypeService = DrinkTypeServiceProvider.getDrinkTypeService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_drink_type)

        // Get references to the EditText and Button views
        val nameInput: EditText = findViewById(R.id.create_drinktype_name_input)
        val perInput: EditText = findViewById(R.id.create_drinktype_percentage_input)
        val typeInput: EditText = findViewById(R.id.create_drinktype_type_input)
        val subTypeInput: EditText = findViewById(R.id.create_drinktype_subtype_input)
        val createButton: Button = findViewById(R.id.create_drinktype_button)
        val cancelButton: Button = findViewById(R.id.cancel_drinktype_button)
        // Set up click listener for the create button
        createButton.setOnClickListener {
            // Validate the input
            val name = nameInput.text.toString()
            val per = perInput.text.toString()
            val type = typeInput.text.toString()
            val subtype = subTypeInput.text.toString()


            if (name.isBlank() || per.isBlank() || type.isBlank() || subtype.isBlank()) {
                // Show an error message if any field is blank
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()

            } else {
                // create a new establishment
                val perc = per.toDouble()
                val newDrinkType : DrinkType = DrinkType(
                    0,
                    name,
                    perc,
                    type,
                    subtype
                )
                createDrinkType(newDrinkType)
            }
        }

        // Set up click listener for the cancel button
        cancelButton.setOnClickListener {
            // navigate back to the drinktype list
            finish()
        }
    }

    fun createDrinkType(newDrinkType: DrinkType) {
        // coroutine to create the drinktype
        CoroutineScope(Dispatchers.Main).launch {
            val drinkType: DrinkType? =
                drinkTypeService.createDrinkType(newDrinkType)
            if (drinkType != null) {
                // Show a success message if the drinktype was created
                Toast.makeText(
                    this@CreateDrinkTypeActivity,
                    "DrinkType created",
                    Toast.LENGTH_SHORT
                ).show()
                // print the new drinktype
                Log.d("CreateEstablishmentActivity", drinkType.toString())
                // navigate back to the drinktype list
                finish()
            } else {
                // Show an error message if the drinktype was not created
                Toast.makeText(
                    this@CreateDrinkTypeActivity,
                    "Failed to create DrinkType",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}