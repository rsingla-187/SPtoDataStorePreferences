package com.kazimasum.spdemo;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import kotlin.Unit;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        createSomeDummySharedPreferenceEntries();
        readFromDataStorePreferences();
    }

    private void readFromDataStorePreferences() {
        MigrationManager.INSTANCE.getStringPreferenceValueForKey(getApplicationContext(), MigrationManager.USER_NAME, this::userName);
        MigrationManager.INSTANCE.getStringPreferenceValueForKey(getApplicationContext(), MigrationManager.PASS_WORD, this::password);
    }

    private void createSomeDummySharedPreferenceEntries() {
        SharedPreferences sp = getSharedPreferences(MigrationManager.INSTANCE.getUSER_PREFERENCES_NAME(), MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(MigrationManager.USER_NAME, "some user");
        editor.putString(MigrationManager.PASS_WORD, "some password");
        editor.apply();
    }

    private Unit password(String password) {
        Log.d("hitesh_log", "Password "+password);
        return Unit.INSTANCE;
    }

    private Unit userName(String username) {
        Log.d("hitesh_log", "UserName "+username);
        return Unit.INSTANCE;
    }
}
