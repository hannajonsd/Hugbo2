package hbv601g.hugb2_team2.ui.activities.single_establishment

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.databinding.ActivitySingleEstablishmentBinding

class SingleEstablishmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleEstablishmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySingleEstablishmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val toolbar: Toolbar = findViewById(R.id.toolbar) // replace 'toolbar' with the id of your Toolbar
        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host_fragment_activity_single_establishment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_establishment_menu, R.id.navigation_establishment_info, R.id.navigation_establishment_reviews
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}