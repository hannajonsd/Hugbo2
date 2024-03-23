package hbv601g.hugb2_team2.ui.activities.drinktype

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.entities.Beverage
import hbv601g.hugb2_team2.entities.DrinkType
import hbv601g.hugb2_team2.services.providers.BeverageServiceProvider
import hbv601g.hugb2_team2.services.providers.DrinkTypeServiceProvider
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditDrinkTypeActivity : AppCompatActivity() {

    private var beverageService = BeverageServiceProvider.getBeverageService()
    private var drinkTypeService = DrinkTypeServiceProvider.getDrinkTypeService()
    private var establishmentService = EstablishmentServiceProvider.getEstablishmentService()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_drink_type)

        val nameInput: EditText = findViewById(R.id.edit_drinktype_name_input)
        val perInput: EditText = findViewById(R.id.edit_drinktype_percentage_input)
        val typeInput: EditText = findViewById(R.id.edit_drinktype_type_input)
        val subtypeInput: EditText = findViewById(R.id.edit_drinktype_subtype_input)



        val editDrinkButton: Button = findViewById(R.id.button_edit_drinktype)
        val cancelButton: Button = findViewById(R.id.cancel_edit_drinktype)



        var drinkTypeId = intent.getLongExtra("DRINKTYPE_ID", -1L)




        cancelButton.setOnClickListener {
            finish()
        }

        // Bæta við drykk á menu-ið
        editDrinkButton.setOnClickListener {
            val nameValue = nameInput.text.toString()
            val perValue = perInput.text.toString().trim().toDoubleOrNull()
            val typeValue = typeInput.text.toString()
            val subTypeValue = subtypeInput.text.toString()

            if (nameValue == "") {

                return@setOnClickListener
            }

            if (perValue == null || perValue == 0.0) {

                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.Main).launch {
                try {

                    val exists: List<DrinkType>? = drinkTypeService.getDrinkTypeByName()
                    if (exists.isNullOrEmpty()) {
                        val editedDrinkType = DrinkType(drinkTypeId, nameValue, perValue, typeValue, subTypeValue)
                        editDrinkType(editedDrinkType)
                    } else {
                        Toast.makeText(this@EditDrinkTypeActivity, "Drink with same type and volume already exists in this menu", Toast.LENGTH_SHORT).show()
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
    private fun editDrinkType(drinktype: DrinkType) {
        CoroutineScope(Dispatchers.Main).launch {
            val drinkType: DrinkType = drinkTypeService.editDrinkType(drinktype)

            Toast.makeText(this@EditDrinkTypeActivity, "Drinktype saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}