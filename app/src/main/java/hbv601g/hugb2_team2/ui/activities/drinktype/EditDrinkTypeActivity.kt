package hbv601g.hugb2_team2.ui.activities.drinktype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.services.providers.DrinkTypeServiceProvider

class EditDrinkTypeActivity : AppCompatActivity() {

    private var drinkTypeService = DrinkTypeServiceProvider.getDrinkTypeService(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_drink_type)
    }
}