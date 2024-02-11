package hbv601g.hugb2_team2.services.providers

import hbv601g.hugb2_team2.services.UserService
import hbv601g.hugb2_team2.services.implementation.UserServiceImpl

object UserServiceProvider {

    private var userService: UserService? = null

    fun getUserService(): UserService {
        if (userService == null) {
            userService = UserServiceImpl()
        }
        return userService!!
    }
}