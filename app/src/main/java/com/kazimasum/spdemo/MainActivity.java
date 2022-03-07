package com.kazimasum.spdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {
    EditText t1, t2;
    Button savebtn, delbtn, mgrt_sp, read_dp, edit_dp;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context = getApplicationContext();

        t1 = (EditText) findViewById(R.id.t1);
        t2 = (EditText) findViewById(R.id.t2);
        tv = (TextView) findViewById(R.id.tv);
        savebtn = (Button) findViewById(R.id.savebtn);
        delbtn = (Button) findViewById(R.id.delbtn);
        mgrt_sp = (Button) findViewById(R.id.mgrt_sp);
        read_dp = (Button) findViewById(R.id.read_dp);
        edit_dp = (Button) findViewById(R.id.edit_dp);


        //  checkexistedrecord();

       /* savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sp = context.getSharedPreferences("credentials", MODE_PRIVATE);

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("username", t1.getText().toString());
                editor.putString("password", t2.getText().toString());
                editor.apply();
                t1.setText("");
                t2.setText("");

                tv.setTextColor(Color.GREEN);
                tv.setText("Insreted Successfully");
            }
        });
        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("credentials", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                t1.setText("");
                t2.setText("");

                tv.setTextColor(Color.GREEN);
                tv.setText("Deleted Successfully");
            }
        });
*/       /* mgrt_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MigrationManager.MigrateSp(context);
                tv.setTextColor(Color.GREEN);
                tv.setText("Migrated Successfully");
            }
        });*/

        read_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BuildersKt.launch(GlobalScope.INSTANCE,
                        Dispatchers.getIO(),//context to be ran on
                        CoroutineStart.DEFAULT,
                        (coroutineScope, continuation) -> {
                            try {
                                MigrationManager.INSTANCE.getStringPreferenceValueForKey(MainActivity.this, MigrationManager.USER_NAME, this::userName);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                );
            }

            private Unit userName(String username) {
                if (Looper.myLooper() != getMainLooper()) {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        setFields(username);
                    });
                } else {
                    setFields(username);
                }
                return Unit.INSTANCE;
            }
        });
        edit_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuildersKt.launch(GlobalScope.INSTANCE,
                        Dispatchers.getIO(),//context to be ran on
                        CoroutineStart.DEFAULT,
                        (coroutineScope, continuation) -> {
                            try {
                                MigrationManager.setUserValue(MainActivity.this, MigrationManager.USER_NAME, t1.getText().toString());
                                tv.setTextColor(Color.GREEN);
                                tv.setText("Edited Successfully");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                );
            }
        });

    }

    private void setFields(String username) {
        if (username != null) {
            t1.setText((CharSequence) username);
            // t2.setText(sp.getString("password",""));
            tv.setTextColor(Color.GREEN);
            tv.setText("Read Successfully");
        } else {
            tv.setText("Record not found");
            tv.setTextColor(Color.RED);
        }
    }

    /*private void migrateToPreferencesDataStore() {
       // val USERNAME = preferencesKey<String>(username)

        MigrationManager.MigrateSp(this);
    }
private void readdatastore()
{
    BuildersKt.launch(GlobalScope.INSTANCE,
            Dispatchers.getIO(),//context to be ran on
            CoroutineStart.DEFAULT,
            (coroutineScope, continuation) -> {
                try {
                   //String username = migrationManager.getValueFlow(migrationManager.USER_NAME, (Continuation<? super String>) continuation).collect((s, continuation1)->s,continuation);
                   String username = (String)  MigrationManager.getValueFlow(MigrationManager.USER_NAME, (Continuation<? super String>) continuation);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
    );
}
private void editdatastore(){
    BuildersKt.launch(GlobalScope.INSTANCE,
            Dispatchers.getIO(),//context to be ran on
            CoroutineStart.DEFAULT,
            (coroutineScope, continuation) -> {
                try {
                       MigrationManager.setValue(MigrationManager.USER_NAME,t2.getText().toString(), (Continuation<? super Unit>) continuation);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
    );
}*/

    public void checkexistedrecord()
    {
        SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
        if(sp.contains("username"))
        {
            t1.setText(sp.getString("username",""));
            t2.setText(sp.getString("password",""));
        }
        else {
            tv.setText("Record not found");
            tv.setTextColor(Color.RED);
        }
    }
}
