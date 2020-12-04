package com.example.mytranscript;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    Button loginBtn,garellyBtn;
    TextView ForgotBtn,SignUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        loginBtn = findViewById(R.id.LoginBtn);
        ForgotBtn = findViewById(R.id.forgot_pass);
        SignUpBtn = findViewById(R.id.signup_Btn);
        //garellyBtn =findViewById(R.id.ChooseImg);

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
                Intent intent = new Intent(Login.this,PaymentSlip.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
