package hbv601g.hugb2_team2.session

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = pref.edit()

    companion object {
        private const val PREF_NAME = "UserSession"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USER_ID = "userId"
        private const val KEY_IS_ADMIN = "isAdmin"
        private const val KEY_USERNAME = "username"
        private const val KEY_FIRST_NAME = "firstName"
        private const val KEY_LAST_NAME = "lastName"
        private const val KEY_EMAIL = "email"
    }

    fun createSession(userId: Long, isAdmin: Boolean, username: String, firstName: String, lastName: String, email: String) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putLong(KEY_USER_ID, userId)
        editor.putBoolean(KEY_IS_ADMIN, isAdmin)
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_FIRST_NAME, firstName)
        editor.putString(KEY_LAST_NAME, lastName)
        editor.putString(KEY_EMAIL, email)
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