package com.example.codeseasy.com.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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

public class ListOrders extends AppCompatActivity {

    TextView textViewOrders;
    SharedPreferences sharedPreferences;
    SQLiteDatabase db;
    SQLiteOpenHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_orders);

        textViewOrders = findViewById(R.id.textOrders);
        sharedPreferences = getSharedPreferences("CustomerApp", MODE_PRIVATE);
        dbHelper = new RatingDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        fetchData("http://137.184.153.254/fooddeliveryapp/public/index.php/api/users/orders/list?email="
                + sharedPreferences.getString("email", ""));
    }

    public void parseJSON(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            LinearLayout linearLayout = findViewById(R.id.linearLayoutOrders);
            linearLayout.removeAllViews(); // Limpiar cualquier vista previa

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject order = jsonArray.getJSONObject(i);
                String id = order.getString("id");
                String created_at = order.getString("created_at");
                String status = order.getString("status");
                String item_details = order.getString("item_details");

                TextView orderDetails = new TextView(this);
                orderDetails.setText("Order placed on: " + created_at + "\nStatus: " + status + "\n");
                linearLayout.addView(orderDetails);

                JSONArray itemsArray = new JSONArray(item_details);
                for (int j = 0; j < itemsArray.length(); j++) {
                    JSONObject item = itemsArray.getJSONObject(j);
                    TextView itemDetails = new TextView(this);
                    itemDetails.setText("Item: " + item.getString("name") + "\nPrice: " + item.getString("price") + "\n");
                    linearLayout.addView(itemDetails);
                }

                RatingAndCommentStatus ratingAndCommentStatus = getRatingAndCommentStatus(id);
                float currentRating = ratingAndCommentStatus.rating;
                String currentComment = ratingAndCommentStatus.comment;
                int commentStatus = ratingAndCommentStatus.commentStatus;

                RatingBar ratingBar = new RatingBar(this);
                ratingBar.setNumStars(5);
                ratingBar.setStepSize(0.5f);
                ratingBar.setRating(currentRating);
                if (commentStatus == 1) {
                    ratingBar.setIsIndicator(true);
                }
                linearLayout.addView(ratingBar);

                TextView commentText = new TextView(this);
                commentText.setText("Comentario: " + currentComment);
                linearLayout.addView(commentText);

                if (commentStatus == 0) {
                    EditText commentInput = new EditText(this);
                    commentInput.setHint("Escribe un comentario...");
                    linearLayout.addView(commentInput);

                    Button submitButton = new Button(this);
                    submitButton.setText("Enviar Calificación y Comentario");
                    submitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveRatingAndComment(id, ratingBar.getRating(), commentInput.getText().toString());
                            ratingBar.setIsIndicator(true);
                            commentInput.setEnabled(false);
                            submitButton.setEnabled(false);
                        }
                    });
                    linearLayout.addView(submitButton);
                }

                TextView space = new TextView(this);
                space.setText("\n\n");
                linearLayout.addView(space);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class RatingAndCommentStatus {
        float rating;
        String comment;
        int commentStatus;

        RatingAndCommentStatus(float rating, String comment, int commentStatus) {
            this.rating = rating;
            this.comment = comment;
            this.commentStatus = commentStatus;
        }
    }

    public void fetchData(String apiUrl) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl,
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
        queue.add(stringRequest);
    }

    private void saveRatingAndComment(String orderId, float rating, String comment) {
        ContentValues values = new ContentValues();
        values.put("order_id", orderId);
        values.put("rating", rating);
        values.put("comment", comment);
        values.put("comment_status", 1); // 1 means comment submitted
        long result = db.insertWithOnConflict("ratings", null, values, SQLiteDatabase.CONFLICT_REPLACE);

        if (result == -1) {
            Toast.makeText(this, "Error al guardar la calificación", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Calificación y comentario guardados", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("Range")
    private RatingAndCommentStatus getRatingAndCommentStatus(String orderId) {
        float rating = 0;
        int commentStatus = 0;
        String comment = "";

        Cursor cursor = db.query("ratings", new String[]{"rating", "comment", "comment_status"}, "order_id = ?", new String[]{orderId}, null, null, null);
        if (cursor.moveToFirst()) {
            rating = cursor.getFloat(cursor.getColumnIndex("rating"));
            comment = cursor.getString(cursor.getColumnIndex("comment"));
            commentStatus = cursor.getInt(cursor.getColumnIndex("comment_status"));
        }
        cursor.close();
        return new RatingAndCommentStatus(rating, comment, commentStatus);
    }

}
