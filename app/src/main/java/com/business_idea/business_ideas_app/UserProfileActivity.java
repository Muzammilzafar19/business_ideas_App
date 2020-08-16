package com.business_idea.business_ideas_app;

import android.app.ProgressDialog;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.business_idea.business_ideas_app.DataClasses.GetBlog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {
    CircleImageView imgperson;
    CheckBox checkmale,checkfemale;
    String Gender;
    Uri filePath;
    StorageReference ref;
    String UserType;
    TextView txtuserType;
    FirebaseStorage storage;
    StorageReference storageReference;
    private FirebaseAuth auth;
    private EditText editName,editEmail,editAge,editUserAbout,editCountry;
    private DatabaseReference mDatabase,refroot;

    TextView txtupdate;
    ImageView gobackplease;
    private final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        init();
        auth=FirebaseAuth.getInstance();
        gobackplease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Toast.makeText(this, getIntent().getExtras().getString("useriddd"), Toast.LENGTH_SHORT).show();
        fetchValue(getIntent().getExtras().getString("useriddd"));
    }
    public void init()
    {
        checkmale=findViewById(R.id.checkmale);
        checkfemale=findViewById(R.id.checkfemale);
        imgperson=findViewById(R.id.circleview);
        gobackplease=findViewById(R.id.gobackplease);
        txtupdate=findViewById(R.id.txtupdate);
txtuserType=findViewById(R.id.txtuserType);
        editName=findViewById(R.id.txteditName);
        editEmail=findViewById(R.id.editEmail);
        editAge=findViewById(R.id.editage);
        editUserAbout=findViewById(R.id.edituserAbout);
        editCountry=findViewById(R.id.editcountry);
    }
    public void fetchValue(String uid)
    {
        Query query = FirebaseDatabase.getInstance().getReference("users")

                .child(uid);

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
                       // Toast.makeText(UserProfileActivity.this, dataSnapshot.child("userName").getValue().toString(), Toast.LENGTH_SHORT).show();
                        editName.setText( dataSnapshot.child("userName").getValue().toString());
                        editEmail.setText( dataSnapshot.child("userEmail").getValue().toString());
                        editAge.setText( dataSnapshot.child("userAge").getValue().toString());
                        editCountry.setText("Pakistan");
                        String gender= dataSnapshot.child("userGender").getValue().toString();
                        if(gender.equals("Male"))
                        {
                            checkmale.setChecked(true);
                        }
                        else {
                            checkfemale.setChecked(true);
                        }
                        RequestOptions requestOptions=new RequestOptions();
                        requestOptions.placeholder(R.drawable.person);

                        Glide.with(UserProfileActivity.this).load( dataSnapshot.child("imageUrl").getValue().toString()).apply(requestOptions).into(imgperson);
                        editUserAbout.setText( dataSnapshot.child("userAbout").getValue().toString());
                      //  Toast.makeText(UserProfileActivity.this, dataSnapshot.child("userType").getValue().toString(), Toast.LENGTH_SHORT).show();
                        String type=dataSnapshot.child("userType").getValue().toString();
                        txtuserType.setText("You are "+ type);
                      //  collectBlog((Map<String,Object>) dataSnapshot.getValue());

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
                Toast.makeText(UserProfileActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
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
            editName.setText((String) singleUser.get("userName"));
            editEmail.setText((String) singleUser.get("userEmail"));
            editAge.setText((String) singleUser.get("userAge"));
            editCountry.setText("Pakistan");
            String gender=(String)singleUser.get("userGender");
            if(gender.equals("Male"))
            {
                checkmale.setChecked(true);
            }
            else {
                checkfemale.setChecked(true);
            }
            RequestOptions requestOptions=new RequestOptions();
            requestOptions.placeholder(R.drawable.person);

            Glide.with(UserProfileActivity.this).load((String) singleUser.get("imageUrl")).apply(requestOptions).into(imgperson);
            editUserAbout.setText((String)singleUser.get("userAbout"));
            txtuserType.setText("You are "+(String)singleUser.get("userType"));
            //  Toast.makeText(ShowBlogsActivity.this,(String) singleUser.get("title"),Toast.LENGTH_SHORT).show();
        }

        return _listblog;
    }
}
