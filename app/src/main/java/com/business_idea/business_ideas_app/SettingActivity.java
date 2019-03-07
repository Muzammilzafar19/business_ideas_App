package com.business_idea.business_ideas_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

public class SettingActivity extends AppCompatActivity {
    private com.rey.material.widget.Switch notifyswitch;
    private TextView _btnbacksetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        notifyswitch=findViewById(R.id.notifyswitch);
        _btnbacksetting=findViewById(R.id._btnbacksetting);
        try {
            if(getDefaults("notify",SettingActivity.this).equals("ON"))
            {
                notifyswitch.setChecked(true);
            }
            else if(getDefaults("notify",SettingActivity.this).equals("OFF"))
            {
                notifyswitch.setChecked(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(notifyswitch.isChecked())
        {
            FirebaseMessaging.getInstance().subscribeToTopic("pushNotificationForBlog");
        }
        else {

        }
        _btnbacksetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(SettingActivity.this,DashboardActivity.class));
                finish();
            }
        });
        notifyswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notifyswitch.isChecked())
                {
                    // Subscribe
                 //   FirebaseApp.initializeApp(SettingActivity.this);
                    FirebaseMessaging.getInstance().subscribeToTopic("pushNotificationForBlog");
                    setDefaults("notify","ON",SettingActivity.this);
                }
                else {
                    // UnSubscribe
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("pushNotificationForBlog");
                    setDefaults("notify","OFF",SettingActivity.this);
                }
            }
        });
    }
    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
    public static void setDefaults(String key,String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
      //  startActivity(new Intent(SettingActivity.this,DashboardActivity.class));
        finish();
    }
}
