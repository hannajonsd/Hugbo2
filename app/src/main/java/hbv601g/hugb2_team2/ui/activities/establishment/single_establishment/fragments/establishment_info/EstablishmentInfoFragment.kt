package hbv601g.hugb2_team2.ui.activities.establishment.single_establishment.fragments.establishment_info

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import hbv601g.hugb2_team2.databinding.FragmentEstablishmentInfoBinding
import hbv601g.hugb2_team2.services.EstablishmentService
import hbv601g.hugb2_team2.services.providers.BeverageServiceProvider
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider
import hbv601g.hugb2_team2.ui.activities.establishment.single_establishment.SingleEstablishmentActivity.SharedViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import android.util.Log
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.entities.Establishment


class EstablishmentInfoFragment : Fragment() {

    private var _binding: FragmentEstablishmentInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEstablishmentInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        sharedViewModel.establishment.observe(viewLifecycleOwner) { establishment ->
            Log.d("SharedViewModel", "Setting establishment in ViewModel: $establishment")
            binding.estName.text = establishment.name
            binding.estType.text = establishment.type
            binding.estLocation.text = "${establishment.location} - ${establishment.address}"
            binding.estTime.text = establishment.openingHours
            //binding.textEstablishmentInfo.text = "${establishment.name}\nAddress: ${establishment.address}\nType of establishment: ${establishment.type}\nLocation: ${establishment.location}\nOpening hours: ${establishment.openingHours} \n"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}