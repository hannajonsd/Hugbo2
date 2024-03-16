package hbv601g.hugb2_team2.ui.activities.menu;

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hbv601g.hugb2_team2.services.providers.DrinkTypeServiceProvider
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider

import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.entities.Beverage
import hbv601g.hugb2_team2.entities.DrinkType
import hbv601g.hugb2_team2.services.providers.BeverageServiceProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddMenuDrinkActivity : AppCompatActivity() {

    private var beverageService = BeverageServiceProvider.getBeverageService()
    private var drinkTypeService = DrinkTypeServiceProvider.getDrinkTypeService()
    private var establishmentService = EstablishmentServiceProvider.getEstablishmentService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_beverage)

        val drinkDropdown: Spinner = findViewById(R.id.drink_type_input)
        val volumeInput: EditText = findViewById(R.id.drink_volume_input)
        val priceInput: EditText = findViewById(R.id.drink_price_input)

        val addDrinkButton: Button = findViewById(R.id.add_drink_btn)
        val cancelButton: Button = findViewById(R.id.cancel_add_drink_btn)

        val establishmentId = intent.getLongExtra("ESTABLISHMENT_ID", -1L)

        var allDrinkTypes: List<DrinkType>? = null

        // Sækja nöfn allra drykkja, og setja í spinner listann
        CoroutineScope(Dispatchers.Main).launch {
            try {
                allDrinkTypes = drinkTypeService.getAllDrinkTypes()
                val drinkTypeNames = allDrinkTypes?.map { it.name } ?: emptyList()

                val adapter = ArrayAdapter(this@AddMenuDrinkActivity, android.R.layout.simple_spinner_item, drinkTypeNames)

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                drinkDropdown.adapter = adapter
            } catch (e: Exception) {
                Log.e("AddMenuDrinkActivity", "Error fetching drink types", e)
            }
        }

        // Fara til baka
        cancelButton.setOnClickListener {
            finish()
        }

        // Bæta við drykk á menu-ið
        addDrinkButton.setOnClickListener {
            val volumeValue = volumeInput.text.toString().trim().toIntOrNull()
            val priceValue = priceInput.text.toString().trim().toIntOrNull()
            val drinkTypeValue = drinkDropdown.selectedItem as? String

            if (volumeValue == null || volumeValue == 0) {
                volumeInput.error = "Volume cannot be 0"
                return@setOnClickListener
            }

            if (priceValue == null || priceValue == 0) {
                priceInput.error = "Price cannot be 0"
                return@setOnClickListener
            }

            // TODO: Checka findDrinksByDrinkTypeAndVolumeAndEstablishment

            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val selectedDrinkType = allDrinkTypes?.find { it.name == drinkTypeValue } ?: return@launch

                    val establishment = establishmentService.getEstablishmentById(establishmentId)

                    val drinkToAdd = Beverage(0, priceValue, volumeValue, establishment, selectedDrinkType)
                    addDrink(drinkToAdd)

                } catch (e: Exception) {
                    Log.e("AddMenuDrinkActivity", "Error fetching drink types", e)
                }
            }
        }
    }

    /**
     * Add drink, bætir við drykk á menu
     * @param drink drykkur sem á að bæta við
     */
    private fun addDrink(drink: Beverage) {
        CoroutineScope(Dispatchers.Main).launch {
            val beverage: Beverage? = beverageService.createBeverage(drink)

            if (beverage != null) {
                Toast.makeText(this@AddMenuDrinkActivity, "Drink added", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this@AddMenuDrinkActivity, "Failed to add drink", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
