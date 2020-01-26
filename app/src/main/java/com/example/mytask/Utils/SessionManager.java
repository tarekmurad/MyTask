package com.example.mytask.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.mytask.LoginActivity;

import java.util.HashMap;

public class SessionManager {

    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    private static final String LOGIN = "IS_LOGIN";

    private static final String PREF_NAME = "LOGIN";
    private SharedPreferences.Editor editor;
    private Context context;
    private SharedPreferences sharedPreferences;
    private int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String email, String password) {

        editor.putBoolean(LOGIN, true);
        editor.putString(PASSWORD, password);
        editor.putString(EMAIL, email);

        editor.commit();
    }


    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }

    }


    public HashMap<String, String> getUserDetail() {

        HashMap<String, String> user = new HashMap<String, String>();
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));
        return user;
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }

}