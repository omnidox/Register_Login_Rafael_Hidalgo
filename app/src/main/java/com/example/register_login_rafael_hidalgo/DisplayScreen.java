package com.example.register_login_rafael_hidalgo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DisplayScreen extends AppCompatActivity {

    private ISharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_screen);

        sharedPreference = new SecuredSharePref("MYPREFS", this);
//        SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);

        String display = sharedPreference.get("display", "");
//        String display = preferences.getString("display", "");

        TextView displayInfo = (TextView) findViewById(R.id.textViewName);
        displayInfo.setText(display);
    }
}