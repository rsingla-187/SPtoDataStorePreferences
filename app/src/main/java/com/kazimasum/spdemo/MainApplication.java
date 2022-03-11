package com.kazimasum.spdemo;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import kotlin.Unit;

public class MainApplication extends Application {
    private static final String TAG = "rupali_MainApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Inside MainApplication oncreate()");
        createSomeDummySharedPreferenceEntries();
        readFromDataStorePreferences();
    }

    private void readFromDataStorePreferences() {
        Log.d(TAG, "Inside MainApplication readFromDP()");
        MigrationManager.INSTANCE.getStringPreferenceValueForKey(getApplicationContext(), MigrationManager.USER_NAME,this::userName);
        MigrationManager.INSTANCE.getStringPreferenceValueForKey(getApplicationContext(), MigrationManager.PASS_WORD,this::password);
    }

    private void createSomeDummySharedPreferenceEntries() {
        Log.d(TAG, "Inside MainApplication createdummy sharedpreferences()");
        SharedPreferences sp = getSharedPreferences(MigrationManager.INSTANCE.getUSER_PREFERENCES_NAME(), MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(MigrationManager.USER_NAME, "some user");
        editor.putString(MigrationManager.PASS_WORD, "some password");
        editor.apply();
        Log.d(TAG, "checking values in shared preference  : "+sp.contains("username"));
    }

    private Unit password(String password) {
        Log.d(TAG, "Password "+password);
        return Unit.INSTANCE;
    }

    private Unit userName(String username) {
        Log.d(TAG, "UserName "+username);
        return Unit.INSTANCE;
    }
}
