package hbv601g.hugb2_team2.ui.activities.establishment.single_establishment.fragments.establishment_reviews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import hbv601g.hugb2_team2.databinding.FragmentEstablishmentReviewsBinding
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider
import hbv601g.hugb2_team2.services.providers.ReviewServiceProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EstablishmentReviewsFragment : Fragment() {

    private lateinit var binding: FragmentEstablishmentReviewsBinding
    private lateinit var reviewAdapter: ReviewAdapter
    private val reviewService = ReviewServiceProvider.getReviewService()
    private val establishmentService = EstablishmentServiceProvider.getEstablishmentService()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEstablishmentReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reviewAdapter = ReviewAdapter(listOf()) // Define your ReviewsAdapter

        binding.rvReviews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = reviewAdapter
        }

        val establishmentId = requireActivity().intent.getLongExtra("ESTABLISHMENT_ID", -1L)
        if (establishmentId != -1L) {
            getAndDisplayReviews(establishmentId)
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
