package org.mcxa.lightingcontrol

import android.content.Context
import android.util.Log

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.OkHttpResponseListener
import com.getpebble.android.kit.PebbleKit
import com.getpebble.android.kit.util.PebbleDictionary

import org.json.JSONException
import org.json.JSONObject

import java.util.UUID

import okhttp3.Response

class PebbleReceiver : PebbleKit.PebbleDataReceiver(UUID.fromString("e004deef-41eb-4cb8-99de-64c1fd2a8666")) {

    override fun receiveData(context: Context, transactionId: Int, data: PebbleDictionary) {
        val light = data.getInteger(10)!!.toInt()
        val state = data.getInteger(11)!!.toInt()

        callService(lightEntities[light], "switch", lightServices[state])

        // A new AppMessage was received, tell Pebble
        PebbleKit.sendAckToPebble(context, transactionId)
    }

    companion object {
        final val lightEntities = arrayOf("switch.office_lamp", "switch.office_lights",
                "switch.living_room_lamp", "switch.humidifier", "switch.bedroom_lamp")
        final val lightServices = arrayOf("turn_off", "turn_on")
    }
}
