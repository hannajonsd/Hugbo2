package hbv601g.hugb2_team2.ui.activities.user

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import hbv601g.hugb2_team2.entities.User
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.services.providers.UserServiceProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateAccountActivity : AppCompatActivity() {

    private var userService = UserServiceProvider.getUserService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        val nameInput: EditText = findViewById(R.id.signup_name)
        val emailInput: EditText = findViewById(R.id.signup_email)
        val passwordInput: EditText = findViewById(R.id.signup_pass)
        val confirmPasswordInput: EditText = findViewById(R.id.signup_pass_confirm)
        val submitButton: Button = findViewById(R.id.signup_btn)

        val switchToLogin: Button = findViewById(R.id.switch_to_login_btn)

        submitButton.setOnClickListener {
            val name = nameInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            if (name.isEmpty()) {
                nameInput.error = "Username can't be blank"
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                emailInput.error = "Email can't be blank"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                passwordInput.error = "Password can't be blank"
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                confirmPasswordInput.error = "Password can't be blank"
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                confirmPasswordInput.error = "Passwords do not match"
                return@setOnClickListener
            }

            // TODO: Salt-a password

            // POST-a user í database
            val newUser = User(0, name, password, name, name, email,false)
            CoroutineScope(Dispatchers.IO).launch {
                userService.createUser(newUser)
            }

            // Ef allt er í lagi, þá skipta yfir í login activity
            goToLoginActivity()
        }

        switchToLogin.setOnClickListener {
            goToLoginActivity()
        }
    }

    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}