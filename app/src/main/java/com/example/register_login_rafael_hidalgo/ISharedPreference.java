package com.example.register_login_rafael_hidalgo;

public interface ISharedPreference {
    String get(String name, String value);

    void put(String name, String value);
}
