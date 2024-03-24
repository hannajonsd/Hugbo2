package hbv601g.hugb2_team2.services.implementation

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import hbv601g.hugb2_team2.entities.Establishment
import hbv601g.hugb2_team2.entities.User
import hbv601g.hugb2_team2.services.UserService
import hbv601g.hugb2_team2.services.network.NetworkingServiceProvider

class UserServiceImpl : UserService {

    private var networkingService = NetworkingServiceProvider.getNetworkingService()
    private var baseUrl = "/users"

    override suspend fun createUser(user: User): User? {
        val reqURL = "$baseUrl/create"
        return try {
            val estJson = Gson().toJson(user)
            val response = networkingService.postRequest(reqURL, estJson)
            Log.d("UserServiceImpl", "Response: $response")
            // convert response to establishment
            Gson().fromJson(response.toString(), User::class.java)
        } catch (e: Exception) {
            Log.d("UserServiceImpl", "Exception: $e")
            null
        }
    }

    override suspend fun editUser(user: User): User {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(user: User): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getUserByUsername(username: String): User? {
        val reqURL = "$baseUrl/byUsername?username=$username"
        return try {
            val response = networkingService.getRequest(reqURL)
            Log.d("UserServiceImpl", "Response: $response")
            Gson().fromJson(response.toString(), User::class.java)
        } catch (e: Exception) {
            Log.d("UserServiceImpl", "Exception: $e")
            null
        }
    }

    override suspend fun getUserById(id: Long): User? {
        val reqURL = "$baseUrl/$id"
        return try {
            val response = networkingService.getRequest(reqURL)
            Log.d("UserServiceImpl", "Response: $response")
            Gson().fromJson(response.toString(), User::class.java)
        } catch (e: Exception) {
            Log.d("UserServiceImpl", "Exception: $e")
            null
        }
    }

    /*override suspend fun login(username: String, password: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun logout(user: User): Boolean {
        TODO("Not yet implemented")
    }*/
}