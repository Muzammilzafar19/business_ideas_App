package com.business_idea.business_ideas_app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.business_idea.business_ideas_app.DataClasses.PostData;
import com.business_idea.business_ideas_app.DataClasses.UserData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rey.material.widget.EditText;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PostBlogsActivity extends AppCompatActivity {
private TextView txtcancel,txtpost,txtideacategory,txtpostedit;
 private com.business_idea.business_ideas_app.customfonts.MyEditText txtTitle,txtAuthor,_txtblog;
private ImageView imgblog;
private static String  count;
String usecount;
    Uri filePath;
   private FirebaseAuth auth;
   private StorageReference storageReference,ref;
    private final int PICK_IMAGE_REQUEST = 1;
  private   FirebaseStorage storage;
    private DatabaseReference mRef,refroot;
    private FirebaseDatabase firebaseDatabase;
    private com.toptoche.searchablespinnerlibrary.SearchableSpinner spinnerpanel,spinnerrange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_blogs);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        FirebaseApp.initializeApp(this);
        txtcancel=findViewById(R.id.txtcancel);
        spinnerpanel=findViewById(R.id.spinnerpanel);
        spinnerrange=findViewById(R.id.spinnerrange);
        txtpost=findViewById(R.id.txtpost);
        txtTitle=findViewById(R.id.txtTitle);
        txtAuthor=findViewById(R.id.txtAuthor);
        _txtblog=findViewById(R.id._txtblog);
        imgblog=findViewById(R.id.imgforblog);
        txtpostedit=findViewById(R.id.txtpostedit);




        auth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        mRef=firebaseDatabase.getReference("posts");
        // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        txtcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgblog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        categorychossing();
        investmentrange();

