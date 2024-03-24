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
import hbv601g.hugb2_team2.session.SessionManager
import hbv601g.hugb2_team2.ui.activities.establishment.CreateEstablishmentActivity


class DrinkTypeListFragment : Fragment() {


    private lateinit var viewModel: DrinkTypeListViewModel
    private lateinit var binding: FragmentDrinktypeListBinding
    private lateinit var sessionManager: SessionManager


    private var _binding: FragmentDrinktypeListBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrinktypeListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        sessionManager = SessionManager(requireContext())

        viewModel = ViewModelProvider(this).get(DrinkTypeListViewModel::class.java)


        val tableLayout = root.findViewById<TableLayout>(R.id.tableLayout)


        viewModel.drinkTypes.observe(viewLifecycleOwner) { drinkTypes ->
            if (drinkTypes != null) {
                populateTable(drinkTypes)
            }
        }

        return root
    }


    private fun populateTable(drinkTypes: List<DrinkType>) {
        val tableLayout = binding.tableLayout

        for ((id, name, percentage, type, subType) in drinkTypes) {
            val row = TableRow(context)
            val nameTextView = TextView(context).apply {
                text = name
                setTextColor(Color.BLUE) // Set text color to blue for a link-like appearance
                paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG // Add underline
                isClickable = true // Set clickable
                isFocusable = true // Set focusable
                setOnClickListener {
                // Handle click event here
                val intent = Intent(context, BeverageListActivity::class.java)
                // Pass any necessary data with the intent
                Log.d("id", id.toString())
                intent.putExtra("drinkTypeId", id)
                context.startActivity(intent)
                }
            }
            row.addView(nameTextView)

            val typeTextView = TextView(context).apply {
                text = type
            }
            row.addView(typeTextView)

            val subtypeTextView = TextView(context).apply {
                text = subType
            }
            row.addView(subtypeTextView)

            val percentageTextView = TextView(context).apply {
                text = percentage.toString()
            }
            row.addView(percentageTextView)

            tableLayout.addView(row)
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
