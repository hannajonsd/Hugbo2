package hbv601g.hugb2_team2.session

import android.content.Context
import android.content.SharedPreferences
import hbv601g.hugb2_team2.entities.User

class SessionManager(context: Context) {
    private val pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = pref.edit()

    companion object {
        private const val PREF_NAME = "UserSession"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USER_ID = "userId"
        private const val KEY_IS_ADMIN = "admin"
        private const val KEY_USERNAME = "username"
        private const val KEY_FIRST_NAME = "firstName"
        private const val KEY_LAST_NAME = "lastName"
        private const val KEY_EMAIL = "email"
    }

    fun createSession(user: User) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putLong(KEY_USER_ID, user.id ?: -1)
        editor.putBoolean(KEY_IS_ADMIN, user.admin)
        editor.putString(KEY_USERNAME, user.username)
        editor.putString(KEY_FIRST_NAME, user.firstName)
        editor.putString(KEY_LAST_NAME, user.lastName)
        editor.putString(KEY_EMAIL, user.email)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getUserId(): Long {
        return pref.getLong(KEY_USER_ID, -1)
    }

    fun isAdmin(): Boolean {
        return pref.getBoolean(KEY_IS_ADMIN, false)
    }

    fun getUsername(): String? {
        return pref.getString(KEY_USERNAME, null)
    }

    fun getFirstName(): String? {
        return pref.getString(KEY_FIRST_NAME, null)
    }

    fun getLastName(): String? {
        return pref.getString(KEY_LAST_NAME, null)
    }

    fun getLastEmail(): String? {
        return pref.getString(KEY_EMAIL, null)
    }

    fun logout() {
        editor.clear()
        editor.apply()
    }
}