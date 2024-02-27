package hbv601g.hugb2_team2.session

import java.security.MessageDigest

class PasswordHash {

    /**
     * Hash-ar lykilorð
     * @param password lykilorðið til að hash-a
     */
    fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val hashedBytes = md.digest(password.toByteArray())
        val sb = StringBuilder()
        for (byte in hashedBytes) {
            sb.append(String.format("%02X", byte))
        }
        return sb.toString()
    }

    /**
     * Check-ar ef lykilorðið er rétt
     * @param inputPassword lykilorðið
     * @param hashedPassword hash-aða lykilorðið
     */
    fun checkPassword(inputPassword: String, hashedPassword: String): Boolean {
        val inputHashed = hashPassword(inputPassword)
        return inputHashed == hashedPassword
    }
}