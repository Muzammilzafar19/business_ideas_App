package com.business_idea.business_ideas_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.business_idea.business_ideas_app.DataClasses.UserData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileActivity extends AppCompatActivity {
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
    private EditText editName,editEmail,editAge,editUserAbout;
    private DatabaseReference mDatabase,refroot;

    TextView txtupdate;
    ImageView gobackplease;
    private final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        txtuserType=findViewById(R.id.txtuserType);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        init();
imgperson.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
});
        auth=FirebaseAuth.getInstance();
        gobackplease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        editName.setText(getDefaults("username",MyProfileActivity.this));
        editEmail.setText(getDefaults("useremail",MyProfileActivity.this));
        editAge.setText(getDefaults("userAge",MyProfileActivity.this));
        if(getDefaults("userGender",MyProfileActivity.this).equals("Male"))
        {
            checkmale.setChecked(true);
        }
        else {
            checkfemale.setChecked(true);
        }
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.person);

        Glide.with(MyProfileActivity.this).load(getDefaults("userimg",MyProfileActivity.this)).apply(requestOptions).into(imgperson);
        editUserAbout.setText(getDefaults("userAbout",MyProfileActivity.this));
  txtuserType.setText("You are "+getDefaults("usertype",MyProfileActivity.this));
        CheckFn();
  txtupdate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
  });

    }

    public void CheckFn()
    {
        checkmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkmale.isChecked())
                {
                    Gender="Male";
                    checkfemale.setEnabled(false);
                }
                else {
                    checkfemale.setEnabled(true);
                }
            }
        });
        checkfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkfemale.isChecked())
                {
                    Gender="Female";
                    checkmale.setEnabled(false);
                }
                else {
                    checkmale.setEnabled(true);
                }
            }
        });
    }
    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
    public void init()
    {
        checkmale=findViewById(R.id.checkmale);
        checkfemale=findViewById(R.id.checkfemale);
        imgperson=findViewById(R.id.circleview);
        gobackplease=findViewById(R.id.gobackplease);
        txtupdate=findViewById(R.id.txtupdate);

        editName=findViewById(R.id.txteditName);
        editEmail=findViewById(R.id.editEmail);
        editAge=findViewById(R.id.editage);
        editUserAbout=findViewById(R.id.edituserAbout);
    }
    private void writeNewUser(String userId, String name, String email,String Age,String Gender,String UserType,String UserAbout,String ImageUrl) {
        UserData user = new UserData(userId,name, email,Age,Gender,UserType,UserAbout,ImageUrl);
        mDatabase.child("users").child(userId).setValue(user);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgperson.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


            ref = storageReference.child("images/"+ auth.getUid());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri dlUri = uri;

                                    writeNewUser(auth.getUid(),editName.getText().toString(),editEmail.getText().toString(),editAge.getText().toString(),Gender,UserType,editUserAbout.getText().toString(), dlUri.toString());
                                    Toast.makeText(MyProfileActivity.this, "PROFILE SAVED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MyProfileActivity.this,DashboardActivity.class));
                                }
                            });








                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MyProfileActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}
