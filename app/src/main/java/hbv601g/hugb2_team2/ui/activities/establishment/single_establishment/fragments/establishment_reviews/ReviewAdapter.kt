package hbv601g.hugb2_team2.ui.activities.establishment.single_establishment.fragments.establishment_reviews

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.entities.Beverage
import hbv601g.hugb2_team2.entities.Establishment
import hbv601g.hugb2_team2.services.providers.DrinkTypeServiceProvider
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider
import hbv601g.hugb2_team2.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Log
import android.widget.Toast
import hbv601g.hugb2_team2.entities.Review
import hbv601g.hugb2_team2.services.providers.BeverageServiceProvider
import hbv601g.hugb2_team2.ui.activities.menu.EditMenuDrinkActivity
import kotlinx.coroutines.CoroutineScope

class ReviewAdapter(private var reviews: List<Review>,
                    private val context: Context,
                    private val sessionManager: SessionManager, ) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>(){
    class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ratingView: TextView = view.findViewById(R.id.reviewRating)
        val commentView: TextView = view.findViewById(R.id.reviewComment)
        val userView: TextView = view.findViewById(R.id.reviewUser)
        val dateView: TextView = view.findViewById(R.id.reviewDate)
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reviews_list, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder , position: Int) {
        val review = reviews[position]
        Log.d("review comment", review.comment)
        holder.ratingView.text = review.rating.toString()
        holder.commentView.text = review.comment
        holder.userView.text = review.user.username
        holder.dateView.text = review.date
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newReviews: List<Review>) {
        reviews = newReviews
        notifyDataSetChanged()
    }
}
