package hbv601g.hugb2_team2.ui.activities.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.services.providers.UserServiceProvider
import android.widget.EditText
import android.widget.Toast
import hbv601g.hugb2_team2.entities.User
import hbv601g.hugb2_team2.session.PasswordHash
import hbv601g.hugb2_team2.session.SessionManager
import hbv601g.hugb2_team2.ui.activities.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private var userService = UserServiceProvider.getUserService()
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)

        val usernameInput: EditText = findViewById(R.id.login_username)
        val passwordInput: EditText = findViewById(R.id.login_pass)
        val submitButton: Button = findViewById(R.id.login_btn)
        val switchToCreateAccount: Button = findViewById(R.id.switch_to_create_btn)

        submitButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if (username.isEmpty()) {
                usernameInput.error = "Email can't be blank"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                passwordInput.error = "Password can't be blank"
                return@setOnClickListener
            }

            login(username, password)
        }

        switchToCreateAccount.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Login, skráir notandan inn ef upplýsingar passa
     * @param username username notandans
     * @param password password notandans
     */
    private fun login(username: String, password: String) {
        val hasher = PasswordHash()
        CoroutineScope(Dispatchers.Main).launch {
            val user: User? = userService.getUserByUsername(username)

            if (user != null && hasher.checkPassword(password, user.password)) {
                createToast("Logged in")

                sessionManager.createSession(user)

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            } else {
                createToast("Username or password is incorrect")
            }
        }
    }

    private fun createToast(msg: String) {
        Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_SHORT).show()
    }
}