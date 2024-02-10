package hbv601g.hugb2_team2.ui.activities.single_establishment.fragments.establishment_reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EstablishmentReviewsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}