package com.kazimasum.spdemo;

import android.app.Application;
import android.content.SharedPreferences;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
            /*SharedPreferences sp = getSharedPreferences("credentials", MODE_PRIVATE);

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username", "dummyUser");
            editor.putString("password", "dummyPasswrd");
            editor.apply();*/
        MigrationManager.INSTANCE.MigrateSp(this);
    }
}
