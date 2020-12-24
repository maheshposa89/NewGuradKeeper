package com.wk.guestpass.guard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;



public class SplashActivity extends AppCompatActivity {

    StringRequest stringRequest;
    RequestQueue mRequestQueue;
    public static final String TAG = "STag";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);

        imageView=findViewById(R.id.imageView);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },1500);
        //splash();
    }

    public void splash() {

        String url = Config.splashscreen;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);

                            String status = j.getString("status");
                            if (status.equals("1")) {

                                Glide.with(getApplicationContext()).load( j.getString("image_path"))
                                        .dontAnimate()
                                        .into(imageView);

                                Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                },3000);
                            }
                        } catch (JSONException e) {
                            Log.e("TAG", "Something Went Wrong");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        finish();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("apikey", "d29985af97d29a80e40cd81016d939af");
                return headers;
            }
        };
        stringRequest.setTag(TAG);
        mRequestQueue.add(stringRequest);
    }
}
