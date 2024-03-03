package hbv601g.hugb2_team2.ui.activities.establishment.single_establishment

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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import hbv601g.hugb2_team2.entities.Establishment
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import android.util.Log

class SingleEstablishmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleEstablishmentBinding
    private var establishmentService = EstablishmentServiceProvider.getEstablishmentService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySingleEstablishmentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView

        val toolbar: Toolbar =
            findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host_fragment_activity_single_establishment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_establishment_menu,
                R.id.navigation_establishment_info,
                R.id.navigation_establishment_reviews
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val establishmentId = intent.getLongExtra("ESTABLISHMENT_ID", -1L)
        if (establishmentId != -1L) {
            getEstablishmentAndSetInViewModel(establishmentId)
        }
    }

    private fun getEstablishmentAndSetInViewModel(establishmentId: Long) {
        // Use lifecycleScope to launch a coroutine
        Log.d("SingleEstActivity", "Fetching establishment for ID: $establishmentId")
        lifecycleScope.launch {
            try {
                val establishment = establishmentService.getEstablishmentById(establishmentId)
                Log.d("SingleEstablishmentActivity", "Fetched establishment: $establishment")

                val sharedViewModel = ViewModelProvider(this@SingleEstablishmentActivity).get(SharedViewModel::class.java)
                sharedViewModel.setEstablishment(establishment)
            } catch (e: Exception) {
                Log.e("SingleEstablishmentActivity", "Error fetching establishment", e)
            }
        }
    }

        class SharedViewModel : ViewModel() {
            private val _establishment = MutableLiveData<Establishment>()
            val establishment: LiveData<Establishment> = _establishment

            fun setEstablishment(establishment: Establishment) {
                Log.d("SharedViewModel", "Setting establishment in ViewModel: $establishment")
                _establishment.value = establishment
            }
        }
}