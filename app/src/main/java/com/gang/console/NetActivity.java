package com.gang.console;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fenghuo.utils.hash.CipherUtils2;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gang on 16-9-11.
 */
public class NetActivity extends AppCompatActivity implements View.OnClickListener {

    private static final boolean DEBUG = true;
    private static final String TAG = "test.NetActivity";

    private TextView textView;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_net);

        findViewById(R.id.start_get).setOnClickListener(this);
        findViewById(R.id.start_post).setOnClickListener(this);

        textView = (TextView) findViewById(R.id.text_show);

        requestQueue = Volley.newRequestQueue(NetActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_get: {

                break;
            }
            case R.id.start_post: {
                String url = "http://kd.zengxiaojiangsb.com:8090/vcheck";
                String body = "{\"appno\":20,\"appid\":\"AP14220716172841570000\"}";
                doVolleyPost(url, body);
                break;
            }
        }
    }

    private void doApachPost(String url, String param) {
        final String bodyEn = CipherUtils2.encrypt(param);
        if (DEBUG) {
            Log.d(TAG, "start post: url=" + url);
            Log.d(TAG, "start post: body=" + url);
            Log.d(TAG, "start post: body_en=" + bodyEn);
        }
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setEntity(new StringEntity(bodyEn, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        defaultHttpClient.getParams().setParameter("http.connection.timeout", Integer.valueOf(10000));
        defaultHttpClient.getParams().setParameter("http.socket.timeout", Integer.valueOf(10000));
        try {
            defaultHttpClient.execute(httpPost);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doVolleyPost(String url, String param) {


        final String bodyEn = CipherUtils2.encrypt(param);
        if (DEBUG) {
            Log.d(TAG, "start post: url=" + url);
            Log.d(TAG, "start post: body=" + url);
            Log.d(TAG, "start post: body_en=" + bodyEn);
        }
        StringRequest request = new MStringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String decrypt = CipherUtils2.decrypt(response);
                if (DEBUG) {
                    Log.d(TAG, "onResponse: " + decrypt);
                }
                textView.setText(decrypt);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (DEBUG) {
                    Log.e(TAG, "onResponse: ", error);
                }
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return bodyEn.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Charset", "UTF-8");
                map.put("Content-Type", "text/plain");
                return map;
            }
        };


        requestQueue.add(request);
    }
}
