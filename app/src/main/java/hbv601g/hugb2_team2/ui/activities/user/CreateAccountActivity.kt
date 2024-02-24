package hbv601g.hugb2_team2.ui.activities.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.services.providers.UserServiceProvider

class CreateAccountActivity : AppCompatActivity() {

    private var userService = UserServiceProvider.getUserService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
    }

    /* val btnSwitchToLogin = findViewById<Button>(R.id.switch_to_login_btn)
    btnSwitchToLogin.setOnClickListener {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    } */
}