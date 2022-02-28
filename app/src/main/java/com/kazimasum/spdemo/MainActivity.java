package com.kazimasum.spdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.flow.FlowCollector;

public class MainActivity extends AppCompatActivity
{
   EditText t1,t2;
   Button savebtn, delbtn;
   TextView tv;
   MigrationManager migrationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context=getApplicationContext();

        t1=(EditText)findViewById(R.id.t1);
         t2=(EditText)findViewById(R.id.t2);
         tv=(TextView)findViewById(R.id.tv);
         savebtn=(Button)findViewById(R.id.savebtn);
         delbtn=(Button)findViewById(R.id.delbtn);

             checkexistedrecord();

             savebtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     /*SharedPreferences sp=context.getSharedPreferences("credentials",MODE_PRIVATE);

                  SharedPreferences.Editor editor=sp.edit();
                    editor.putString("username",t1.getText().toString());
                    editor.putString("password",t2.getText().toString());
                    editor.apply();
                    t1.setText("");
                    t2.setText("");

                    tv.setTextColor(Color.GREEN);
                    tv.setText("Insreted Successfully");*/
                     migrateToPreferencesDataStore();
                 }
             });
/*
             delbtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
                     SharedPreferences.Editor editor=sp.edit();
                     editor.clear();
                     editor.apply();
                     t1.setText("");
                     t2.setText("");

                     tv.setTextColor(Color.GREEN);
                     tv.setText("Deleted Successfully");
                 }
             });*/

    }

    private void migrateToPreferencesDataStore() {
       // val USERNAME = preferencesKey<String>(username)
        migrationManager = new MigrationManager(this);

    }
private void readdatastore()
{
    BuildersKt.launch(GlobalScope.INSTANCE,
            Dispatchers.getIO(),//context to be ran on
            CoroutineStart.DEFAULT,
            (coroutineScope, continuation) -> {
                try {
                    migrationManager.getUserValueFlow("").collect((s,continuation1)->s,continuation);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
    );
}
/*private void editdatastore(){
    BuildersKt.launch(GlobalScope.INSTANCE,
            Dispatchers.getIO(),//context to be ran on
            CoroutineStart.DEFAULT,
            (coroutineScope, continuation) -> {
                try {
                    migrationManager.setValue(username,"");
                } catch (Exception e) {
                    e.printStackTrace();
                }
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