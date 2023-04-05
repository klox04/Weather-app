package com.hcdc.caralos.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText et;
    TextView tv;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        et=findViewById(R.id.et);
        tv=findViewById(R.id.tv);
        image = findViewById(R.id.dc_image);

    }
    public void get(View v){
        String apikey="74950ea51065a79850a10efe895fa0ed";
        String city=et.getText().toString();
        String url="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=74950ea51065a79850a10efe895fa0ed";
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONObject object=response.getJSONObject("main");
                    String temperature=object.getString( "temp");
                    Double temp=Double.parseDouble(temperature)-273.15;
                    tv.setText("Temperature" + temp.toString().substring(0,5) + "C");

                    if (temp > 31) {
                        Toast.makeText(MainActivity.this, "Sunny Day", Toast.LENGTH_LONG).show();
                        image.setImageResource(R.drawable.sun);

                    }
                    else if  (temp > 25 && temp < 30) {
                        Toast.makeText(MainActivity.this, "Cloudy Day", Toast.LENGTH_LONG).show();
                        image.setImageResource(R.drawable.cloud);
                    }
                    else if (temp < 24) {
                        Toast.makeText(MainActivity.this, "Rainy day", Toast.LENGTH_LONG).show();
                        image.setImageResource(R.drawable.rain);

                    }


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Please try Again",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);

    }
}