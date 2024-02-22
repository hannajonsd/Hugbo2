package hbv601g.hugb2_team2.services.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import hbv601g.hugb2_team2.WineNotApp
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject;
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class NetworkingServiceImpl(applicationContext: Context) : NetworkingService {

    private var BASE_URL = "http://10.0.2.2:8080"

    private val context = applicationContext

override suspend fun getRequest(reqURL: String): JSONObject = suspendCancellableCoroutine { continuation ->
    val url = BASE_URL + reqURL
    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, url, null,
        { response ->
            continuation.resume(response)
        },
        { error ->
            continuation.resumeWithException(VolleyError("Request failed"))
        }
    )
    // Add the request to the RequestQueue.
    WineNotApp.requestQueue.add(jsonObjectRequest)
}

    override suspend fun postRequest(reqURL: String, data: JSONObject): JSONObject {
        TODO("Not yet implemented")
    }

    override suspend fun putRequest(reqURL: String, data: JSONObject): JSONObject {
        TODO("Not yet implemented")
    }

    override suspend fun patchRequest(reqURL: String, data: JSONObject): JSONObject {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRequest(reqURL: String): JSONObject {
        TODO("Not yet implemented")
    }

}