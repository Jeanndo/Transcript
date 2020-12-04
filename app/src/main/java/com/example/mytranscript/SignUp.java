package com.example.mytranscript;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    TextView backLoginBtn, registerBtn;
    EditText etFullName, etRegNumber, etEmail, etCollege, etSchool, etDepartment, etLevel, etPassword, etPassConfirm;
    AwesomeValidation awesomeValidation;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);


        // assign Variables

        etFullName = findViewById(R.id.full_Name);
        etRegNumber = findViewById(R.id.reg_number);
        etEmail = findViewById(R.id.email);
        etCollege = findViewById(R.id.college);
        etSchool = findViewById(R.id.school);
        etDepartment = findViewById(R.id.department);
        etLevel = findViewById(R.id.level);
        etPassword = findViewById(R.id.pass);
        //  etPassConfirm = findViewById(R.id.pass_confirm);
        registerBtn = findViewById(R.id.RegisterBtn);
        backLoginBtn = findViewById(R.id.Tologin);

        //INITIALIZE AWESOME VALIDATION

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Name Validation
        awesomeValidation.addValidation(this, R.id.full_Name, RegexTemplate.NOT_EMPTY, R.string.invalid_name);

        //For Regn Number
        // awesomeValidation.addValidation(this,R.id.reg_number, "[5-9]{1}[0-9]9$",R.string.invalid_RegNumber);

        // For Email
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);

        //Collage Validation
        awesomeValidation.addValidation(this, R.id.college, RegexTemplate.NOT_EMPTY, R.string.invalid_college);

        //School Validation
        awesomeValidation.addValidation(this, R.id.school, RegexTemplate.NOT_EMPTY, R.string.invalid_scholl);


        //Department Validation
        awesomeValidation.addValidation(this, R.id.department, RegexTemplate.NOT_EMPTY, R.string.invalid_department);

        //Level Validation
        awesomeValidation.addValidation(this, R.id.level, RegexTemplate.NOT_EMPTY, R.string.invalid_level);

        //For website

        // awesomeValidation.addValidation(this,R.id.website,Patterns.WEB_URL,R.string.invalid_websit);

        //Password
        awesomeValidation.addValidation(this, R.id.pass, ".{6,}", R.string.invalid_password);




        backLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check Validation

                if (awesomeValidation.validate()) {
                    //On success
                    signUp(
                                "" + etFullName.getText().toString(),
                                "" + etRegNumber.getText().toString(),
                                "" + etEmail.getText().toString(),
                                "" + etCollege.getText().toString(),
                                "" + etSchool.getText().toString(),
                                "" + etDepartment.getText().toString(),
                                "" + etLevel.getText().toString(),
                                "" + etPassword.getText().toString()

                        );


                } else {
                    Toast.makeText(getApplicationContext(), "Form Validation Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void signUp(String fullName, String regNumber, String email, String college,String school,String department
    ,String level,String password ) {
        String url="https://transcript-ur.herokuapp.com/api/v1/auth/signup";

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("fullName", fullName);
            jsonObject.put("regNumber",regNumber);
            jsonObject.put("email", email);
            jsonObject.put("college",college);
            jsonObject.put("school",school);
            jsonObject.put("department",department);
            jsonObject.put("level",level);
            jsonObject.put("password",password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //build response
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    sharedPreferences=getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                    String saveToken=response.getString("token");
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString("token",saveToken);
                    editor.commit();
                    System.out.println(saveToken);
                    Toast.makeText(SignUp.this, "User Signed up   Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(SignUp.this,UserDashBoard.class);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SignUp.this,  "Failed", Toast.LENGTH_SHORT).show();
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