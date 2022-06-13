package com.example.register_login_rafael_hidalgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    //A simple Splash page with an image button that will bring the user to the login/register page when clicking on continue
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btnContinue = (ImageButton) findViewById(R.id.imageButton);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent LoginRegister = new Intent(MainActivity.this, LoginRegisterPage.class);
                startActivity(LoginRegister);
            }
        });
    }
}