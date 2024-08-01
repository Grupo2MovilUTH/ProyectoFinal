package com.example.codeseasy.com.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Perfil extends AppCompatActivity {

    TextInputEditText editTextName, editTextEmail, editTextPassword;
    String name, email, password;
    TextView textViewError;
    Button buttonUpdate, buttonChangePassword;
    ProgressBar progressBar;

    SharedPreferences sharedPreferences;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        editTextName = findViewById(R.id.name);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        textViewError = findViewById(R.id.error);
        buttonUpdate = findViewById(R.id.update);
        buttonChangePassword = findViewById(R.id.changePassword);
        progressBar = findViewById(R.id.loading);

        sharedPreferences = getSharedPreferences("CustomerApp", MODE_PRIVATE);

        // Load user data from SharedPreferences
        userId = sharedPreferences.getString("userId", "");
        name = sharedPreferences.getString("name", "");
        email = sharedPreferences.getString("email", "");

        editTextName.setText(name);
        editTextEmail.setText(email);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editTextName.getText().toString().trim();
                email = editTextEmail.getText().toString().trim();
                password = editTextPassword.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Nombre y correo no pueden estar vacíos", Toast.LENGTH_LONG).show();
                    return;
                }

                String url = "http://137.184.153.254/admin/updateRegister.php";

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Asegúrate de que la respuesta sea válida y la actualización sea correcta
                                if (response.contains("User was updated.")) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("name", name);
                                    editor.putString("email", email);
                                    editor.apply();

                                    Toast.makeText(getApplicationContext(), "Datos actualizados con éxito", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error: " + response, Toast.LENGTH_LONG).show();
                                }
                                progressBar.setVisibility(View.GONE); // Ocultar el progressBar
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        String errorMessage = "Error en la actualización: ";
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            errorMessage += new String(error.networkResponse.data);
                        } else {
                            errorMessage += error.getMessage();
                        }
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE); // Ocultar el progressBar
                    }

                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        return null; // No necesitas implementar esto si usas el método `getBody` para enviar JSON
                    }

                    @Override
                    public byte[] getBody() {
                        try {
                            // Crea un objeto JSON con los parámetros
                            JSONObject jsonBody = new JSONObject();
                            jsonBody.put("email", email);
                            jsonBody.put("name", name);
                            if (!password.isEmpty()) {
                                jsonBody.put("password", password);
                            }
                            return jsonBody.toString().getBytes("utf-8");
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                };
                progressBar.setVisibility(View.VISIBLE); // Mostrar el progressBar
                queue.add(stringRequest);
            }
        });

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = editTextPassword.getText().toString().trim();
                if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Por favor, ingrese una nueva contraseña", Toast.LENGTH_LONG).show();
                    return;
                }
                sendPasswordResetEmail();
            }
        });
    }

    private void sendPasswordResetEmail() {
        String url = "http://137.184.153.254/admin/updateRegister.php";

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("User was updated.")) {
                            Toast.makeText(getApplicationContext(), "Correo de verificación enviado", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error: " + response, Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error en el envío del correo: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Deja este método vacío, ya que estamos usando getBody() para enviar JSON
                return null;
            }

            @Override
            public byte[] getBody() {
                try {
                    // Crea un objeto JSON con los parámetros
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("email", email);
                    jsonBody.put("password", password);
                    jsonBody.put("name", name);
                    return jsonBody.toString().getBytes("utf-8");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        queue.add(stringRequest);
    }
}
