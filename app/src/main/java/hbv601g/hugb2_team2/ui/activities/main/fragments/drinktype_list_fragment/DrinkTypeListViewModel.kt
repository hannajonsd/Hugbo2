package hbv601g.hugb2_team2.ui.activities.main.fragments.drinktype_list_fragment


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hbv601g.hugb2_team2.entities.DrinkType
import hbv601g.hugb2_team2.services.providers.DrinkTypeServiceProvider
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DrinkTypeListViewModel : ViewModel() {


    private val _drinkTypes = MutableLiveData<List<DrinkType>>()
    val drinkTypes: LiveData<List<DrinkType>> = _drinkTypes
    private var drinkTypeService = DrinkTypeServiceProvider.getDrinkTypeService()


    init {
        // Initialize the ViewModel by fetching data from the repository
        fetchDrinkTypes()
    }


    @OptIn(DelicateCoroutinesApi::class)
    fun fetchDrinkTypes() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = drinkTypeService.getAllDrinkTypes()
                withContext(Dispatchers.Main) {
                    _drinkTypes.value = response
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
