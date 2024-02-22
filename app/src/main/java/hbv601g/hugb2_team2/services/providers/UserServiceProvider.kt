package hbv601g.hugb2_team2.services.providers

import android.content.Context
import hbv601g.hugb2_team2.services.UserService
import hbv601g.hugb2_team2.services.implementation.UserServiceImpl

object UserServiceProvider {
    private lateinit var userService: UserService

    fun getUserService() : UserService {
        if (!::userService.isInitialized) {
            userService = UserServiceImpl()
        }
        return userService
    }


}