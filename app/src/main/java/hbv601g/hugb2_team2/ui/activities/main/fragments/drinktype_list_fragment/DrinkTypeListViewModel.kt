package hbv601g.hugb2_team2.ui.activities.main.fragments.drinktype_list_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DrinkTypeListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is drinktype list Fragment"
    }
    val text: LiveData<String> = _text
}