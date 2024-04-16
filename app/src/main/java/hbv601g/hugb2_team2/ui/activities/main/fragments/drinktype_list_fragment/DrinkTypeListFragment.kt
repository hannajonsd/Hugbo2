package hbv601g.hugb2_team2.ui.activities.main.fragments.drinktype_list_fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.databinding.FragmentDrinktypeListBinding
import hbv601g.hugb2_team2.ui.activities.drinktype.CreateDrinkTypeActivity
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import hbv601g.hugb2_team2.databinding.FragmentEstablishmentMenuBinding
import hbv601g.hugb2_team2.services.providers.DrinkTypeServiceProvider
import hbv601g.hugb2_team2.session.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DrinkTypeListFragment : Fragment() {
    private lateinit var viewModel: DrinkTypeListViewModel
    private lateinit var binding: FragmentDrinktypeListBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var drinkTypeAdapter: DrinkTypeAdapter
    private var drinkTypeService = DrinkTypeServiceProvider.getDrinkTypeService()

    private var _binding: FragmentDrinktypeListBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDrinktypeListBinding.inflate(inflater, container, false)
        sessionManager = SessionManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drinkTypeAdapter = DrinkTypeAdapter(listOf(), requireContext(), sessionManager, viewLifecycleOwner.lifecycleScope)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = drinkTypeAdapter
        }

        val createDrinkTypeButton = view.findViewById<Button>(R.id.button_create_drinktype)
        if (sessionManager.isLoggedIn() && sessionManager.isAdmin()) {
            createDrinkTypeButton.visibility = View.VISIBLE
            createDrinkTypeButton.setOnClickListener {
                val intent = Intent(activity, CreateDrinkTypeActivity::class.java)
                startActivity(intent)
            }
        } else {
            createDrinkTypeButton.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getAndDisplayDrinkTypes() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val drinkTypes = drinkTypeService.getAllDrinkTypes()
                if (drinkTypes.isNullOrEmpty()) {
                    binding.textViewNoDrinkTypes.visibility = View.VISIBLE
                } else {
                    drinkTypeAdapter.updateData(drinkTypes)
                    binding.textViewNoDrinkTypes.visibility = View.GONE
                }
            } catch (e: Exception) {
                Log.e("DrinkTypeListFragment", "Error fetching drinktypes", e)
            }
        }
    }

    /**
     * Uppfæra menu listann þegar við komum til baka
     */
    override fun onResume() {
        super.onResume()
        getAndDisplayDrinkTypes()
    }
}
