package hbv601g.hugb2_team2.ui.activities.establishment.single_establishment.fragments.establishment_reviews

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.databinding.FragmentEstablishmentReviewsBinding
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider
import hbv601g.hugb2_team2.services.providers.ReviewServiceProvider
import hbv601g.hugb2_team2.session.SessionManager
import hbv601g.hugb2_team2.ui.activities.beverage.BeverageListActivity
import hbv601g.hugb2_team2.ui.activities.drinktype.CreateDrinkTypeActivity
import hbv601g.hugb2_team2.ui.activities.main.fragments.drinktype_list_fragment.DrinkTypeAdapter
import hbv601g.hugb2_team2.ui.activities.reviews.CreateReviewActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EstablishmentReviewsFragment : Fragment() {

    private lateinit var binding: FragmentEstablishmentReviewsBinding
    private lateinit var reviewAdapter: ReviewAdapter
    private val reviewService = ReviewServiceProvider.getReviewService()
    private val establishmentService = EstablishmentServiceProvider.getEstablishmentService()
    private lateinit var sessionManager: SessionManager


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEstablishmentReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())
        reviewAdapter = ReviewAdapter(emptyList(), requireContext(), sessionManager)

        binding.rvReviews.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            this.adapter= reviewAdapter
        }

        val establishmentId = requireActivity().intent.getLongExtra("ESTABLISHMENT_ID", -1L)
        if (establishmentId != -1L) {
            getAndDisplayReviews(establishmentId)
        }

        val addReviewButton = view.findViewById<Button>(R.id.button_add_review)
        if (sessionManager.isLoggedIn()) {
            addReviewButton.visibility = View.VISIBLE
            addReviewButton.setOnClickListener {
                val intent = Intent(activity, CreateReviewActivity::class.java)
                intent.putExtra("ESTABLISHMENT_ID", establishmentId)
                intent.putExtra("USER_ID", sessionManager.getUserId())
                startActivity(intent)
            }
        } else {
            addReviewButton.visibility = View.GONE
        }
    }

    private fun getAndDisplayReviews(establishmentId: Long) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val est = establishmentService.getEstablishmentById(establishmentId)
                val reviews = reviewService.getReviewsByEstablishment(est) // Implement this method based on your service
                if (reviews.isEmpty()) {
                    binding.textViewNoReviews.visibility = View.VISIBLE
                } else {
                    reviewAdapter.updateData(reviews)
                    binding.textViewNoReviews.visibility = View.GONE
                }
            } catch (e: Exception) {
                Log.e("ReviewsFragment", "Error fetching reviews", e)
            }
        }
    }

    /**
     * Update the reviews list when the fragment resumes
     */
    override fun onResume() {
        super.onResume()
        val establishmentId = requireActivity().intent.getLongExtra("ESTABLISHMENT_ID", -1L)
        if (establishmentId != -1L) {
            getAndDisplayReviews(establishmentId)
        }
    }
}
