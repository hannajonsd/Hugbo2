package hbv601g.hugb2_team2.ui.activities.main.fragments.drinktype_list_fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import hbv601g.hugb2_team2.databinding.FragmentDrinktypeListBinding
import android.content.Intent
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.entities.DrinkType
import hbv601g.hugb2_team2.services.DrinkTypeService
import hbv601g.hugb2_team2.services.providers.BeverageServiceProvider
import hbv601g.hugb2_team2.services.providers.DrinkTypeServiceProvider
import hbv601g.hugb2_team2.ui.activities.beverage.BeverageListActivity
import hbv601g.hugb2_team2.ui.activities.drinktype.CreateDrinkTypeActivity
import hbv601g.hugb2_team2.ui.activities.drinktype.EditDrinkTypeActivity


class DrinkTypeListFragment : Fragment() {


    private lateinit var viewModel: DrinkTypeListViewModel
    private lateinit var binding: FragmentDrinktypeListBinding


    private var _binding: FragmentDrinktypeListBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrinktypeListBinding.inflate(inflater, container, false)
        val root: View = binding.root


        viewModel = ViewModelProvider(this).get(DrinkTypeListViewModel::class.java)


        val tableLayout = root.findViewById<TableLayout>(R.id.tableLayout)


        viewModel.drinkTypes.observe(viewLifecycleOwner) { drinkTypes ->
            populateTable(drinkTypes)
        }


        viewModel.fetchDrinkTypes()


        return root
    }


    private fun populateTable(drinkTypes: List<DrinkType>) {
        val tableLayout = binding.tableLayout
        tableLayout.removeAllViews()


        for (drinkType in drinkTypes) {
            val row = TableRow(requireContext())


            val nameTextView = TextView(requireContext())
            nameTextView.text = drinkType.name
            row.addView(nameTextView)


            val typeTextView = TextView(requireContext())
            typeTextView.text = drinkType.type
            row.addView(typeTextView)


            val subtypeTextView = TextView(requireContext())
            subtypeTextView.text = drinkType.subtype
            row.addView(subtypeTextView)


            val percentageTextView = TextView(requireContext())
            percentageTextView.text = drinkType.percentage.toString()
            row.addView(percentageTextView)


            tableLayout.addView(row)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonCreateDrinktype.setOnClickListener {
            startActivity(Intent(activity, CreateDrinkTypeActivity::class.java))
        }


        binding.buttonEditDrinktype.setOnClickListener {
            startActivity(Intent(activity, EditDrinkTypeActivity::class.java))
        }


        binding.buttonGoToBeverageList.setOnClickListener {
            startActivity(Intent(activity, BeverageListActivity::class.java))
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
