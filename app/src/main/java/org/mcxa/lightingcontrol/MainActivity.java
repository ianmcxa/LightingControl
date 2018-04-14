package org.mcxa.lightingcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Button;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static SparseArray<int[]> codes = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        codes.put(R.id.lchlon, new int[]{0,1});
        codes.put(R.id.lchloff, new int[]{0,0});
        codes.put(R.id.kchloff, new int[]{1,0});
        codes.put(R.id.kchlon, new int[]{1,1});
        codes.put(R.id.lvloff, new int[]{2,0});
        codes.put(R.id.lvlon, new int[]{2,1});
        codes.put(R.id.bchloff, new int[]{3,0});
        codes.put(R.id.bchlon, new int[]{3,1});
        codes.put(R.id.bloff, new int[]{4,0});
        codes.put(R.id.blon, new int[]{4,1});
    }

    @OnClick({ R.id.lchlon, R.id.lchloff, R.id.kchlon, R.id.kchloff,
            R.id.lvlon, R.id.lvloff, R.id.bchloff, R.id.bchlon,
            R.id.blon, R.id.bloff})
    public void sendCode(Button button) {
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("light", codes.get(button.getId())[0]);
            requestBody.put("state", codes.get(button.getId())[1]);
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
    }
}
