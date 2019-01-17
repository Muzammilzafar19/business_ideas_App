package com.business_idea.business_ideas_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShowBlogsActivity extends AppCompatActivity {
    private DatabaseReference mDatabase,refroot;
    private List<GetBlog> blogdata;
    private BlogAdapter blogAdapter;
    private ImageView btnbackblog;
    public RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_blogs);
        btnbackblog=findViewById(R.id.btnblogback);
        btnbackblog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(ShowBlogsActivity.this,DashboardActivity.class));
                finish();
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
    private List<GetBlog> collectBlog(Map<String,Object> users) {

        ArrayList<String> phoneNumbers = new ArrayList<>();
List<GetBlog> _listblog=new ArrayList<>();
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            phoneNumbers.add((String) singleUser.get("title"));
            _listblog.add(new GetBlog((String) singleUser.get("title"),(String) singleUser.get("authorName"),(String) singleUser.get("postDateTime"),(String) singleUser.get("blog"),(String) singleUser.get("blogImageUri"),(String) singleUser.get("userImage"),"",""));
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
}
