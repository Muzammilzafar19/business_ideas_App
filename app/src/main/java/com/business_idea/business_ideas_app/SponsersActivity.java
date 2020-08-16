package com.business_idea.business_ideas_app;

import android.app.ProgressDialog;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.business_idea.business_ideas_app.Adapter.SponserAdapter;
import com.business_idea.business_ideas_app.DataClasses.SponserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SponsersActivity extends AppCompatActivity {
private SponserData sponserData;
private SponserAdapter sponserAdapter;
private List<SponserData> _list;
private FirebaseDatabase firebaseDatabase;
    public RecyclerView rv;
    private ImageView _btnback;
private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsers);
        _btnback=findViewById(R.id._btnback);
        _btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fetch();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // startActivity(new Intent(SponsersActivity.this,DashboardActivity.class));
        finish();
    }
   public void fetch()
   {
       Query query = FirebaseDatabase.getInstance().getReference("users")
               .orderByChild("userType")
               .equalTo("Investor");
       _list=new ArrayList<>();
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
               Toast.makeText(SponsersActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
           }
       };
       query.addListenerForSingleValueEvent(eventListener);
   }

    private List<SponserData> collectBlog(Map<String,Object> users) {

        ArrayList<String> phoneNumbers = new ArrayList<>();
        List<SponserData> _list=new ArrayList<>();
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            phoneNumbers.add((String) singleUser.get("title"));
            _list.add(new SponserData((String) singleUser.get("userName"),(String) singleUser.get("imageUrl"),(String) singleUser.get("userType"),(String) singleUser.get("uid")));
            //  Toast.makeText(ShowBlogsActivity.this,(String) singleUser.get("title"),Toast.LENGTH_SHORT).show();
        }
        rv = findViewById(R.id.sponser_recycle);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(SponsersActivity.this));
        sponserAdapter=new SponserAdapter(SponsersActivity.this,_list);
        rv.setAdapter(sponserAdapter);
        return _list;
    }
}
