package com.example.mytranscript;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    TextView backLoginBtn, registerBtn;
    EditText etFullName, etRegNumber, etEmail, etCollege, etSchool, etDepartment, etLevel, etPassword, etPassConfirm;
    AwesomeValidation awesomeValidation;

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


        //For confirmpassword

        //awesomeValidation.addValidation(this,R.id.pass_confirm,R.id.pass,R.string.invalid_confirm_password);

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
                    //On sucess
                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    // Toast.makeText(getApplicationContext(),"Form Validation Sucessfull",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Form Validation Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}