package com.business_idea.business_ideas_app;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.business_idea.business_ideas_app.Adapter.MyBlogAdapter;
import com.business_idea.business_ideas_app.DataClasses.GetBlog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyBlogActivity extends AppCompatActivity {
    private List<GetBlog> blogdata;
    private MyBlogAdapter blogAdapter;
    private DatabaseReference mDatabase,refroot;
    private ImageView btnbackblog;
    FirebaseAuth auth;
    public RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_blog);

        auth=FirebaseAuth.getInstance();
        btnbackblog=findViewById(R.id.btnblogback);
        btnbackblog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyBlogActivity.this,DashboardActivity.class));
                finish();
            }
        });
        fetchblog();
        childchange();
    }
    private void childchange()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        refroot=mDatabase.child("posts");
        refroot.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                try {


                    fetchblog();
                    //  Toast.makeText(EmployeeAuthenticationActivity.this, "Status "+status, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MyBlogActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                fetchblog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void fetchblog()
    {
        Query query = FirebaseDatabase.getInstance().getReference("posts")
                .orderByChild("uid")
                .equalTo(auth.getUid());
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
                Toast.makeText(MyBlogActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        };
        query.addListenerForSingleValueEvent(eventListener);
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
        rv.setLayoutManager(new LinearLayoutManager(MyBlogActivity.this));
        blogAdapter=new MyBlogAdapter(MyBlogActivity.this,_listblog);
        rv.setAdapter(blogAdapter);
        return _listblog;
    }

}
