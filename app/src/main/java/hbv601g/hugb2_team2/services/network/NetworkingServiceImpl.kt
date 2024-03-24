package hbv601g.hugb2_team2.services.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonElement
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject;
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class NetworkingServiceImpl(applicationContext: Context) : NetworkingService {

    private var BASE_URL = "http://10.0.2.2:8080/api"

    private val context = applicationContext
    var apiRequestQueue: RequestQueue = Volley.newRequestQueue(context)

    override suspend fun getRequest(reqURL: String): JsonElement =
        suspendCancellableCoroutine { continuation ->
            val url = "$BASE_URL$reqURL"
            val jsonElementRequest = JsonElementRequest(
                Request.Method.GET, url,
                { response ->
                    continuation.resume(response)
                },
                { error ->
                    continuation.resumeWithException(Exception(error.toString()))
                }
            )
            apiRequestQueue.add(jsonElementRequest)
        }

    override suspend fun postRequest(reqURL: String, data: String): JSONObject =
        suspendCancellableCoroutine { continuation ->
            val url = "$BASE_URL$reqURL"
            val jsonObject = JSONObject(data)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, url, jsonObject,
                { response ->
                    continuation.resume(response)
                },
                { error ->
                    continuation.resumeWithException(Exception(error.toString()))
                }
            )
            apiRequestQueue.add(jsonObjectRequest)
        }


    override suspend fun putRequest(reqURL: String, data: String): JSONObject =
        suspendCancellableCoroutine { continuation ->
            val url = "$BASE_URL$reqURL"
            val jsonObject = JSONObject(data)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.PUT, url, jsonObject,
                { response ->
                    continuation.resume(response)
                },
                { error ->
                    continuation.resumeWithException(Exception(error.toString()))
                }
            )
            apiRequestQueue.add(jsonObjectRequest)
        }

    override suspend fun patchRequest(reqURL: String, data: JSONObject): JSONObject {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRequest(reqURL: String): JSONObject =
        suspendCancellableCoroutine { continuation ->
            val url = "$BASE_URL$reqURL"
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.DELETE, url, null,
                { response ->
                    continuation.resume(response)
                },
                { error ->
                    continuation.resumeWithException(Exception(error.toString()))
                }
            )
            apiRequestQueue.add(jsonObjectRequest)
        }

}