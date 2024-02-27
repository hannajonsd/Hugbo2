package hbv601g.hugb2_team2.services.network

import com.google.gson.JsonElement
import org.json.JSONObject

interface NetworkingService {

    suspend fun getRequest(reqURL: String): JsonElement
    suspend fun postRequest(reqURL: String, data: String): JSONObject
    suspend fun putRequest(reqURL: String, data: JSONObject): JSONObject
    suspend fun patchRequest(reqURL: String, data: JSONObject): JSONObject
    suspend fun deleteRequest(reqURL: String): JSONObject

}