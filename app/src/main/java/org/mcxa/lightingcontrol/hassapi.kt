package org.mcxa.lightingcontrol

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONException
import org.json.JSONObject

private val API_KEY = ""

fun callService(entityId: String, serviceDomain: String, serviceName: String,
                        successCallback: (JSONObject?) -> Unit = {}) {
    val requestBody = JSONObject()
    try {
        requestBody.put("entity_id", entityId)
    } catch (e: JSONException) {
        Log.d("MAIN", "OH NO, there was a JSON exception. This clearly needs a try block!")
    }

    AndroidNetworking.post("https://ha.mcxa.org/api/services/${serviceDomain}/${serviceName}")
            .addHeaders("Authorization", "Bearer ${API_KEY}")
            .addHeaders("Content-Type", "application/json")
            .addJSONObjectBody(requestBody)
            .build().getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    successCallback.invoke(response)
                    Log.d("MAIN", "request succeeded")
                }

                override fun onError(anError: ANError) {
                    Log.d("MAIN", "request failed")
                }
            })
}

fun getStatus(entityId: String, callback: (JSONObject?) -> Unit) {
    AndroidNetworking.get("https://ha.mcxa.org/api/states/${entityId}")
            .addHeaders("Authorization", "Bearer ${API_KEY}")
            .addHeaders("Content-Type", "application/json")
            .build().getAsJSONObject(object: JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    callback.invoke(response)
                }

                override fun onError(anError: ANError?) {
                    Log.d("MAIN", "Status request for ${entityId} failed")
                }
            } )
}