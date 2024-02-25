package hbv601g.hugb2_team2.ui.activities.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.services.providers.UserServiceProvider
import android.widget.EditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private var userService = UserServiceProvider.getUserService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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

            CoroutineScope(Dispatchers.IO).launch {
                val user = userService.getUserByUsername(username)

                if (user == null || user.password == password) {
                    // TODO: Skila error message
                }

                // TODO: Ef allt er í lagi, þá skipta yfir í profile eða main menu
            }
        }

        switchToCreateAccount.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }
}