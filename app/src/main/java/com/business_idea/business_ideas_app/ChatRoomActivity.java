package com.business_idea.business_ideas_app;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.business_idea.business_ideas_app.Adapter.BlogAdapter;
import com.business_idea.business_ideas_app.Adapter.ChatListAdapter;
import com.business_idea.business_ideas_app.DataClasses.ChatListData;
import com.business_idea.business_ideas_app.DataClasses.GetBlog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatRoomActivity extends AppCompatActivity {
    private ImageView _btnback;
    private DatabaseReference mDatabase, refroot;
    public RecyclerView rv;
    private ChatListAdapter chatlistadapter;
    private List<ChatListData> _list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        _btnback = findViewById(R.id._btnback);
        _btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fetchList();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void fetchList() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        refroot = mDatabase.child("users");
        _list = new ArrayList<>();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setIcon(R.drawable.loading);
        progressDialog.setMessage("Please Wait........");
        progressDialog.show();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //create new user
                    progressDialog.dismiss();

                    try {
                        collectBlog((Map<String, Object>) dataSnapshot.getValue());
                      /*  rv = findViewById(R.id.my_recycler_view);
                        rv.setHasFixedSize(true);
                        rv.setLayoutManager(new LinearLayoutManager(ShowBlogsActivity.this));
                        blogAdapter=new BlogAdapter(ShowBlogsActivity.this,blogdata);
                        rv.setAdapter(blogAdapter);
                        */
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    //  Toast.makeText(DashboardActivity.this,"Value "+name+" "+email,Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ChatRoomActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
        refroot.addListenerForSingleValueEvent(eventListener);


    }

    private List<ChatListData> collectBlog(Map<String, Object> users) {

        ArrayList<String> phoneNumbers = new ArrayList<>();
        List<ChatListData> _lis = new ArrayList<>();
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            //phoneNumbers.add((String) singleUser.get("title"));
            _list.add(new ChatListData((String) singleUser.get("userName"), (String) singleUser.get("userType"), (String) singleUser.get("imageUrl")));
            //  Toast.makeText(ShowBlogsActivity.this,(String) singleUser.get("title"),Toast.LENGTH_SHORT).show();
        }
        rv = findViewById(R.id.chatlist_recycle);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(ChatRoomActivity.this));
        chatlistadapter = new ChatListAdapter(ChatRoomActivity.this, _list);
        rv.setAdapter(chatlistadapter);
        return _list;
    }
}
