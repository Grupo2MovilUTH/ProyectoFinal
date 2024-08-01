package com.example.codeseasy.com.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    TextInputEditText editTextName, editTextEmail, editTextPassword;
    String name, email, password;
    TextView textViewError, textViewLogin;
    Button buttonSubmit;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        editTextName = findViewById(R.id.name);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        textViewError = findViewById(R.id.error);
        textViewLogin = findViewById(R.id.loginNow);
        buttonSubmit = findViewById(R.id.submit);
        progressBar = findViewById(R.id.loading);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editTextName.getText().toString();
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                String url = "http://137.184.153.254/fooddeliveryapp/public/index.php/api/users/register";

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("success")) {
                                    Toast.makeText(getApplicationContext(), "Cuenta Creada con exito", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } else
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("name", name);
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