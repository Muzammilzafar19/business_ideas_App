package com.business_idea.business_ideas_app;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.business_idea.business_ideas_app.Fragment.OtherImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        //finish();
       // startActivity(new Intent(MainActivity.this,ProfileActivity.class));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
