package hbv601g.hugb2_team2.ui.activities.reviews

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.entities.Review
import hbv601g.hugb2_team2.entities.User
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider
import hbv601g.hugb2_team2.services.providers.ReviewServiceProvider
import hbv601g.hugb2_team2.services.providers.UserServiceProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class CreateReviewActivity : AppCompatActivity() {

    private val reviewService = ReviewServiceProvider.getReviewService()
    private val establishmentService = EstablishmentServiceProvider.getEstablishmentService()
    private val userService = UserServiceProvider.getUserService()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_review)

        val addReviewButton: Button = findViewById(R.id.add_review_btn)
        val cancelReviewButton: Button = findViewById(R.id.cancel_add_review_btn)
        val ratingInput: EditText = findViewById(R.id.reviewRating)
        val commentInput: EditText = findViewById(R.id.add_comment)

        cancelReviewButton.setOnClickListener {
            finish()
        }

        addReviewButton.setOnClickListener {
            val ratingText = ratingInput.text.toString().trim()
            val commentText = commentInput.text.toString().trim()

            if (ratingText.isEmpty() || commentText.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val rating = ratingText.toDoubleOrNull()
            if (rating == null || rating !in 0.0..5.0) {
                ratingInput.error = "Rating must be a number between 0 and 5"
                return@setOnClickListener
            }

            // Assuming you have a user object representing the current user

            val establishmentId = intent.getLongExtra("ESTABLISHMENT_ID", -1L)
            val userId = intent.getLongExtra("USER_ID", -1L)



            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val establishment = establishmentService.getEstablishmentById(establishmentId)
                    val user = userService.getUserById(userId)
                    val review: Review

                    if(user != null && establishment != null){
                        val date = LocalDate.now().toString()
                        review = Review(0, establishment, user, rating, commentText, date)
                        val createdReview = reviewService.createReview(review)
                        Toast.makeText(this@CreateReviewActivity, "Review added successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }else {
                        Log.e("CreateReviewActivity", "Error adding review")
                        Toast.makeText(this@CreateReviewActivity, "An error occurred", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } catch (e: Exception) {
                    Log.e("CreateReviewActivity", "Error adding review", e)
                    Toast.makeText(this@CreateReviewActivity, "An error occurred", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
