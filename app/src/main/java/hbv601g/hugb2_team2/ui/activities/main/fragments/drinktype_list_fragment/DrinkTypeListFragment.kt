package hbv601g.hugb2_team2.ui.activities.main.fragments.drinktype_list_fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.databinding.FragmentDrinktypeListBinding
import hbv601g.hugb2_team2.entities.DrinkType
import hbv601g.hugb2_team2.ui.activities.beverage.BeverageListActivity
import hbv601g.hugb2_team2.ui.activities.drinktype.CreateDrinkTypeActivity
import hbv601g.hugb2_team2.ui.activities.drinktype.EditDrinkTypeActivity
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hbv601g.hugb2_team2.databinding.FragmentEstablishmentListBinding
import hbv601g.hugb2_team2.services.DrinkTypeService
import hbv601g.hugb2_team2.services.providers.DrinkTypeServiceProvider
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider
import hbv601g.hugb2_team2.session.SessionManager
import hbv601g.hugb2_team2.ui.activities.establishment.CreateEstablishmentActivity
import hbv601g.hugb2_team2.ui.activities.establishment.single_establishment.SingleEstablishmentActivity
import hbv601g.hugb2_team2.ui.activities.main.fragments.establishment_list_fragment.EstablishmentAdapter
import hbv601g.hugb2_team2.ui.activities.main.fragments.establishment_list_fragment.EstablishmentListViewModel
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val drinkTypeListViewModel =
            ViewModelProvider(this).get(DrinkTypeListViewModel::class.java)
        binding = FragmentDrinktypeListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        sessionManager = SessionManager(requireContext())

        setupRecyclerView()

        getDrinkTypeList()



        return root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {

            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(context)


            val adapter = DrinkTypeAdapter(emptyList(), requireContext(), sessionManager) { drinkType ->
                val intent = Intent(activity, BeverageListActivity::class.java).apply {
                    putExtra("EXTRA_DRINKTYPE_ID", drinkType.id)
                }
                startActivity(intent)
            }
            binding.recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                this.adapter = adapter
            }

        }
    }



    private fun getDrinkTypeList() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val drinkTypes = drinkTypeService.getAllDrinkTypes()
                binding.recyclerView.adapter?.let { adapter ->
                    if (adapter is DrinkTypeAdapter) {
                        adapter.updateData(drinkTypes ?: emptyList())
                    }
                }
            } catch (e: Exception) {
                Log.e("DrinkTypeListFragment", "Error fetching drinktypes", e)
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
}
