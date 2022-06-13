package com.example.register_login_rafael_hidalgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Pattern;

public class RegistrationPage extends AppCompatActivity {

    private ISharedPreference sharedPreference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        final EditText firstName = (EditText) findViewById(R.id.etFirstName);
        final EditText lastName = (EditText) findViewById(R.id.etLastName);
        final EditText dateOfBirth = (EditText) findViewById(R.id.etDateOfBirth);
        final EditText email = (EditText) findViewById(R.id.etNewEmail);
        final EditText password = (EditText) findViewById(R.id.etNewPassword);
        Button btnRegister = (Button) findViewById(R.id.btnNewRegister);


        //used this site to help implement the code for the text entry https://www.youtube.com/watch?v=Sce4EKklwKE&ab_channel=TechProgrammingIdeas
        dateOfBirth.addTextChangedListener(new TextWatcher() {


            private String current = "";
            private String mmddyyyy = "MMDDYYYY";
            private Calendar cal = Calendar.getInstance();


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + mmddyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int mon = Integer.parseInt(clean.substring(0, 2));
                        int day = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        if (mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon - 1);

                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", mon, day, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    dateOfBirth.setText(current);
                    dateOfBirth.setSelection(sel < current.length() ? sel : current.length());


                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                SharedPreferences preferences = getSharedPreferences("MYPREFS",MODE_PRIVATE);

                sharedPreference = new SecuredSharePref("MYPREFS", RegistrationPage.this);

                String newUserFirstName = firstName.getText().toString();
                String newUserLastName = lastName.getText().toString();
                String newUserDateOfBirth = dateOfBirth.getText().toString();
                String newUserEmail = email.getText().toString();
                String newPassword = password.getText().toString();

                if (!isValidEntry(newUserFirstName, newUserLastName, newUserDateOfBirth, newUserEmail, newPassword))
                {
                    return;
                }


//                if (!isValidName(newUserFirstName)){
//
//                    Toast.makeText(RegistrationPage.this, "Please Input a name between 3 to 30 characters long ",
//                            Toast.LENGTH_SHORT).show();
//                    return;
//                }


//                if (!isValidName(newUserLastName)){
//
//                    Toast.makeText(RegistrationPage.this, "Please Input a password between 3 to 30 characters long ",
//                            Toast.LENGTH_SHORT).show();
//                    return;
//                }

//                if (!isValidDate(newUserDateOfBirth)){
//
//                    Toast.makeText(RegistrationPage.this, "Please Input a full birth date",
//                            Toast.LENGTH_SHORT).show();
//                    return;
//                }


//                if (!isValidEmail(newUserEmail)){
//
//                    Toast.makeText(RegistrationPage.this, "Please Input a proper email",
//                            Toast.LENGTH_SHORT).show();
//                    return;
//                }


//                if (!isValidPassword(newPassword)){
//
//                    Toast.makeText(RegistrationPage.this, "Please Input a password between 8 to 24 characters long ",
//                            Toast.LENGTH_SHORT).show();
//                    return;
//                }

//                SharedPreferences.Editor editor = preferences.edit();

                    //stores 3 new instances of sharedprefs. Both the user and password's keys are the same as the input.
                    //Must be done this way because sharedprefs is stupid and inefficient. You cannot store Arrays easily
                    //so I use strings instead.


                    sharedPreference.put(newUserEmail, newUserEmail);
                sharedPreference.put(newPassword, newPassword);
                sharedPreference.put(newUserEmail + newPassword + "data", newUserFirstName +
                        "\n" + newUserLastName + "\n" + newUserDateOfBirth + "\n" + newUserEmail + "\n" +
                        newPassword);
//                editor.putString(newUser,newUser);
//                editor.commit();
//                editor.putString(newPassword, newPassword);
//                editor.commit();
//                editor.putString(newUser + newPassword + "data", newUser + "\n" + newEmail);
//                editor.commit();

                Intent loginScreen = new Intent(RegistrationPage.this, LoginRegisterPage.class);
                startActivity(loginScreen);
            }
        });














    }

//    public static boolean isValidEmail(CharSequence target) {
//        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
//    }
//
//    public static boolean isValidPassword(String s) {
//        Pattern PASSWORD_PATTERN
//                = Pattern.compile(
//                "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");
//
//        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
//    }
//
//    public static boolean isValidName(String s) {
//        Pattern PASSWORD_PATTERN
//                = Pattern.compile(
//                "[a-zA-Z0-9\\!\\@\\#\\$]{3,30}");
//
//        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
//    }
//
//    public static boolean isValidDate(CharSequence target) {
//        return (!TextUtils.isEmpty(target) && !(target.toString().matches(".*[A-Z].*")));
//    }

    public boolean isValidEntry(String firstName, String lastName, CharSequence userBirthDate, CharSequence email, String password) {


        final EditText firstEditName = (EditText) findViewById(R.id.etFirstName);
        final EditText lastEditName = (EditText) findViewById(R.id.etLastName);
        final EditText dateOfBirthEdit = (EditText) findViewById(R.id.etDateOfBirth);
        final EditText emailEdit = (EditText) findViewById(R.id.etNewEmail);
        final EditText passwordEdit = (EditText) findViewById(R.id.etNewPassword);

        boolean valid = true;
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");

        Pattern Name_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{3,30}");


        if ((TextUtils.isEmpty(firstName)) || !Name_PATTERN.matcher(firstName).matches()) {
            Toast.makeText(RegistrationPage.this, "Please Input a first name between 3 to 30 characters long ",
                    Toast.LENGTH_SHORT).show();
            valid = false;
            firstEditName.setError("Please Input a first name between 3 to 30 characters long");
        }

        if ((TextUtils.isEmpty(lastName)) || !Name_PATTERN.matcher(lastName).matches()) {
            Toast.makeText(RegistrationPage.this, "Please Input a last name between 3 to 30 characters long ",
                    Toast.LENGTH_SHORT).show();
            valid = false;
            lastEditName.setError(("Please Input a last name between 3 to 30 characters long "));
        }

        if (TextUtils.isEmpty(userBirthDate) || (userBirthDate.toString().matches(".*[A-Z].*"))) {
            Toast.makeText(RegistrationPage.this, "Please Input a full birth date",
                    Toast.LENGTH_SHORT).show();
            valid = false;
            dateOfBirthEdit.setError("Please Input a full birth date");
        }

        //check email fields
        if ((TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            Toast.makeText(RegistrationPage.this, "Please Input a proper email",
                    Toast.LENGTH_SHORT).show();
            valid = false;
            emailEdit.setError("Please Input a proper email");
        }

        if ((TextUtils.isEmpty(password)) || !PASSWORD_PATTERN.matcher(password).matches()) {
            Toast.makeText(RegistrationPage.this, "Please Input a password between 8 to 24 characters long",
                    Toast.LENGTH_SHORT).show();
            valid = false;
            passwordEdit.setError("Please Input a password between 8 to 24 characters long");
        }


        return valid;


    }
}

