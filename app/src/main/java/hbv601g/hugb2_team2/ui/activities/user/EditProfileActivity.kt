package hbv601g.hugb2_team2.ui.activities.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hbv601g.hugb2_team2.R
import hbv601g.hugb2_team2.services.providers.UserServiceProvider

class EditProfileActivity : AppCompatActivity() {

    private var userService = UserServiceProvider.getUserService(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
    }
}