package com.business_idea.business_ideas_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ChatAreaActivity extends AppCompatActivity {
TextView txtName;
ImageView _btnback;
    private de.hdodenhof.circleimageview.CircleImageView imgchat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_area);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        txtName=findViewById(R.id.txtname);
        _btnback=findViewById(R.id._btnback);
        _btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgchat=findViewById(R.id.imgchater);
        txtName.setText(getIntent().getExtras().getString("Name"));
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.person);

        Glide.with(ChatAreaActivity.this).load(getIntent().getExtras().getString("ImageUrl")).apply(requestOptions).into( imgchat);
    }
}
