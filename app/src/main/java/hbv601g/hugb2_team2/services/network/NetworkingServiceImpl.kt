package hbv601g.hugb2_team2.services.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject;
import com.android.volley.toolbox.Volley;


class NetworkingServiceImpl public constructor(context: Context) : NetworkingService {

    private final var BASE_URL = "http://10.0.2.2:8080"

    private lateinit var mContext : Context
    private var mQueue: RequestQueue
        get() {
            return mQueue
        }


    init {
        this.mQueue = Volley.newRequestQueue(mContext.applicationContext)
    }

    override suspend fun getRequest(reqURL: String): JSONObject {
        // send the get request to base_url + reqURL. return the response
        var response = JSONObject()
        val url = BASE_URL + reqURL
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                // Display the first 500 characters of the response string.
                println("Response is: ${response.toString()}")
            },
            { error ->
                println("That didn't work!")
            }
        )
        return response
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

    override fun setContent(context: Context) {
        this.mContext = context
    }


}