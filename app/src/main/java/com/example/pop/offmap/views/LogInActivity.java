package com.example.pop.offmap.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.pop.offmap.R;
import com.example.pop.offmap.controller.appconfig.AppConfig;
import com.example.pop.offmap.controller.appconfig.AppController;
import com.example.pop.offmap.controller.network.NetConnectionAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {

    private static final String TAG = "LogInActivity";
    EditText edt_userName, edt_password;
    Button btn_login, btn_exit;
    Context mContext = LogInActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initializeWidget();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userVerification(edt_userName.getText().toString(), edt_password.getText().toString());
            }
        });
    }

    /**
     * @since version 1.0
     * <p>
     * a method to initialize the widgets
     * </p>
     */
    private void initializeWidget() {
        edt_userName = (EditText) findViewById(R.id.edt_userName);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_login = (Button) findViewById(R.id.btnLogin);
        btn_exit = (Button) findViewById(R.id.btnExit);
    }

    /**
     * @param userName
     * @param password
     */

    public void userVerification(String userName, String password) {
        //userName = edt_userName.getText().toString();
        //password = edt_password.getText().toString();

        if (userName.equals("") || password.equals("")) {
            Toast.makeText(mContext, "EMPTY USER NAME AND PASSWORD", Toast.LENGTH_SHORT).show();

        } else {
            // todo check  local db match if no than check inter net
            NetConnectionAdapter netCD = new NetConnectionAdapter(mContext);
            boolean isNetAvailable = netCD.isConnectingToInternet();

            if (isNetAvailable) {


                checkLogIn();
                //    gotoHomePage();

            } else {
                // todo: show dialog box

            }

        }

    }//end of method

    private void gotoHomePage() {

        finish();
        Intent i = new Intent(mContext, HomeActivity.class);
        startActivity(i);

    }


    private void checkLogIn() {
        String tag_sring = "req_log_in";

        StringRequest strRequest = new StringRequest(Request.Method.POST,
                AppConfig.APP_LINK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jObj = new JSONObject(response);

                            String error = jObj.getString("Error");

//                            Log.d(TAG, "error :" + error);

                            if (error.equals("0")) {

                                if (!jObj.isNull("staff_info")) {
                                    JSONArray staffs_info = jObj.getJSONArray("staff_info");
                                    int size = staffs_info.length();
                                    for (int i = 0; i < size; i++) {

                                        JSONObject staff_info = (JSONObject) staffs_info.get(i);
                                        String UserID = staff_info.getString("UserID");
                                        String Name = staff_info.getString("Name");
                                        String Email = staff_info.getString("Email");
                                        String EntryBy = staff_info.getString("EntryBy");
                                        String EntryDate = staff_info.getString("EntryDate");
                                        Log.d(TAG, "UserID:" + UserID + " Name:" + Name + " Email:" + Email + " EntryBy:" + EntryBy + " EntryDate:" + EntryDate);
                                    }
                                }


                            } else {
//                                Toast.makeText(mContext, "WRONG USER NAME AND PASSWORD", Toast.LENGTH_SHORT).show();
                            }

                            Log.d(TAG, "object :" + jObj.toString());
                        } catch (Exception e) {
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Login Error: " + error + " Stack Tracr = "
                                + error.getStackTrace() + " Detail = " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<String, String>();
                param.put("task", "log_in_check");
                return param;
            }
        };

        AppController.getInstance().addToRequestQueue(strRequest, tag_sring);
    }

}
