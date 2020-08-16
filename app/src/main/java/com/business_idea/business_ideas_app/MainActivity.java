package com.business_idea.business_ideas_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

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
