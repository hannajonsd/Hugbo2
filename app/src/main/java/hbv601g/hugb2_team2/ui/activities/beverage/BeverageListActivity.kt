package hbv601g.hugb2_team2.ui.activities.beverage

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.databinding.ActivityBeverageListBinding
import hbv601g.hugb2_team2.databinding.FragmentDrinktypeListBinding
import hbv601g.hugb2_team2.services.providers.BeverageServiceProvider
import hbv601g.hugb2_team2.services.providers.DrinkTypeServiceProvider
import hbv601g.hugb2_team2.session.SessionManager
import hbv601g.hugb2_team2.ui.activities.drinktype.CreateDrinkTypeActivity
import hbv601g.hugb2_team2.ui.activities.drinktype.EditDrinkTypeActivity
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



    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeverageListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the drink type ID passed to the activity
        beverageTypeTableLayout = findViewById(R.id.beverageTypeTableLayout)
        establishmentTableLayout = findViewById(R.id.establishmentTableLayout)
        beverageNameTextView = findViewById(R.id.textViewBeverageName)

        val view = binding.root
        val editDrinkTypeButton = view.findViewById<Button>(R.id.button_edit_drinktype)
        editDrinkTypeButton.visibility = View.VISIBLE
        editDrinkTypeButton.setOnClickListener {
            val intent = Intent(this@BeverageListActivity, EditDrinkTypeActivity::class.java)
            startActivity(intent)
        }


        val drinkTypeId = intent.getLongExtra("drinkTypeId", -1)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                beverageService = BeverageServiceProvider.getBeverageService()
                drinkTypeService = DrinkTypeServiceProvider.getDrinkTypeService()


                // Fetch drink type
                val drinkType = drinkTypeService.getDrinkTypeById(drinkTypeId)
                val row = TableRow(this@BeverageListActivity)
                if (drinkType != null) {
                    beverageNameTextView.text = "${drinkType.name}"
                    val typeTextView = TextView(this@BeverageListActivity).apply {
                        text = "${drinkType.type}"
                        layoutParams = TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT,
                            1f
                        )
                    }
                    val subtypeTextView = TextView(this@BeverageListActivity).apply {
                        text = " ${drinkType.subType}"
                        layoutParams = TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT,
                            1f
                        )
                    }
                    val percentageTextView = TextView(this@BeverageListActivity).apply {
                        text = "${drinkType.percentage}%"
                        layoutParams = TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT,
                            1f
                        )
                    }
                    row.addView(typeTextView)
                    row.addView(subtypeTextView)
                    row.addView(percentageTextView)

                    beverageTypeTableLayout.addView(row)
                }


                // Fetch all beverages by drink type
                val beverages = beverageService.getAllBeveragesByDrinkTypeId(drinkTypeId)


                // Populate table with beverages
                if (beverages != null) {
                    for (beverage in beverages) {
                        val row = TableRow(this@BeverageListActivity)

                        val establishmentTextView = TextView(this@BeverageListActivity).apply {
                            text = beverage.establishment.name
                            setTextColor(Color.BLUE)
                            paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
                            isClickable = true
                            setOnClickListener {
                                val intent = Intent(
                                    this@BeverageListActivity,
                                    BeverageListActivity::class.java
                                )
                                intent.putExtra("drinkTypeId", beverage.id)
                                startActivity(intent)
                            }
                        }
                        row.addView(establishmentTextView)

                        val volumeTextView = TextView(this@BeverageListActivity).apply {
                            text = beverage.volume.toString()
                        }
                        row.addView(volumeTextView)

                        val priceTextView = TextView(this@BeverageListActivity).apply {
                            text = beverage.price.toString()
                        }
                        row.addView(priceTextView)

                        establishmentTableLayout.addView(row)
                    }
                }
            } catch (e: Exception) {
                Log.e("BeverageListActivity", "Exception: $e")
            }

            // Initialize the beverage service

        }
    }
}