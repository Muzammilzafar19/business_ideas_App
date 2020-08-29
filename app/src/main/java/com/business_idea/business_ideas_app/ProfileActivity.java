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

import com.business_idea.business_ideas_app.DataClasses.UserData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    CircleImageView imgperson;
    CheckBox checkmale, checkfemale;
    String Gender;
    Uri filePath;
    StorageReference ref;
    String UserType;
    FirebaseStorage storage;
    StorageReference storageReference;
    private FirebaseAuth auth;
    private EditText editName, editEmail, editAge, editUserAbout, editCountry;
    private DatabaseReference mDatabase, refroot;
    private com.rey.material.widget.Switch aSwitch, aSwitch1, aSwitch2;
    TextView txtsave;
    ImageView backimg;
    private final int PICK_IMAGE_REQUEST = 71;

    private Uri imageUri;
    int RESULT_LOAD_IMG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        init();


        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aSwitch.isChecked()) {
                    aSwitch1.setChecked(false);
                    aSwitch2.setChecked(false);
                } else {
                    aSwitch1.setChecked(true);
                    aSwitch2.setChecked(true);
                }
            }
        });
        aSwitch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aSwitch1.isChecked()) {
                    aSwitch.setChecked(false);
                    aSwitch2.setChecked(false);
                } else {
                    aSwitch.setChecked(true);
                    aSwitch2.setChecked(true);
                }
            }
        });
        aSwitch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aSwitch2.isChecked()) {
                    aSwitch.setChecked(false);
                    aSwitch1.setChecked(false);
                } else {
                    aSwitch.setChecked(true);
                    aSwitch1.setChecked(true);
                }
            }
        });


        try {
            auth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
        } catch (Exception e) {
            Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        txtsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (aSwitch.isChecked()) {

                        UserType = "Investor";
                    }
                    if (aSwitch1.isChecked()) {
                        UserType = "Entrepreneur";
                    }
                    if (aSwitch2.isChecked()) {
                        UserType = "Mentor";
                    }


                    uploadImage();


                    //  Toast.makeText(ProfileActivity.this,"Record Successfully Saved",Toast.LENGTH_SHORT).show();
                    //   finish();

                } catch (Exception e) {
                    Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        imgperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                auth = FirebaseAuth.getInstance();
                auth.signOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });
        CheckFn();
    }

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();

    }

    public void CheckFn() {
        checkmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkmale.isChecked()) {
                    Gender = "Male";
                    checkfemale.setEnabled(false);
                } else {
                    checkfemale.setEnabled(true);
                }
            }
        });
        checkfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkfemale.isChecked()) {
                    Gender = "Female";
                    checkmale.setEnabled(false);
                } else {
                    checkmale.setEnabled(true);
                }
            }
        });
    }

    private void writeNewUser(String userId, String name, String email, String Age, String Gender, String UserType, String UserAbout, String ImageUrl) {
        UserData user = new UserData(userId, name, email, Age, Gender, UserType, UserAbout, ImageUrl, editCountry.getText().toString());
        mDatabase.child("users").child(userId).setValue(user);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgperson.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


            ref = storageReference.child("images/" + auth.getUid());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri dlUri = uri;

                                    writeNewUser(auth.getUid(), editName.getText().toString(), editEmail.getText().toString(), editAge.getText().toString(), Gender, UserType, editUserAbout.getText().toString(), dlUri.toString());
                                    Toast.makeText(ProfileActivity.this, "PROFILE SAVED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ProfileActivity.this, DashboardActivity.class));
                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ProfileActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    public void init() {
        checkmale = findViewById(R.id.checkmale);
        checkfemale = findViewById(R.id.checkfemale);
        imgperson = findViewById(R.id.circleview);
        backimg = findViewById(R.id.gobackplease);
        txtsave = findViewById(R.id.txtsave);
        aSwitch = findViewById(R.id.switcher);
        aSwitch1 = findViewById(R.id.switcher1);
        aSwitch2 = findViewById(R.id.switcher2);
        editName = findViewById(R.id.txteditName);
        editEmail = findViewById(R.id.editEmail);
        editAge = findViewById(R.id.editage);
        editUserAbout = findViewById(R.id.edituserAbout);
        editCountry = findViewById(R.id.editcountry);
    }
}
