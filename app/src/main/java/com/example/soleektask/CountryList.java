package com.example.soleektask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CountryList extends AppCompatActivity {
    Context context;
    ListView CountryListView;
    String response;
    Button Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this.context;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);
        CountryListView = (ListView)findViewById(R.id.CountryListView);
        Logout = findViewById(R.id.Button_Country_Logout);
        ArrayList<String> CountryList;
        getData();

        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        response = m.getString("response", "response");
        Log.e("someOtherrrrwwwwr", response);
        CountryList=new ArrayList<>();
        {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray array = obj.getJSONArray("result");
                for(int i = 0 ; i < array.length() ; i++){
                    CountryList.add(array.getJSONObject(i).getString("name"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter ListAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,CountryList);

        CountryListView.setAdapter(ListAdapter);


        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CountryList.this,SignIn.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private void sharedResponseString(String key,String value) {
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = m.edit();
        editor.putString(key, value);
        editor.commit();
    };

    private void getData(){
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.printful.com/countries",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("someOtherrrrr", response);
                            sharedResponseString("response",response);
                        } catch (Exception e) {
                            Log.e("someOther", response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
        };


        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 20 * 1000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 20 * 1000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                error.printStackTrace();
                //commonCallBackInterface.onSuccess("ServicePl_VolleyError", "VolleyError");
            }
        });

        VolleySingelton volleySingleton = VolleySingelton.getInstance(this);
        volleySingleton.getRequestQueue().add(stringRequest);
    }

}
