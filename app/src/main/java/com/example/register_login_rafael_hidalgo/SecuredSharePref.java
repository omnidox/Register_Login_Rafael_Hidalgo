package com.example.register_login_rafael_hidalgo;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;


public class SecuredSharePref implements ISharedPreference {
    private SharedPreferences sharedPreferences;

    public SecuredSharePref(String name, Context context) {


        MasterKey masterKey = null;
        try {
            masterKey = new MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build();


            sharedPreferences = EncryptedSharedPreferences.create(
                    context,
                    name,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );


        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    @Override
    public String get(String name, String value) {
        return sharedPreferences.getString(name, value);
    }

    @Override
    public void put(String name, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, value);
        editor.apply();

    }
}
