package hbv601g.hugb2_team2.ui.activities.user

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import hbv601g.hugb2_team2.entities.User
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.services.providers.UserServiceProvider
import hbv601g.hugb2_team2.session.SessionManager
import hbv601g.hugb2_team2.session.PasswordHash
import hbv601g.hugb2_team2.ui.activities.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateAccountActivity : AppCompatActivity() {

    private var userService = UserServiceProvider.getUserService()
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        sessionManager = SessionManager(this)
        val hasher = PasswordHash()

        val usernameInput: EditText = findViewById(R.id.signup_username)
        val firstNameInput: EditText = findViewById(R.id.signup_first_name)
        val lastNameInput: EditText = findViewById(R.id.signup_last_name)
        val emailInput: EditText = findViewById(R.id.signup_email)
        val passwordInput: EditText = findViewById(R.id.signup_pass)
        val confirmPasswordInput: EditText = findViewById(R.id.signup_pass_confirm)
        val submitButton: Button = findViewById(R.id.signup_btn)

        val switchToLogin: Button = findViewById(R.id.switch_to_login_btn)

        submitButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val firstName = firstNameInput.text.toString()
            val lastName = lastNameInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            if (username.isEmpty()) {
                usernameInput.error = "Username can't be blank"
                return@setOnClickListener
            }

            if (firstName.isEmpty()) {
                firstNameInput.error = "First name can't be blank"
                return@setOnClickListener
            }

            if (lastName.isEmpty()) {
                lastNameInput.error = "Last name can't be blank"
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

            if (password.length < 8 || !password.matches(".*[a-zA-Z].*".toRegex()) || !password.matches(".*\\d.*".toRegex())) {
                passwordInput.error = "Password must be at least 8 characters long and contain both letters and numbers"
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


            val hashedPassword = hasher.hashPassword(password)

            val newUser = User(username = username, password = hashedPassword, firstName = firstName, lastName = lastName, email = email, admin = false)
            createAccount(newUser)
        }

        switchToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Create account, býr til nýjan notanda
     * @param username notendanafn notandans
     * @param password lykilorð notandans
     */
    private fun createAccount(newUser: User) {
        CoroutineScope(Dispatchers.Main).launch {
            if (userService.getUserByUsername(newUser.username) == null) {
                val user: User? = userService.createUser(newUser)

                if (user != null) {
                    createToast("Account created")

                    sessionManager.createSession(user)

                    val intent = Intent(this@CreateAccountActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                else {
                    createToast("Failed to create account")
                }
            } else {
                createToast("Username is already in use")
            }
        }
    }

    private fun createToast(msg: String) {
        Toast.makeText(this@CreateAccountActivity, msg, Toast.LENGTH_SHORT).show()
    }
}