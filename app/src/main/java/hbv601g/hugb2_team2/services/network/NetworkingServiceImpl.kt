package hbv601g.hugb2_team2.services.network

import android.content.Context
import com.android.volley.RequestQueue
import org.json.JSONObject;
import com.android.volley.toolbox.Volley;


class NetworkingServiceImpl public constructor(context: Context) : NetworkingService {

    private final var BASE_URL = "http://10.0.2.2:8080/"

    private var mContext: Context = context
        get() {
            return mContext
        }
    private var mQueue: RequestQueue
        get() {
            return mQueue
        }


    init {
        this.mQueue = Volley.newRequestQueue(mContext.applicationContext)
    }

    override suspend fun getRequest(reqURL: String): JSONObject {
        TODO("Not yet implemented")
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