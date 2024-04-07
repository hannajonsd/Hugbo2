package hbv601g.hugb2_team2.ui.activities.beverage

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.databinding.ActivityBeverageListBinding
import hbv601g.hugb2_team2.services.providers.BeverageServiceProvider
import hbv601g.hugb2_team2.services.providers.DrinkTypeServiceProvider
import hbv601g.hugb2_team2.session.SessionManager
import hbv601g.hugb2_team2.ui.activities.establishment.single_establishment.SingleEstablishmentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch

class BeverageListActivity : AppCompatActivity() {

    private var beverageService = BeverageServiceProvider.getBeverageService()
    private var drinkTypeService = DrinkTypeServiceProvider.getDrinkTypeService()
    private lateinit var establishmentTableLayout: TableLayout
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: ActivityBeverageListBinding

    @SuppressLint("MissingInflatedId", "SetTextI18n", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeverageListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drinkTypeId = intent.getLongExtra("DRINKTYPE_ID", -1)

        // Get the drink type ID passed to the activity
        establishmentTableLayout = findViewById(R.id.establishmentTableLayout)

        val view = binding.root
        sessionManager = SessionManager(applicationContext)
        Log.d("BeverageListActivity", "All user info: \nLogged in: ${sessionManager.isLoggedIn()}\nAdmin: ${sessionManager.isAdmin()}\nFirst name: ${sessionManager.getFirstName()}\nLast name: ${sessionManager.getLastName()}")

        CoroutineScope(Dispatchers.Main).launch {
            try {
                beverageService = BeverageServiceProvider.getBeverageService()
                drinkTypeService = DrinkTypeServiceProvider.getDrinkTypeService()

                // Fetch drink type
                val drinkType = drinkTypeService.getDrinkTypeById(drinkTypeId)

                binding.drinktypeName.text = drinkType!!.name
                binding.drinktypeSubtype.text = "${drinkType.type} - ${drinkType.subType}"
                binding.drinktypePercentage.text = "${drinkType.percentage}%"

                val inflater = LayoutInflater.from(this@BeverageListActivity)

                // Fetch all beverages by drink type
                val beverages = beverageService.getAllBeveragesByDrinkTypeId(drinkTypeId)

                // Populate table with beverages
                if (beverages != null) {
                    for (beverage in beverages) {
                        //val row = TableRow(this@BeverageListActivity)
                        val row = inflater.inflate(R.layout.table_row, null) as TableRow

                        val establishmentTextView = row.findViewById<TextView>(R.id.establishmentName)
                        establishmentTextView.text = beverage.establishment.name

                        val volumeTextView = row.findViewById<TextView>(R.id.volume)
                        volumeTextView.text = "${beverage.volume} ml"

                        val priceTextView = row.findViewById<TextView>(R.id.price)
                        priceTextView.text = "${beverage.price} kr"

                        // Set the click listener on the whole row
                        row.setOnClickListener {
                            val intent = Intent(
                                this@BeverageListActivity,
                                SingleEstablishmentActivity::class.java
                            )
                            intent.putExtra("ESTABLISHMENT_ID", beverage.establishment.id)
                            startActivity(intent)
                        }

                        establishmentTableLayout.addView(row)
                    }
                }
            } catch (e: Exception) {
                Log.e("BeverageListActivity", "Exception: $e")
            }
        }
    }
}