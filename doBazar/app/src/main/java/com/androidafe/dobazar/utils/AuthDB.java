// AuthDB.java
package com.androidafe.dobazar.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthDB {
    private String username;
    private Context context;

    public AuthDB(Context context) {
        this.context = context;
    }

    public String getUserName() {
        if (username == null) {
            SharedPreferences preferences = context.getSharedPreferences("status", Context.MODE_PRIVATE);
            username = preferences.getString("userName", "null");
        }
        return username;
    }

    public boolean isSameUser() {
        SharedPreferences preferences = context.getSharedPreferences("dbStatus", Context.MODE_PRIVATE);
        String storedUsername = preferences.getString("username", "null");
        return storedUsername.equals(username);
    }
}
