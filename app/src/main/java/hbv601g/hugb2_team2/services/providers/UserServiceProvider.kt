package hbv601g.hugb2_team2.services.providers

import android.content.Context
import hbv601g.hugb2_team2.services.UserService
import hbv601g.hugb2_team2.services.implementation.UserServiceImpl

object UserServiceProvider {
    private var userService: UserService? = null

    fun getUserService(context: Context): UserService {
        return userService ?: synchronized(this) {
            userService ?: createUserService(context).also { userService = it }
        }
    }

    private fun createUserService(context: Context): UserService {
        val service = UserServiceImpl(context)
        service.setContext(context)
        return service
    }
}