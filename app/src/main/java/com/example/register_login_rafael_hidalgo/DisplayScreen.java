package com.example.register_login_rafael_hidalgo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DisplayScreen extends AppCompatActivity {

    //In this App, an encrypted shared preference is implemented to save user information.
    private ISharedPreference sharedPreference;

    //this will display the content of shared preferences
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_screen);

        sharedPreference = new SecuredSharePref("MYPREFS", this);

        String display = sharedPreference.get("display", "");

        TextView displayInfo = (TextView) findViewById(R.id.textViewName);
        displayInfo.setText(display);
    }
}