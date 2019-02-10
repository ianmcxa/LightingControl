package org.mcxa.lightingcontrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.androidnetworking.interfaces.OkHttpResponseListener
import com.google.gson.JsonObject

import org.json.JSONException
import org.json.JSONObject

import okhttp3.Response

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        office_lamp_on.setOnClickListener {
            callService("switch.office_lamp","switch", "turn_on")
        }
        office_lamp_off.setOnClickListener {
            callService("switch.office_lamp", "switch","turn_off")
        }

        office_lights_on.setOnClickListener {
            callService("switch.office_lights", "switch","turn_on")
        }
        office_lights_off.setOnClickListener {
            callService("switch.office_lights", "switch","turn_off")
        }

        living_room_lamp_on.setOnClickListener {
            callService("switch.living_room_lamp", "switch","turn_on")
        }
        living_room_lamp_off.setOnClickListener {
            callService("switch.living_room_lamp", "switch","turn_off")
        }

        humidifier_on.setOnClickListener {
            callService("switch.humidifier", "switch","turn_on")
        }
        humidifier_off.setOnClickListener {
            callService("switch.humidifier", "switch","turn_off")
        }

        bedroom_lamp_on.setOnClickListener {
            callService("switch.bedroom_lamp", "switch","turn_on")
        }
        bedroom_lamp_off.setOnClickListener {
            callService("switch.bedroom_lamp", "switch","turn_off")
        }

        front_door_lock_lock.setOnClickListener {
            callService("lock.kwikset_unknown_type_0003_id_0446_locked",
                    "lock","lock") {
                val lockStatus = (it?.get("attributes") as? JSONObject)?.get("lock_status")
                front_door_lock_status.text = "Lock Status: ${lockStatus}"
            }
        }
        front_door_lock_unlock.setOnClickListener {
            callService("lock.kwikset_unknown_type_0003_id_0446_locked", "lock","unlock")
        }
    }

    override fun onResume() {
        super.onResume()

        getStatus("lock.kwikset_unknown_type_0003_id_0446_locked") {
            val lockStatus = (it?.get("attributes") as? JSONObject)?.get("lock_status")
            front_door_lock_status.text = "Lock Status: ${lockStatus}"
        }
    }
}