txtpost.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        try {
            FirebaseMessaging.getInstance().subscribeToTopic("pushNotificationForBlog");

            getChildCount();

           uploadImage(getDefaults("postcount",PostBlogsActivity.this));
           // writeNewPost(auth.getUid(),txtTitle.getText().toString(),txtAuthor.getText().toString(),_txtblog.getText().toString(),"","","","12");

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        // Toast.makeText(getApplicationContext(),getChildCount(),Toast.LENGTH_SHORT).show();
    }
});
        try{
            _txtblog.setText(getIntent().getExtras().getString("blogwritten"));
            txtTitle.setText(getIntent().getExtras().getString("title"));
            txtAuthor.setText(getIntent().getExtras().getString("blogauthor"));
            txtpost.setVisibility(View.GONE);
            txtpostedit.setVisibility(View.VISIBLE);
            txtpostedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseMessaging.getInstance().subscribeToTopic("pushNotificationForBlog");

                    getChildCount();

                    uploadImageEdit(getDefaults("postcount",PostBlogsActivity.this));
                }
            });
        }catch (Exception ex)
        {

        }
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
                imgblog.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    public static void setDefaults(String key,String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();

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
    public void categorychossing()
    {
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerpanel.setTitle("Ideas Categories");
        spinnerArray.add("Select Categories");

        spinnerArray.add("App Developer");
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
    public String getChildCount()
    {
        firebaseDatabase =FirebaseDatabase.getInstance();
            refroot=firebaseDatabase.getReference("posts");
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Checking Post");
            progressDialog.setIcon(R.drawable.authpic);
            progressDialog.setMessage("Please Wait.........");
            progressDialog.show();
            refroot.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.exists()) {
                        //create new user
                        progressDialog.dismiss();



                        count= "0";

setDefaults("postcount",count,PostBlogsActivity.this);
                        //Toast.makeText(DashboardActivity.this,"Value "+name+" "+email,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        count= String.valueOf(dataSnapshot.getChildrenCount());
                        setDefaults("postcount",count,PostBlogsActivity.this);

                       // Toast.makeText(getApplicationContext(),count,Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
Toast.makeText(PostBlogsActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

            return count;




    }
    private void writeNewPost(String userId, String title, String author,String blog,String blogimg,String blogdatetime,String userimg,String use1,String ideacategory,String investment) {
      //  PostData postblog = new PostData(title,author,blog,blogimg,userId,userimg,blogdatetime,"0");
       long c=Integer.parseInt(use1)+1;
       String pushid=mRef.push().getKey();
      // mRef.push().setValue(new PostData(title,author,blog,blogimg,userId,userimg,blogdatetime,"0"));
        mRef.child(pushid).setValue(new PostData(title,author,blog,blogimg,userId,userimg,blogdatetime,"0",pushid,ideacategory,investment));
    }
    private void writeEditPost(String pushid,String userId, String title, String author,String blog,String blogimg,String blogdatetime,String userimg,String use1,String ideacategory,String investment) {
        //  PostData postblog = new PostData(title,author,blog,blogimg,userId,userimg,blogdatetime,"0");
        long c=Integer.parseInt(use1)+1;
      //  String pushid=mRef.push().getKey();
        // mRef.push().setValue(new PostData(title,author,blog,blogimg,userId,userimg,blogdatetime,"0"));
        mRef.child(pushid).setValue(new PostData(title,author,blog,blogimg,userId,userimg,blogdatetime,"0",pushid,ideacategory,investment));
    }
    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
    private void uploadImage(final String use1) {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading Blog...");

            progressDialog.show();


            ref = storageReference.child("post_images/"+ "p_"+String.valueOf(Integer.parseInt(use1)+1));
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri dlUri = uri;
                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
                                    String datetime = dateformat.format(c.getTime());
                                    writeNewPost(auth.getUid(),txtTitle.getText().toString(),txtAuthor.getText().toString(),_txtblog.getText().toString(),getDefaults("userimg",PostBlogsActivity.this),datetime,dlUri.toString(),use1,spinnerpanel.getSelectedItem().toString(),spinnerrange.getSelectedItem().toString());
                                    Toast.makeText(PostBlogsActivity.this, "Blog Uploaded", Toast.LENGTH_SHORT).show();
                                   // startActivity(new Intent(PostBlogsActivity.this,DashboardActivity.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(PostBlogsActivity.this,"Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(PostBlogsActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
        else {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
            String datetime = dateformat.format(c.getTime());
            writeNewPost(auth.getUid(),txtTitle.getText().toString(),txtAuthor.getText().toString(),_txtblog.getText().toString(),getDefaults("userimg",PostBlogsActivity.this),datetime,"",use1,spinnerpanel.getSelectedItem().toString(),spinnerrange.getSelectedItem().toString());
            Toast.makeText(PostBlogsActivity.this,"Image Not Uplaod But Blog is Saved",Toast.LENGTH_LONG).show();
        }
    }
    private void uploadImageEdit(final String use1) {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading Blog...");

            progressDialog.show();


            ref = storageReference.child("post_images/"+ "p_"+String.valueOf(Integer.parseInt(use1)+1));
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri dlUri = uri;
                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
                                    String datetime = dateformat.format(c.getTime());
                                    writeEditPost(getIntent().getExtras().getString("pushid"),auth.getUid(),txtTitle.getText().toString(),txtAuthor.getText().toString(),_txtblog.getText().toString(),getDefaults("userimg",PostBlogsActivity.this),datetime,dlUri.toString(),use1,spinnerpanel.getSelectedItem().toString(),spinnerrange.getSelectedItem().toString());
                                    Toast.makeText(PostBlogsActivity.this, "Blog Uploaded", Toast.LENGTH_SHORT).show();
                                    // startActivity(new Intent(PostBlogsActivity.this,DashboardActivity.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(PostBlogsActivity.this,"Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(PostBlogsActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
        else {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
            String datetime = dateformat.format(c.getTime());
            writeEditPost(getIntent().getExtras().getString("pushid"),auth.getUid(),txtTitle.getText().toString(),txtAuthor.getText().toString(),_txtblog.getText().toString(),getDefaults("userimg",PostBlogsActivity.this),datetime,"",use1,spinnerpanel.getSelectedItem().toString(),spinnerrange.getSelectedItem().toString());
            Toast.makeText(PostBlogsActivity.this,"Image Not Uplaod But Blog is Saved",Toast.LENGTH_LONG).show();
        }
    }
}
