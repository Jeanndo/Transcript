package com.example.mytranscript;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    Button loginBtn;
    TextView ForgotBtn,SignUpBtn;
    EditText userName,userPass;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        loginBtn = findViewById(R.id.LoginBtn);
        ForgotBtn = findViewById(R.id.forgot_pass);
        SignUpBtn = findViewById(R.id.signup_Btn);
        userName= findViewById(R.id.user_name);
        userPass=findViewById(R.id.user_pass);


        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(Login.this,SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn(
                        ""+userName.getText().toString(),
                        ""+userPass.getText().toString()
                );

            }
        });


    }
    private void signIn(String username, String password ) {
        String url="https://transcript-ur.herokuapp.com/api/v1/auth/signin";

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName", username);
            jsonObject.put("password",password);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        //build response
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    sharedPreferences=getSharedPreferences("tokenPrefs", Context.MODE_PRIVATE);
                    String saveToken=response.getString("token");
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString("token",saveToken);
                    editor.commit();
                    System.out.println(saveToken);
                    Toast.makeText(Login.this, "User Signed in   Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(Login.this,UserDashBoard.class);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Login.this,  "Failed", Toast.LENGTH_SHORT).show();
            }
        }) {
            @NonNull
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");

                return headers;
            }
        };
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}
