package hbv601g.hugb2_team2.ui.activities.beverage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.services.providers.BeverageServiceProvider

class CreateBeverageActivity : AppCompatActivity() {

    private var beverageService = BeverageServiceProvider.getBeverageService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_beverage)
    }
}