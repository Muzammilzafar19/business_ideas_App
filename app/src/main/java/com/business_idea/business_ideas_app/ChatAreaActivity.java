package com.business_idea.business_ideas_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ChatAreaActivity extends AppCompatActivity {
    TextView txtName;
    ImageView _btnback;
    LinearLayout layout;
    ImageView sendButton,btndelete;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2;
    private DatabaseReference mDatabase,refroot;
    private de.hdodenhof.circleimageview.CircleImageView imgchat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_area);
        btndelete=findViewById(R.id._btndelete);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        txtName = findViewById(R.id.txtname);
        _btnback = findViewById(R.id._btnback);
        _btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgchat = findViewById(R.id.imgchater);
        txtName.setText(getIntent().getExtras().getString("Name"));
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.person);

        Glide.with(ChatAreaActivity.this).load(getIntent().getExtras().getString("ImageUrl")).apply(requestOptions).into(imgchat);

        layout = (LinearLayout) findViewById(R.id.layout1);
        sendButton = (ImageView) findViewById(R.id.sendButton);
        messageArea = (EditText) findViewById(R.id.messageArea);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://businessideas-deedb.firebaseio.com/messages/" + getIntent().getExtras().getString("Name") + "_" + getDefaults("username",ChatAreaActivity.this));
        reference2 = new Firebase("https://businessideas-deedb.firebaseio.com/messages/" + getDefaults("username",ChatAreaActivity.this) + "_" + getIntent().getExtras().getString("Name"));

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if (!messageText.equals("")) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", getDefaults("username",ChatAreaActivity.this));
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                }
                messageArea.setText("");
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if (userName.equals(getDefaults("username",ChatAreaActivity.this))) {
                    addMessageBox("You:-\n" + message, 1);
                } else {
                    addMessageBox(txtName.getText().toString() + ":-\n" + message, 2);

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase= FirebaseDatabase.getInstance().getReference();
                refroot=mDatabase.child("messages");
refroot.child( getIntent().getExtras().getString("Name") + "_" + getDefaults("username",ChatAreaActivity.this)).removeValue();
                Toast.makeText(ChatAreaActivity.this, "Message Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
    public void alert()
    {
        try {
            /*Must be run one time*/
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addMessageBox(String message, int type){
        TextView textView = new TextView(ChatAreaActivity.this);
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 10);
        textView.setLayoutParams(lp);

        if(type == 1) {
            textView.setBackgroundResource(R.drawable.rounded_corner1);
        }
        else{
            textView.setBackgroundResource(R.drawable.rounded_corner2);
        }

        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}


