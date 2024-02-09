package hbv601g.hugb2_team2.ui.activities.main.fragments.drinktype_list_fragment

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
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.ui.activities.create_drinktype.CreateDrinkTypeActivity

class DrinkTypeListFragment : Fragment() {

    private var _binding: FragmentDrinktypeListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val drinkTypeListViewModel =
                ViewModelProvider(this).get(DrinkTypeListViewModel::class.java)

        _binding = FragmentDrinktypeListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        drinkTypeListViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


// ...

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonOpenCreateDrinkType = view.findViewById<Button>(R.id.button_open_create_drink_type)
        buttonOpenCreateDrinkType.setOnClickListener {
            val intent = Intent(activity, CreateDrinkTypeActivity::class.java)
            startActivity(intent)
        }
    }
}