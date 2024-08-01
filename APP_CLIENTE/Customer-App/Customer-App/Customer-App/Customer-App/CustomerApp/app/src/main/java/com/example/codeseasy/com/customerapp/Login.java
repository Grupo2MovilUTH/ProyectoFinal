package com.example.codeseasy.com.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    String name, email, password;
    TextView textViewError, textViewRegister;
    Button buttonSubmit;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("CustomerApp", MODE_PRIVATE);
        if (sharedPreferences.getString("login", "false").equals("true")) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonSubmit = findViewById(R.id.submit);
        progressBar = findViewById(R.id.loading);
        textViewError = findViewById(R.id.error);
        textViewRegister = findViewById(R.id.registerNow);
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
                finish();
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                textViewError.setVisibility(View.GONE);
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://137.184.153.254/fooddeliveryapp/public/index.php/api/users/login";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");
                                    String message = jsonObject.getString("message");
                                    if (status.equals("success")) {
                                        name = jsonObject.getString("name");
                                        email = jsonObject.getString("email");
                                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                        myEdit.putString("login", "true");
                                        myEdit.putString("name", name);
                                        myEdit.putString("email", email);
                                        myEdit.apply();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        textViewError.setText(message);
                                        textViewError.setVisibility(View.VISIBLE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        textViewError.setText(error.getLocalizedMessage());
                        textViewError.setVisibility(View.VISIBLE);
                        error.printStackTrace();
                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("email", email);
                        paramV.put("password", password);
                        return paramV;
                    }
                };
                queue.add(stringRequest);

            }
        });
    }
}