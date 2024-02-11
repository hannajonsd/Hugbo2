package hbv601g.hugb2_team2.services.network

import org.json.JSONObject

interface NetworkingService {

    suspend fun getRequest(reqURL: String): JSONObject
    suspend fun postRequest(reqURL: String, data: JSONObject): JSONObject
    suspend fun deleteRequest(reqURL: String): JSONObject
}