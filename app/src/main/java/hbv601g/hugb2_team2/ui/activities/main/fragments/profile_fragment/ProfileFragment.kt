package hbv601g.hugb2_team2.ui.activities.main.fragments.profile_fragment

import android.content.Context
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
import hbv601g.hugb2_team2.databinding.FragmentProfileBinding
import hbv601g.hugb2_team2.services.UserService
import hbv601g.hugb2_team2.services.providers.BeverageServiceProvider
import hbv601g.hugb2_team2.services.providers.EstablishmentServiceProvider
import hbv601g.hugb2_team2.services.providers.UserServiceProvider
import hbv601g.hugb2_team2.ui.activities.user.CreateAccountActivity
import hbv601g.hugb2_team2.ui.activities.user.EditProfileActivity
import hbv601g.hugb2_team2.ui.activities.user.LoginActivity

class ProfileFragment : Fragment() {

    private val userService : UserService by lazy {
        UserServiceProvider.getUserService(requireContext())
    }

    private var _binding: FragmentProfileBinding ? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
                ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.titleFragmentProfile
        profileViewModel.text.observe(viewLifecycleOwner) {
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
//        get and set the onclick listener for the login, create account, edit profile and delete account buttons
        val loginButton = view.findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        val createAccountButton = view.findViewById<Button>(R.id.create_account_button)
        createAccountButton.setOnClickListener {
            val intent = Intent(activity, CreateAccountActivity::class.java)
            startActivity(intent)
        }

        val editProfileButton = view.findViewById<Button>(R.id.edit_profile_button)
        editProfileButton.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        val deleteAccountButton = view.findViewById<Button>(R.id.delete_account_button)
        deleteAccountButton.setOnClickListener {
            // do nothing for now. just print something to console
            println("Delete account button clicked")
        }
    }
}