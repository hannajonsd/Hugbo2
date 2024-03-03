package hbv601g.hugb2_team2.ui.activities.establishment.single_establishment.fragments.establishment_menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import hbv601g.hugb2_team2.databinding.FragmentEstablishmentMenuBinding
import hbv601g.hugb2_team2.services.BeverageService
import hbv601g.hugb2_team2.services.providers.BeverageServiceProvider
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import hbv601g.hugb2_team2.entities.Beverage
import hbv601g.hugb2_team2.entities.Establishment
import hbv601g.hugb2_team2.ui.activities.establishment.single_establishment.fragments.establishment_menu.EstablishmentMenuAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log



/*class EstablishmentMenuFragment : Fragment() {

    private var beverageService = BeverageServiceProvider.getBeverageService()

    private var _binding: FragmentEstablishmentMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val establishmentMenuViewModel =
            ViewModelProvider(this).get(EstablishmentMenuViewModel::class.java)

        _binding = FragmentEstablishmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textEstablishmentMenu
        establishmentMenuViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }


}*/

class EstablishmentMenuFragment : Fragment() {

    private lateinit var binding: FragmentEstablishmentMenuBinding
    private lateinit var menuAdapter: EstablishmentMenuAdapter
    private var establishmentService = EstablishmentServiceProvider.getEstablishmentService()
    private var beverageService = BeverageServiceProvider.getBeverageService()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEstablishmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuAdapter = EstablishmentMenuAdapter(listOf())

        binding.rvBeverageMenu.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = menuAdapter
        }

        val establishmentId = requireActivity().intent.getLongExtra("ESTABLISHMENT_ID", -1L)
        if (establishmentId != -1L) {
            fetchAndDisplayMenu(establishmentId)
        }
    }

    private fun fetchAndDisplayMenu(establishmentId: Long) {
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
