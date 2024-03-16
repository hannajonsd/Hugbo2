package hbv601g.hugb2_team2.ui.activities.menu;

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import hbv601g.hugb2_team2.services.providers.DrinkTypeServiceProvider
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider

import hbv601g.hugb2_team2.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddMenuDrinkActivity : AppCompatActivity() {

    private var drinkTypeService = DrinkTypeServiceProvider.getDrinkTypeService()
    private var establishmentService = EstablishmentServiceProvider.getEstablishmentService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_menu)

        val drinkDropdown: Spinner = findViewById(R.id.drink_type_input)
        val addDrinkButton: Button = findViewById(R.id.add_drink_btn)
        val cancelButton: Button = findViewById(R.id.cancel_add_drink_btn)

        // Sækja nöfn allra drykkja, og setja í spinner listann
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val drinkTypes = drinkTypeService.getAllDrinkTypes()
                val drinkTypeNames = drinkTypes?.map { it.name } ?: emptyList()

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

        }
    }
}
