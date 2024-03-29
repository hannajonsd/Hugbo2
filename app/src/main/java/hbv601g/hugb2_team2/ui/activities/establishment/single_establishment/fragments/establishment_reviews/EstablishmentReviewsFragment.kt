package hbv601g.hugb2_team2.ui.activities.establishment.single_establishment.fragments.establishment_reviews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import hbv601g.hugb2_team2.databinding.FragmentEstablishmentReviewsBinding
import hbv601g.hugb2_team2.services.ReviewService
import hbv601g.hugb2_team2.services.providers.BeverageServiceProvider
import hbv601g.hugb2_team2.services.providers.ReviewServiceProvider

class EstablishmentReviewsFragment : Fragment() {

    private var reviewService = ReviewServiceProvider.getReviewService()

    private var _binding: FragmentEstablishmentReviewsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val establishmentReviewsViewModel =
            ViewModelProvider(this).get(EstablishmentReviewsViewModel::class.java)

        _binding = FragmentEstablishmentReviewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textEstablishmentReviews
        establishmentReviewsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}