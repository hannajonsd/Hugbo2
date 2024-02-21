package hbv601g.hugb2_team2.ui.activities.reviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.services.providers.ReviewServiceProvider

class CreateReviewActivity : AppCompatActivity() {

    private var reviewService = ReviewServiceProvider.getReviewService(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_review)
    }
}