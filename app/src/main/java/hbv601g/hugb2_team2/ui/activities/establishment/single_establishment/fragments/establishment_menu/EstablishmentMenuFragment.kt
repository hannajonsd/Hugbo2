package hbv601g.hugb2_team2.ui.activities.establishment.single_establishment.fragments.establishment_menu

import android.os.Bundle
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hbv601g.hugb2_team2.databinding.FragmentEstablishmentMenuBinding
import hbv601g.hugb2_team2.services.providers.BeverageServiceProvider
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import hbv601g.hugb2_team2.session.SessionManager
import hbv601g.hugb2_team2.ui.activities.menu.AddMenuDrinkActivity

class EstablishmentMenuFragment : Fragment() {

    private lateinit var binding: FragmentEstablishmentMenuBinding
    private lateinit var menuAdapter: EstablishmentMenuAdapter
    private var establishmentService = EstablishmentServiceProvider.getEstablishmentService()
    private var beverageService = BeverageServiceProvider.getBeverageService()
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEstablishmentMenuBinding.inflate(inflater, container, false)
        sessionManager = SessionManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuAdapter = EstablishmentMenuAdapter(listOf(), sessionManager, viewLifecycleOwner.lifecycleScope)

        binding.rvBeverageMenu.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = menuAdapter
        }

        if (sessionManager.isLoggedIn() || sessionManager.isAdmin()) {
            binding.addDrinkButton.visibility = View.VISIBLE
        } else {
            binding.addDrinkButton.visibility = View.GONE
        }

        val establishmentId = requireActivity().intent.getLongExtra("ESTABLISHMENT_ID", -1L)
        if (establishmentId != -1L) {
            getAndDisplayMenu(establishmentId)
        }

        binding.addDrinkButton.setOnClickListener {
            val intent = Intent(context, AddMenuDrinkActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getAndDisplayMenu(establishmentId: Long) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val establishment = establishmentService.getEstablishmentById(establishmentId)
                val menuItems = beverageService.getMenu(establishment)
                if (menuItems.isEmpty()) {
                    binding.textViewNoDrinks.visibility = View.VISIBLE
                } else {
                    menuAdapter.updateData(menuItems)
                    binding.textViewNoDrinks.visibility = View.GONE
                }
            } catch (e: Exception) {
                Log.e("EstablishmentMenuFragment", "Error fetching menu", e)
            }
        }
    }




}
