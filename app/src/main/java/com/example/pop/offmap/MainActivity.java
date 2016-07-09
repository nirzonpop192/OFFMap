package com.example.pop.offmap;

import android.drm.DrmManagerClient;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.pop.offmap.controller.appconfig.AppConfig;
import com.example.pop.offmap.controller.appconfig.AppController;
import com.example.pop.offmap.controller.network.NetConnectionAdapter;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button btnLog;
    private TextView tv;
    //private String r_esponse="10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewReference();
        setListener();
    }

    private void setListener() {
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetConnectionAdapter netCD = new NetConnectionAdapter(MainActivity.this);
                boolean isNetAvailable = netCD.isConnectingToInternet();

                if (isNetAvailable) {
                    tv.setText("net is on");

                    checkLogIn();

                } else {
                    tv.setText("No net");
                }
            }
        });
    }

    private void viewReference() {

        btnLog = (Button) findViewById(R.id.btn_LogIn);
        tv = (TextView) findViewById(R.id.tv_net);
    }


    private void checkLogIn() {
        String tag_sring = "req_log_in";

        StringRequest strRequest = new StringRequest(Request.Method.POST,
                AppConfig.APP_LINK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject object= new JSONObject(response);
                        }catch (Exception e){}


                    }
                },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error + " Stack Tracr = "
                        + error.getStackTrace() + " Detail = " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> param=new HashMap<String, String>();
                param.put("task","log_in_check");
                return param;
            }
        };

        AppController.getInstance().addToRequestQueue(strRequest,tag_sring);
    }

}
