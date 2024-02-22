package hbv601g.hugb2_team2.services

import android.content.Context
import hbv601g.hugb2_team2.entities.User

interface UserService {
    suspend fun createUser(user: User): User
    suspend fun editUser(user: User): User
    suspend fun deleteUser(user: User): Boolean
    suspend fun getUserByUsername(username: String): User
    suspend fun getUserById(id: Long): User
    suspend fun login(username: String, password: String): User
    suspend fun logout(user: User): Boolean
}