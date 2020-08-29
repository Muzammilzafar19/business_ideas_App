package com.business_idea.business_ideas_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth auth;
    private TextView txtMainName,txtuseremail_;
    private de.hdodenhof.circleimageview.CircleImageView _imgperson,profileimg;
    private DatabaseReference mDatabase,refroot;
    private CardView blogcard,ideacard,profilecard,newscard;
    String dataTitle, dataMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

            profileimg=findViewById(R.id.profileimg);
            ideacard=findViewById(R.id.ideacard);
            newscard=findViewById(R.id.newscard);
            profilecard=findViewById(R.id.profilecard);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            try{
                if(getDefaults("firstTime",DashboardActivity.this)!=null && !getDefaults("firstTime",DashboardActivity.this).equals("Yes"))
                {
                    FirebaseApp.initializeApp(this);
                    setDefaults("firstTime","Yes",DashboardActivity.this);
                }
            }
            catch (Exception ignored)
            {

            }

            try {


    // Handle possible data accompanying notification message.
                if (getIntent().getExtras() != null) {
                    for (String key : getIntent().getExtras().keySet()) {
                        if (key.equals("title")) {
                            dataTitle=(String)getIntent().getExtras().get(key);
                        }
                        if (key.equals("message")) {
                            dataMessage = (String)getIntent().getExtras().get(key);;

                        }
                    }
                    showAlertDialog();
                }
            } catch (Exception e) {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
            newscard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DashboardActivity.this,NewsActivity.class));
                }
            });
            ideacard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DashboardActivity.this,BusinessIdeasActivity.class));
                   // finish();
                }
            });
            profilecard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DashboardActivity.this,MyProfileActivity.class));
                   // finish();
                }
            });
            blogcard=findViewById(R.id.blogcard);
            auth=FirebaseAuth.getInstance();
            setSupportActionBar(toolbar);
            blogcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DashboardActivity.this,ShowBlogsActivity.class));

                }
            });

            try {
                if(getDefaults("ValueStored",DashboardActivity.this).equals("Yes"))
                {
                    txtMainName=findViewById(R.id.txtMainName);
                    txtuseremail_=findViewById(R.id._txtuseremail);
                    _imgperson=findViewById(R.id._imgperson);
                    txtuseremail_.setText(getDefaults("useremail",DashboardActivity.this));
                    txtMainName.setText(getDefaults("username",DashboardActivity.this));
                    RequestOptions requestOptions=new RequestOptions();
                    requestOptions.placeholder(R.drawable.person);

                    Glide.with(DashboardActivity.this).load(getDefaults("userimg",DashboardActivity.this)).apply(requestOptions).into(_imgperson);
                    Glide.with(DashboardActivity.this).load(getDefaults("userimg",DashboardActivity.this)).apply(requestOptions).into(profileimg);
                }
                else {
                 getData();
                    }

            } catch (Exception e) {
           getData();
              // Toast.makeText(DashboardActivity.this,"Error:"+e.getMessage(),Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }


            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                           // .setAction("Action", null).show();
                    startActivity(new Intent(DashboardActivity.this,ChatRoomActivity.class));
                }
            });

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

    }
        private void showAlertDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Message");
            builder.setMessage("title: " + dataTitle + "\n" + "message: " + dataMessage);
            builder.setPositiveButton("OK", null);
            builder.show();
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
    protected void onResume() {
        super.onResume();
       // Toast.makeText(DashboardActivity.this,"Back Again from OnResume Function",Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onStart() {

        super.onStart();
      //  Toast.makeText(DashboardActivity.this,"Back Again from OnStart Function",Toast.LENGTH_LONG).show();
    }

    public void getData()
    {

        mDatabase=FirebaseDatabase.getInstance().getReference();
        refroot=mDatabase.child("users").child(auth.getUid());

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Check Authentication");
        progressDialog.setIcon(R.drawable.authpic);
        progressDialog.setMessage("Please Wait.........");
        progressDialog.show();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    //create new user
                    progressDialog.dismiss();
                    String a= String.valueOf(dataSnapshot.getChildrenCount());
                    String name= dataSnapshot.child("userName").getValue().toString();
                    String usid=dataSnapshot.child("uid").getValue().toString();
                   String UserType=dataSnapshot.child("userType").getValue().toString();
                    String UserAbout=dataSnapshot.child("userAbout").getValue().toString();
                 String UserGender=dataSnapshot.child("userGender").getValue().toString();
                    String UserAge=dataSnapshot.child("userAge").getValue().toString();
                    String email= dataSnapshot.child("userEmail").getValue().toString();
                    String imgurl=dataSnapshot.child("imageUrl").getValue().toString();
                    try {
                        txtMainName=findViewById(R.id.txtMainName);
                        txtuseremail_=findViewById(R.id._txtuseremail);
                        _imgperson=(de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id._imgperson);
                        txtuseremail_.setText(email);
                        txtMainName.setText(name);
                        setDefaults("userimg",imgurl,DashboardActivity.this);
                        setDefaults("username",name,DashboardActivity.this);
                        setDefaults("useremail",email,DashboardActivity.this);
                        setDefaults("userid",usid,DashboardActivity.this);
                        setDefaults("usertype",UserType,DashboardActivity.this);
                        setDefaults("userAge",UserAge,DashboardActivity.this);
                        setDefaults("userGender",UserGender,DashboardActivity.this);
                        setDefaults("userAbout",UserAbout,DashboardActivity.this);
                        setDefaults("ValueStored","Yes",DashboardActivity.this);
                        RequestOptions requestOptions=new RequestOptions();
                        requestOptions.placeholder(R.drawable.person);

                        Glide.with(DashboardActivity.this).load(imgurl).apply(requestOptions).into(_imgperson);
                        Glide.with(DashboardActivity.this).load(imgurl).apply(requestOptions).into(profileimg);
                        //  Toast.makeText(getApplicationContext(),a,Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    //  Toast.makeText(DashboardActivity.this,"Value "+name+" "+email,Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DashboardActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        };
        refroot.addListenerForSingleValueEvent(eventListener);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
            finish();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(DashboardActivity.this,SettingActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_blog) {
            startActivity(new Intent(DashboardActivity.this,PostBlogsActivity.class));

            // Handle the camera action
        } else if (id == R.id.nav_myblog) {
            startActivity(new Intent(DashboardActivity.this,MyBlogActivity.class));

        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(DashboardActivity.this,SettingActivity.class));


        }
        else if(id==R.id.nav_highrate)
        {
            startActivity(new Intent(DashboardActivity.this,HighRatingIdeaActivity.class));
        }
        else if (id == R.id.nav_sponsers) {
            startActivity(new Intent(DashboardActivity.this,SponsersActivity.class));


        }
        else if (id == R.id.nav_signout) {
            auth=FirebaseAuth.getInstance();
            auth.signOut();
            startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
