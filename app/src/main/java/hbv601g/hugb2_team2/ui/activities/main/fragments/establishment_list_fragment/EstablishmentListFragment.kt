package hbv601g.hugb2_team2.ui.activities.main.fragments.establishment_list_fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.databinding.FragmentEstablishmentListBinding
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider
import hbv601g.hugb2_team2.session.SessionManager
import hbv601g.hugb2_team2.ui.activities.establishment.CreateEstablishmentActivity
import hbv601g.hugb2_team2.ui.activities.establishment.NearbyEstablishmentsActivity
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.LinearLayoutManager;

class EstablishmentListFragment : Fragment() {

    private var establishmentService = EstablishmentServiceProvider.getEstablishmentService()
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: FragmentEstablishmentListBinding
    private lateinit var establishmentsAdapter: EstablishmentAdapter

    private var _binding: FragmentEstablishmentListBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentEstablishmentListBinding.inflate(inflater, container, false)
        sessionManager = SessionManager(requireContext())
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        establishmentsAdapter = EstablishmentAdapter(listOf(), requireContext(), sessionManager, viewLifecycleOwner.lifecycleScope)

        binding.recyclerViewEstablishments.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = establishmentsAdapter
        }

        val buttonOpenNearbyEstablishments = view.findViewById<Button>(R.id.button_nearby_establishments)
        buttonOpenNearbyEstablishments.setOnClickListener {
            val intent = Intent(activity, NearbyEstablishmentsActivity::class.java)
            startActivity(intent)
        }
        val createEstablishmentButton = view.findViewById<Button>(R.id.button_create_establishment)
        // CREATE ESTABLISHMENT BUTTON
        if (sessionManager.isLoggedIn() && sessionManager.isAdmin()) {
            createEstablishmentButton.visibility = View.VISIBLE
            createEstablishmentButton.setOnClickListener {
                val intent = Intent(activity, CreateEstablishmentActivity::class.java)
                startActivity(intent)
            }
        } else {
            createEstablishmentButton.visibility = View.GONE
        }

    }

    private fun getAndDisplayEstablishments() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val establishments = establishmentService.getAllEstablishments()
                if (establishments.isNullOrEmpty()) {
                    binding.textViewNoEstablishments.visibility = View.VISIBLE
                } else {
                    establishmentsAdapter.updateData(establishments)
                    binding.textViewNoEstablishments.visibility = View.GONE
                }
            } catch (e: Exception) {
                Log.e("EstablishmentListFragment", "Error fetching establishments", e)
            }
        }
    }

    /**
     * Uppfæra menu listann þegar við komum til baka
     */
    override fun onResume() {
        super.onResume()
        getAndDisplayEstablishments()
    }
}