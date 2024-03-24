package hbv601g.hugb2_team2.ui.activities.main.fragments.establishment_list_fragment

import hbv601g.hugb2_team2.entities.Establishment;
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.databinding.FragmentEstablishmentListBinding
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider
import hbv601g.hugb2_team2.session.SessionManager
import hbv601g.hugb2_team2.ui.activities.establishment.CreateEstablishmentActivity
import hbv601g.hugb2_team2.ui.activities.establishment.NearbyEstablishmentsActivity
import hbv601g.hugb2_team2.ui.activities.establishment.single_establishment.SingleEstablishmentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.LinearLayoutManager;
import kotlinx.coroutines.withContext

class EstablishmentListFragment : Fragment() {

    private var establishmentService = EstablishmentServiceProvider.getEstablishmentService()
    private lateinit var sessionManager: SessionManager


    private var _binding: FragmentEstablishmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val establishmentListViewModel =
            ViewModelProvider(this).get(EstablishmentListViewModel::class.java)
        sessionManager = SessionManager(requireContext())

        _binding = FragmentEstablishmentListBinding.inflate(inflater, container, false)
        val root: View = binding.root


        setupRecyclerView()

        getEstablishmentList()
        return root
    }

    private fun setupRecyclerView() {
        binding.recyclerViewEstablishments.apply {

            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(context)


                val adapter = EstablishmentAdapter(emptyList(), requireContext(), sessionManager) { establishment ->
                    val intent = Intent(activity, SingleEstablishmentActivity::class.java).apply {
                        putExtra("EXTRA_ESTABLISHMENT_ID", establishment.id)
                    }
                    startActivity(intent)
                }
                binding.recyclerViewEstablishments.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(context)
                    this.adapter = adapter
                }

        }
    }



    private fun getEstablishmentList() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val establishments = establishmentService.getAllEstablishments()
                binding.recyclerViewEstablishments.adapter?.let { adapter ->
                    if (adapter is EstablishmentAdapter) {
                        adapter.updateData(establishments ?: emptyList())
                    }
                }
            } catch (e: Exception) {
                Log.e("EstablishmentListFragment", "Error fetching establishments", e)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val buttonOpenCreateDrinkType =
            view.findViewById<Button>(R.id.button_go_to_single_establishment)
        buttonOpenCreateDrinkType.setOnClickListener {
            val intent = Intent(activity, SingleEstablishmentActivity::class.java)
            startActivity(intent)
        }*/

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
}