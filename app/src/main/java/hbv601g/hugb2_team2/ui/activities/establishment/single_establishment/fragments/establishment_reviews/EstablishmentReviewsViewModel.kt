package hbv601g.hugb2_team2.ui.activities.establishment.single_establishment.fragments.establishment_reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EstablishmentReviewsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Establishment Reviews Fragment"
    }
    val text: LiveData<String> = _text
}