package com.example.register_login_rafael_hidalgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class LoginRegisterPage extends AppCompatActivity {

    //In this App, an encrypted shared preference is implemented to save user information.
    private ISharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register_page);

        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);


        // Determines what code will run when the login button is clicked.
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = etEmail.getText().toString();
                String password = etPassword.getText().toString();


                //Will set an error on the edit text fields if password or email values are not correct
                if (!isValidEmail(userEmail) || !isValidPassword(password)) {
                    if (!isValidEmail(userEmail)) {
                        etEmail.setError("Please Input a proper email");
                    }

                    if (!isValidPassword(password)) {
                        etPassword.setError("Please Input a password between 8 to 24 characters long");
                    }

                    return;
                }


                //retrieves information from shared preference to verify if user input matches sharedpreferences data
                sharedPreference = new SecuredSharePref("MYPREFS", LoginRegisterPage.this);

                String userDetails = sharedPreference.get(userEmail + password + "data", "No information on that user");

                sharedPreference.put("display", userDetails);


                //if values are valid clicking the login button will lead to the display page which will display user information
                Intent displayScreen = new Intent(LoginRegisterPage.this, DisplayScreen.class);
                startActivity(displayScreen);
            }
        });


//        Determines what code will run when the Register button is clicked.
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //clicking the register button will lead to the registration page
                Intent registerScreen = new Intent(LoginRegisterPage.this, RegistrationPage.class);
                startActivity(registerScreen);
            }
        });
    }

    //returns a bool to help determine if email is valid
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    //returns a bools to help determine is password is valid
    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }
}