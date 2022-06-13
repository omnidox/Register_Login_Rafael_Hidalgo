package com.example.register_login_rafael_hidalgo;

//An interface for shared preferences, considering using this to help implement both encrypted shared preferences
// and normal shared preferences with future projects.
public interface ISharedPreference {
    String get(String name, String value);

    void put(String name, String value);
}
