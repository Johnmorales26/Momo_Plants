package com.johndev.momoplantsparent.common.fmc

import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.johndev.momoplantsparent.MomoParentApp
import com.johndev.momoplantsparent.common.utils.Constants.MOMO_PLANTS_RS
import com.johndev.momoplantsparent.common.utils.Constants.PARAM_MESSAGE
import com.johndev.momoplantsparent.common.utils.Constants.PARAM_METHOD
import com.johndev.momoplantsparent.common.utils.Constants.PARAM_SUCCESS
import com.johndev.momoplantsparent.common.utils.Constants.PARAM_TITLE
import com.johndev.momoplantsparent.common.utils.Constants.PARAM_TOKENS
import com.johndev.momoplantsparent.common.utils.Constants.SEND_NOTIFICATION
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject
import kotlin.jvm.Throws

class NotificationRS @Inject constructor() {

    fun sendNotification(title: String, message: String, tokens: String) {
        val params = JSONObject()
        params.apply {
            put(PARAM_METHOD, SEND_NOTIFICATION)
            put(PARAM_TITLE, title)
            put(PARAM_MESSAGE, message)
            put(PARAM_TOKENS, tokens)
        }
        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(
                Method.POST,
                MOMO_PLANTS_RS,
                params,
                Response.Listener { response ->
                    try {
                        val success = response.getInt(PARAM_SUCCESS)
                        Log.i("Volley success", success.toString())
                        Log.i("Response", response.toString())
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Log.e("Volley exception", e.localizedMessage)
                    }
                },
                Response.ErrorListener { error ->
                    if (error.localizedMessage != null) {
                        Log.e("Volley error", error.localizedMessage)
                    }
                }) {

                @Throws(AuthFailureError::class)
                override fun getHeaders(): MutableMap<String, String> {
                    val paramsHeaders = HashMap<String, String>()
                    paramsHeaders["Content-Type"] = "application/json; charset=utf-8"
                    return super.getHeaders()
                }

            }
        MomoParentApp.volleyHelper.addToRequestQueue(jsonObjectRequest)
    }

}