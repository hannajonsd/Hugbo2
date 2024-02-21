package hbv601g.hugb2_team2.services.implementation

import android.content.Context
import hbv601g.hugb2_team2.entities.User
import hbv601g.hugb2_team2.services.UserService
import hbv601g.hugb2_team2.services.network.NetworkingServiceProvider

class UserServiceImpl(context: Context) : UserService {
    private lateinit var context: Context
    private var networkingService = NetworkingServiceProvider.getNetworkingService(context)

    override suspend fun createUser(user: User): User {
        TODO("Not yet implemented")
    }

    override suspend fun editUser(user: User): User {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(user: User): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getUserByUsername(username: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(id: Long): User {
        TODO("Not yet implemented")
    }

    override suspend fun login(username: String, password: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun logout(user: User): Boolean {
        TODO("Not yet implemented")
    }

    override fun setContext(context: Context) {
        this.context = context
    }
}