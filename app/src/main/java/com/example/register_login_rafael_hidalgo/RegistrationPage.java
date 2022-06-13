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

    //In this App, an encrypted shared preference is implemented to save user information.
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


        //used this site to help implement the code for the date text entry
        // https://www.youtube.com/watch?v=Sce4EKklwKE&ab_channel=TechProgrammingIdeas
        //This ensures that the date inputted will match the MM/DD/YYY format
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

        //This helps configure what happens once the register button is clicked
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreference = new SecuredSharePref("MYPREFS", RegistrationPage.this);

                String newUserFirstName = firstName.getText().toString();
                String newUserLastName = lastName.getText().toString();
                String newUserDateOfBirth = dateOfBirth.getText().toString();
                String newUserEmail = email.getText().toString();
                String newPassword = password.getText().toString();


                //This calls on a program to determine if all entries are valid. If not valid,
                // the program ends with "return". If Valid,
                //the user data is then saved into an encrypted shared preferences.
                if (!isValidEntry(newUserFirstName, newUserLastName, newUserDateOfBirth, newUserEmail, newPassword)) {
                    return;
                }

                //Saves values in a shared preferences called "MYPREFS"
                sharedPreference.put(newUserEmail, newUserEmail);
                sharedPreference.put(newPassword, newPassword);
                sharedPreference.put(newUserEmail + newPassword + "data", newUserFirstName +
                        "\n" + newUserLastName + "\n" + newUserDateOfBirth + "\n" + newUserEmail + "\n" +
                        newPassword);


                //once the user data is saved, the user is taken back into the login page
                Intent loginScreen = new Intent(RegistrationPage.this, LoginRegisterPage.class);
                startActivity(loginScreen);
            }
        });
    }

    // this is a function that will check if all entries are valid according to different conditions
    public boolean isValidEntry(String firstName, String lastName,
                                CharSequence userBirthDate, CharSequence email, String password) {

        //set up edit texts in order to input errors if need be
        final EditText firstEditName = (EditText) findViewById(R.id.etFirstName);
        final EditText lastEditName = (EditText) findViewById(R.id.etLastName);
        final EditText dateOfBirthEdit = (EditText) findViewById(R.id.etDateOfBirth);
        final EditText emailEdit = (EditText) findViewById(R.id.etNewEmail);
        final EditText passwordEdit = (EditText) findViewById(R.id.etNewPassword);

        //boolean valid will remain true unless a an entry is found to not be valid
        boolean valid = true;

        //sets up what pattern will be recognized depending whether it is a password or a name
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");

        Pattern Name_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{3,30}");

        //all if statements below will determine if a relevant entry is valid.
        // If not valid, boolean valid will be set to false and an error will appear in the relevant field.
        if ((TextUtils.isEmpty(firstName)) || !Name_PATTERN.matcher(firstName).matches()) {
            valid = false;
            firstEditName.setError("Please Input a first name between 3 to 30 characters long");
        }

        if ((TextUtils.isEmpty(lastName)) || !Name_PATTERN.matcher(lastName).matches()) {
            valid = false;
            lastEditName.setError(("Please Input a last name between 3 to 30 characters long "));
        }

        if (TextUtils.isEmpty(userBirthDate) || (userBirthDate.toString().matches(".*[A-Z].*"))) {
            valid = false;
            dateOfBirthEdit.setError("Please Input a full birth date");
        }

        //check email fields
        if ((TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            valid = false;
            emailEdit.setError("Please Input a proper email");
        }

        if ((TextUtils.isEmpty(password)) || !PASSWORD_PATTERN.matcher(password).matches()) {
            valid = false;
            passwordEdit.setError("Please Input a password between 8 to 24 characters long");
        }


        return valid;

    }
}

