package org.mcxa.lightingcontrol;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import okhttp3.Response;

public class PebbleReceiver extends PebbleKit.PebbleDataReceiver {

    public PebbleReceiver() {
        super(UUID.fromString("e004deef-41eb-4cb8-99de-64c1fd2a8666"));
    }

    @Override
    public void receiveData(Context context, int transactionId, PebbleDictionary data) {
        int light = data.getInteger(10).intValue();
        int state = data.getInteger(11).intValue();

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("light", light);
            requestBody.put("state", state);
        } catch (JSONException e) {
            Log.d("MAIN", "OH NO, there was a JSON exception. This clearly needs a try block!");
        }

        AndroidNetworking.post("http://[2606:a000:1127:4082:ba27:ebff:fe59:23c]/update")
                .addJSONObjectBody(requestBody)
                .build().getAsOkHttpResponse(new OkHttpResponseListener() {
            @Override
            public void onResponse(Response response) {
                Log.d("MAIN", "request succeeded");
            }

            @Override
            public void onError(ANError anError) {
                Log.d("MAIN", "request failed");
            }
        });


        // A new AppMessage was received, tell Pebble
        PebbleKit.sendAckToPebble(context, transactionId);
    }
}
