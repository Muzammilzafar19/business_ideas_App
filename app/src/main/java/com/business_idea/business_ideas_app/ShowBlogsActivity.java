package com.business_idea.business_ideas_app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.business_idea.business_ideas_app.Adapter.BlogAdapter;
import com.business_idea.business_ideas_app.DataClasses.GetBlog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.SnackBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ShowBlogsActivity extends AppCompatActivity {
    private DatabaseReference mDatabase,refroot;
    private List<GetBlog> blogdata;
    private BlogAdapter blogAdapter;
    private ImageView btnbackblog,btnfilter;
    public RecyclerView rv;
    private Button btnsearch,btnsearchrange;
    private com.toptoche.searchablespinnerlibrary.SearchableSpinner spinnerpanel,spinnerrange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_blogs);
        btnbackblog=findViewById(R.id.btnblogback);
        btnsearch=findViewById(R.id.btnsearch);
        btnsearchrange=findViewById(R.id.btnsearchrange);
        btnfilter=findViewById(R.id.btnfilter);
        spinnerpanel=findViewById(R.id.spinnerpanel);
        spinnerrange=findViewById(R.id.spinnerrange);
        btnbackblog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(ShowBlogsActivity.this,DashboardActivity.class));
                finish();
            }
        });
        categorychossing();
        investmentrange();
        btnfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categorychossing();
            }
        });
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchcategory();
            }
        });
        btnsearchrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchinvestment();
            }
        });
        mDatabase=FirebaseDatabase.getInstance().getReference();
        refroot=mDatabase.child("posts");
        blogdata=new ArrayList<>();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setIcon(R.drawable.loading);
        progressDialog.setMessage("Please Wait........");
        progressDialog.show();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    //create new user
                    progressDialog.dismiss();

                    try {
                        collectBlog((Map<String,Object>) dataSnapshot.getValue());
                      /*  rv = findViewById(R.id.my_recycler_view);
                        rv.setHasFixedSize(true);
                        rv.setLayoutManager(new LinearLayoutManager(ShowBlogsActivity.this));
                        blogAdapter=new BlogAdapter(ShowBlogsActivity.this,blogdata);
                        rv.setAdapter(blogAdapter);
                        */
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
                Toast.makeText(ShowBlogsActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        };
        refroot.addListenerForSingleValueEvent(eventListener);


    }
    public void categorychossing()
    {
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerpanel.setTitle("Ideas Categories");
        spinnerArray.add("Select Categories");

        spinnerArray.add("App Developer");

        spinnerArray.add("Constructions");
        spinnerArray.add("Antique Furniture Sales");
        spinnerArray.add("Blog Consultant");
        spinnerArray.add("Business Consultant");
        spinnerArray.add("Appliance Repair Technician");
        spinnerArray.add("Computer Repair and Maintenance");
        spinnerArray.add("Customer Service Professional");
        spinnerArray.add("Freelance Writer");
        spinnerArray.add("Grant Writer");
        spinnerArray.add("Hair Salon Owner");
        spinnerArray.add("Ice Cream Shop Business");
        spinnerArray.add("Laundry Service");
        spinnerArray.add("ONLINE COACH");
        spinnerArray.add("ONLINE Business");
        spinnerArray.add("ONLINE TEACHING");
        // you need to have a list of data that you want the spinner to display




        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Spinner sItems = (Spinner) findViewById(R.id.spinner1);
        spinnerpanel.setAdapter(adapter);
    }
    public void investmentrange()
    {
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerpanel.setTitle("Investment Ranges");
        spinnerArray.add("Select Investment Ranges");

        spinnerArray.add("5,000-10,000");
        spinnerArray.add("10,000-20,000");
        spinnerArray.add("20,000-30,000");
        spinnerArray.add("30,000-50,000");
        spinnerArray.add("50,000-80,000");
        spinnerArray.add("80,000-95,000");
        spinnerArray.add("100,000-150,000");
        spinnerArray.add("more than 150,000");

        // you need to have a list of data that you want the spinner to display




        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Spinner sItems = (Spinner) findViewById(R.id.spinner1);
        spinnerrange.setAdapter(adapter);
    }
    private List<GetBlog> collectBlog(Map<String,Object> users) {

        ArrayList<String> phoneNumbers = new ArrayList<>();
List<GetBlog> _listblog=new ArrayList<>();
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            phoneNumbers.add((String) singleUser.get("title"));
            _listblog.add(new GetBlog((String) singleUser.get("title"),(String) singleUser.get("authorName"),(String) singleUser.get("postDateTime"),(String) singleUser.get("blog"),(String) singleUser.get("blogImageUri"),(String) singleUser.get("userImage"),"","",(String) singleUser.get("rating"),(String) singleUser.get("pushId"),(String) singleUser.get("ideaCategory"),(String) singleUser.get("investment")));
          //  Toast.makeText(ShowBlogsActivity.this,(String) singleUser.get("title"),Toast.LENGTH_SHORT).show();
        }
        rv = findViewById(R.id.my_recycler_view);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(ShowBlogsActivity.this));
        blogAdapter=new BlogAdapter(ShowBlogsActivity.this,_listblog);
        rv.setAdapter(blogAdapter);
return _listblog;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // startActivity(new Intent(ShowBlogsActivity.this,DashboardActivity.class));
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.post_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id._filter:
            {

            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void searchcategory()
    {
        Query query = FirebaseDatabase.getInstance().getReference("posts")
                .orderByChild("ideaCategory")
                .equalTo(spinnerpanel.getSelectedItem().toString());
        blogdata=new ArrayList<>();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setIcon(R.drawable.loading);
        progressDialog.setMessage("Please Wait........");
        progressDialog.show();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    //create new user
                    progressDialog.dismiss();

                    try {
                        collectBlog((Map<String,Object>) dataSnapshot.getValue());
                      /*  rv = findViewById(R.id.my_recycler_view);
                        rv.setHasFixedSize(true);
                        rv.setLayoutManager(new LinearLayoutManager(ShowBlogsActivity.this));
                        blogAdapter=new BlogAdapter(ShowBlogsActivity.this,blogdata);
                        rv.setAdapter(blogAdapter);
                        */
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    //  Toast.makeText(DashboardActivity.this,"Value "+name+" "+email,Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Record not found related to this Category",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ShowBlogsActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        };
        query.addListenerForSingleValueEvent(eventListener);
    }
    public void searchinvestment()
    {
        Query query = FirebaseDatabase.getInstance().getReference("posts")
                .orderByChild("investment")
                .equalTo(spinnerrange.getSelectedItem().toString());
        blogdata=new ArrayList<>();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setIcon(R.drawable.loading);
        progressDialog.setMessage("Please Wait........");
        progressDialog.show();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    //create new user
                    progressDialog.dismiss();

                    try {
                        collectBlog((Map<String,Object>) dataSnapshot.getValue());
                      /*  rv = findViewById(R.id.my_recycler_view);
                        rv.setHasFixedSize(true);
                        rv.setLayoutManager(new LinearLayoutManager(ShowBlogsActivity.this));
                        blogAdapter=new BlogAdapter(ShowBlogsActivity.this,blogdata);
                        rv.setAdapter(blogAdapter);
                        */
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    //  Toast.makeText(DashboardActivity.this,"Value "+name+" "+email,Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Record not found related to this Range",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ShowBlogsActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        };
        query.addListenerForSingleValueEvent(eventListener);
    }
}
