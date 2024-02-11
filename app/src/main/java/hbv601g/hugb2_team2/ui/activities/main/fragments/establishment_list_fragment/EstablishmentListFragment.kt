package hbv601g.hugb2_team2.ui.activities.main.fragments.establishment_list_fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.databinding.FragmentEstablishmentListBinding
import hbv601g.hugb2_team2.services.EstablishmentService
import hbv601g.hugb2_team2.ui.activities.establishment.NearbyEstablishmentsActivity
import hbv601g.hugb2_team2.ui.activities.establishment.single_establishment.SingleEstablishmentActivity

class EstablishmentListFragment : Fragment() {

    private lateinit var establishmentService: EstablishmentService

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

        _binding = FragmentEstablishmentListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.titleEstablishmentList
        establishmentListViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonOpenCreateDrinkType =
            view.findViewById<Button>(R.id.button_go_to_single_establishment)
        buttonOpenCreateDrinkType.setOnClickListener {
            val intent = Intent(activity, SingleEstablishmentActivity::class.java)
            startActivity(intent)
        }

        val buttonOpenNearbyEstablishments = view.findViewById<Button>(R.id.button_nearby_establishments)
        buttonOpenNearbyEstablishments.setOnClickListener {
            val intent = Intent(activity, NearbyEstablishmentsActivity::class.java)
            startActivity(intent)
        }
    }
}