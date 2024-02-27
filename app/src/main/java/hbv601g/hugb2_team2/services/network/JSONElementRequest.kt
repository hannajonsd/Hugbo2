package hbv601g.hugb2_team2.services.network

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

class JsonElementRequest(
    method: Int,
    url: String,
    private val listener: Response.Listener<JsonElement>?,
    errorListener: Response.ErrorListener
) : Request<JsonElement>(method, url, errorListener) {

    override fun deliverResponse(response: JsonElement): Unit = listener?.onResponse(response)!!

    override fun parseNetworkResponse(response: NetworkResponse): Response<JsonElement> {
        return try {
            val json = String(
                response.data,
                Charset.forName(HttpHeaderParser.parseCharset(response.headers, "UTF-8"))
            )
            Response.success(
                Gson().fromJson<JsonElement>(json, object : TypeToken<JsonElement>() {}.type),
                HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        } catch (jsonException: JsonSyntaxException) {
            Response.error(ParseError(jsonException))
        }
    }
}