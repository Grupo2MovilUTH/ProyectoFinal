package com.example.codeseasy.deliveryboyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView textViewAddress, textViewUser;
    String apiUrl = "http://137.184.153.254/fooddeliveryapp/public/index.php/api/users/delivery?email=";
    SharedPreferences sharedPreferences;
    String order_id, longitude, latitude;
    CardView cardViewDeliveryDetails;
    TextView textViewTitle;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewAddress = findViewById(R.id.new_delivery);
        textViewUser = findViewById(R.id.userDetails);
        sharedPreferences = getSharedPreferences("DeliveryBoyApp", MODE_PRIVATE);
        cardViewDeliveryDetails = findViewById(R.id.deliveryDetails);
        textViewTitle = findViewById(R.id.textDeliveryTitle);
        textViewTitle.setText("Sin entrega pendiente");
        cardViewDeliveryDetails.setVisibility(View.GONE);

        if (sharedPreferences.getString("login", "false").equals("false")) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        checkPermissions();
        GPSUtils gpsUtils = new GPSUtils(this);
        gpsUtils.statusCheck();
        textViewUser.setText(sharedPreferences.getString("name", "") + "\n" +
                sharedPreferences.getString("email", ""));
        fetchData();

        Button buttonLocation = findViewById(R.id.startDelivery);
        Button buttonSuccess = findViewById(R.id.btn_success);
        Button buttonFailed = findViewById(R.id.btn_failed);
        Button buttonLogout = findViewById(R.id.logoutButton);

        buttonSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markOrderStatus("http://137.184.153.254/fooddeliveryapp/public/index.php/api/users/delivery/success?order_id=" + order_id,
                        "success");
            }
        });

        buttonFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markOrderStatus("http://137.184.153.254/fooddeliveryapp/public/index.php/api/users/delivery/failed?order_id=" + order_id,
                        "failed");
            }
        });

        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewLocation.class);
                intent.putExtra("lat", latitude);
                intent.putExtra("long", longitude);
                startActivity(intent);
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Borra la sesión y redirige al Login
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("login", "false");
                editor.apply();

                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void checkPermissions() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    public void markOrderStatus(String url, String status) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            Toast.makeText(MainActivity.this, "Marcado: Entrega " + status, Toast.LENGTH_SHORT).show();
                            cardViewDeliveryDetails.setVisibility(View.GONE);
                            textViewTitle.setText("Sin entrega pendiente");
                        } else {
                            Toast.makeText(MainActivity.this, "Operacion fallida", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);
    }

    public void parseJSON(String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);
            if (jsonArray.length() > 0) {
                cardViewDeliveryDetails.setVisibility(View.VISIBLE);
                textViewTitle.setText("Se encontraron nuevos detalles de entrega");
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject stu = jsonArray.getJSONObject(i);
                String address = stu.getString("destination_address");
                order_id = stu.getString("id");
                latitude = stu.getString("destination_lat");
                longitude = stu.getString("destination_lon");
                textViewAddress.setText("Address: " + address);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void fetchData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl + sharedPreferences.getString("email", ""),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJSON(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}
