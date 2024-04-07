package hbv601g.hugb2_team2.ui.activities.beverage

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.databinding.ActivityBeverageListBinding
import hbv601g.hugb2_team2.entities.DrinkType
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
    private lateinit var beverageTypeTableLayout: TableLayout
    private lateinit var establishmentTableLayout: TableLayout
    private lateinit var beverageNameTextView: TextView
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: ActivityBeverageListBinding
    private lateinit var sortByPriceSpinner: Spinner
    private var currentSortOrder = "ASC"
    private lateinit var drinkType: DrinkType



    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeverageListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drinkTypeId = intent.getLongExtra("DRINKTYPE_ID", -1)

        // Get the drink type ID passed to the activity
        establishmentTableLayout = findViewById(R.id.establishmentTableLayout)

        setupSortByPriceSpinner()
        findViewById<Button>(R.id.sortByPriceButton).setOnClickListener {
            sortBeverages()
        }


        val view = binding.root
        sessionManager = SessionManager(applicationContext)
        Log.d("BeverageListActivity", "All user info: \nLogged in: ${sessionManager.isLoggedIn()}\nAdmin: ${sessionManager.isAdmin()}\nFirst name: ${sessionManager.getFirstName()}\nLast name: ${sessionManager.getLastName()}")



        CoroutineScope(Dispatchers.Main).launch {
            try {
                beverageService = BeverageServiceProvider.getBeverageService()
                drinkTypeService = DrinkTypeServiceProvider.getDrinkTypeService()


                // Fetch drink type
                drinkType = drinkTypeService.getDrinkTypeById(drinkTypeId) ?: return@launch;            val row = TableRow(this@BeverageListActivity)

                binding.drinktypeName.text = drinkType!!.name
                binding.drinktypeSubtype.text = "${drinkType.type} - ${drinkType.subType}"
                binding.drinktypePercentage.text = "${drinkType.percentage}%"

                val inflater = LayoutInflater.from(this@BeverageListActivity)

                // Fetch all beverages by drink type
                val beverages = beverageService.getAllBeveragesByDrinkTypeId(drinkTypeId)


                // Populate table with beverages
                if (beverages != null) {
                    for (beverage in beverages) {
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

            // Initialize the beverage service

        }

    }

    private fun setupSortByPriceSpinner() {
        sortByPriceSpinner = findViewById(R.id.sortByPriceDropdown)
        sortByPriceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                currentSortOrder = if (position == 0) "ASC" else "DESC"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    private fun sortBeverages() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                if(!this@BeverageListActivity::drinkType.isInitialized) {
                    Toast.makeText(this@BeverageListActivity, "Drink type not initialized.", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val beverages = if (currentSortOrder == "ASC") {
                    beverageService.getAllBeveragesByDrinkTypeSortByPriceAsc(drinkType)
                } else {
                    beverageService.getAllBeveragesByDrinkTypeSortByPriceDesc(drinkType)
                }

                establishmentTableLayout.removeAllViews()

                val inflater = LayoutInflater.from(this@BeverageListActivity)

                for (beverage in beverages) {
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
            } catch (e: Exception) {
                Log.e("BeverageListActivity", "Exception: $e")
                Toast.makeText(this@BeverageListActivity, "Failed to sort beverages.", Toast.LENGTH_SHORT).show()
            }
        }
    }


}