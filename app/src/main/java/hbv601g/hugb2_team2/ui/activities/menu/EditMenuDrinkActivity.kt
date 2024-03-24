package hbv601g.hugb2_team2.ui.activities.menu;

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.entities.Beverage
import hbv601g.hugb2_team2.entities.DrinkType
import hbv601g.hugb2_team2.services.providers.BeverageServiceProvider
import hbv601g.hugb2_team2.services.providers.DrinkTypeServiceProvider
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditMenuDrinkActivity : AppCompatActivity() {
    private var beverageService = BeverageServiceProvider.getBeverageService()
    private var drinkTypeService = DrinkTypeServiceProvider.getDrinkTypeService()
    private var establishmentService = EstablishmentServiceProvider.getEstablishmentService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_beverage)

        val drinkDropdown: Spinner = findViewById(R.id.drink_type_input)
        val volumeInput: EditText = findViewById(R.id.drink_volume_input)
        val priceInput: EditText = findViewById(R.id.drink_price_input)

        val editDrinkButton: Button = findViewById(R.id.edit_drink_btn)
        val cancelButton: Button = findViewById(R.id.cancel_add_drink_btn)

        var allDrinkTypes: List<DrinkType>? = null

        var beverageId = intent.getLongExtra("BEVERAGE_ID", -1L)
        var selectedDrink: Beverage? = null

        // Sækja nöfn allra drykkja, og setja í spinner listann
        CoroutineScope(Dispatchers.Main).launch {
            try {
                selectedDrink = beverageService.getBeverageById(beverageId)

                allDrinkTypes = drinkTypeService.getAllDrinkTypes()
                val drinkTypeNames = allDrinkTypes?.map { it.name } ?: emptyList()

                val adapter = ArrayAdapter(
                    this@EditMenuDrinkActivity,
                    android.R.layout.simple_spinner_item,
                    drinkTypeNames
                )

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                drinkDropdown.adapter = adapter

                val drinkTypeIndex = allDrinkTypes?.indexOfFirst { it.id == selectedDrink?.drinkType?.id }
                drinkDropdown.setSelection(drinkTypeIndex ?: 0)

                volumeInput.setText(selectedDrink?.volume.toString() ?: "")
                priceInput.setText(selectedDrink?.price.toString() ?: "")
            } catch (e: Exception) {
                Log.e("AddMenuDrinkActivity", "Error fetching drink types", e)
            }
        }

        // Fara til baka
        cancelButton.setOnClickListener {
            finish()
        }

        // Bæta við drykk á menu-ið
        editDrinkButton.setOnClickListener {
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

            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val selectedDrinkType = allDrinkTypes?.find { it.name == drinkTypeValue } ?: return@launch

                    val exists: List<Beverage>? = beverageService.findDrinksByDrinkTypeAndVolumeAndEstablishment(selectedDrinkType, volumeValue, selectedDrink!!.establishment)
                    if (exists.isNullOrEmpty() || exists.any { it.id == selectedDrink!!.id }) {
                        val editedDrink = Beverage(selectedDrink!!.id, priceValue, volumeValue, selectedDrink!!.establishment, selectedDrinkType)
                        editDrink(editedDrink)
                    } else {
                        Toast.makeText(this@EditMenuDrinkActivity, "Drink with same type and volume already exists in this menu", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("AddMenuDrinkActivity", "Error fetching drink types", e)
                }
            }
        }
    }

    /**
     * Edit drink, breytir drykk á menu
     * @param drink breyttur drykkur
     */
    private fun editDrink(drink: Beverage) {
        CoroutineScope(Dispatchers.Main).launch {
            val beverage: Beverage? = beverageService.editBeverage(drink)

            if (beverage != null) {
                Toast.makeText(this@EditMenuDrinkActivity, "Drink saved", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this@EditMenuDrinkActivity, "Failed to edit drink", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
