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

class EstablishmentInfoFragment : Fragment() {

    private var establishmentService = EstablishmentServiceProvider.getEstablishmentService()


    private var _binding: FragmentEstablishmentInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val establishmentInfoViewModel =
            ViewModelProvider(this).get(EstablishmentInfoViewModel::class.java)

        _binding = FragmentEstablishmentInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textEstablishmentInfo
        establishmentInfoViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}