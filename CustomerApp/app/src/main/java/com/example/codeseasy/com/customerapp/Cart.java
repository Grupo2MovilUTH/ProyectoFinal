package com.example.codeseasy.com.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class Cart extends AppCompatActivity {

    TextView textViewCartData, textViewDisDur;
    SharedPreferences sharedPreferences;
    Button buttonConfirm, buttonRemove;

    int pricePerKM = 5;
    String urlConfirm =
            "http://137.184.153.254/fooddeliveryapp/public/index.php/api/users/cart/confirm?user_email=";

    String urlRemove =
            "http://137.184.153.254/fooddeliveryapp/public/index.php/api/users/cart/clear?user_email=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        textViewCartData = findViewById(R.id.textCartData);
        textViewDisDur = findViewById(R.id.textDisDur);
        sharedPreferences = getSharedPreferences("CustomerApp", MODE_PRIVATE);
        buttonConfirm = findViewById(R.id.btnConfirmOrder);
        buttonRemove = findViewById(R.id.btnClearCart);

        // Obtener el correo electrónico del usuario desde SharedPreferences
        String userEmail = sharedPreferences.getString("email", "");
        if (!userEmail.isEmpty()) {
            fetchData(userEmail);
        } else {
            Toast.makeText(getApplicationContext(), "No se ha encontrado el correo del usuario", Toast.LENGTH_SHORT).show();
        }

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest(view, urlConfirm + userEmail);
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest(view, urlRemove + userEmail);
            }
        });
    }

    public void sendRequest(View v, String apiUrl) {
        Log.e("url", apiUrl);
        v.setEnabled(false);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                v.setEnabled(true);
                if (response.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Operación Exitosa", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Operación Fallida", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                v.setEnabled(true);
                if (error.networkResponse != null) {
                    Log.e("Error Response code", String.valueOf(error.networkResponse.statusCode));
                }
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error en la solicitud", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    @SuppressLint("SetTextI18n")
    public void parseJSON(String response) {
        try {
            // Extraer solo el JSON, eliminando el prefijo "Conectado a la base de datos"
            String jsonResponse = response.replaceFirst("Conectado a la base de datos", "").trim();

            // Crear el objeto JSONObject
            JSONObject jsonObject = new JSONObject(jsonResponse);

            // Procesar el JSON para obtener los datos del carrito
            JSONArray cartArray = jsonObject.getJSONArray("cart");
            StringBuilder cartData = new StringBuilder();
            double totalPrice = 0.0;
            for (int i = 0; i < cartArray.length(); i++) {
                JSONObject cartItem = cartArray.getJSONObject(i);
                String name = cartItem.getString("name");
                double price = cartItem.getDouble("price"); // Cambiado a double para sumar los precios

                // Añadir la información del artículo al StringBuilder
                cartData.append("Nombre: ").append(name)
                        .append("\nPrecio: L").append(price)
                        .append("\n\n");

                // Sumar el precio del artículo al precio total
                totalPrice += price;
            }

            // Mostrar la información del carrito en el TextView
            textViewCartData.setText(cartData.toString());

            int distance = jsonObject.getInt("distance");
            int duration = jsonObject.getInt("duration");

            // Mostrar la distancia y duración en el TextView
            textViewDisDur.setText("Distancia: " + distance + "m\nDuración: " + duration + "s\nPrecio Total: L" + totalPrice);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSON Parsing Error", e.getMessage());
        }
    }

    public void fetchData(String userEmail) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://137.184.153.254/admin/fetch_cart.php?user_email=" + userEmail + "&distance=1000";
        Log.d("fetchData", "Fetching data from URL: " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("API Response", response); // Imprime la respuesta
                        parseJSON(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if (error.networkResponse != null) {
                    Log.e("Error Response code", String.valueOf(error.networkResponse.statusCode));
                }
                Toast.makeText(getApplicationContext(), "Error al obtener los datos del carrito", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}
