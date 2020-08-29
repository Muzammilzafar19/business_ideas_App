package com.business_idea.business_ideas_app.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.business_idea.business_ideas_app.DataClasses.GetBlog;
import com.business_idea.business_ideas_app.Fragment.OtherImageView;
import com.business_idea.business_ideas_app.PostBlogsActivity;
import com.business_idea.business_ideas_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyBlogAdapter extends RecyclerView.Adapter<MyBlogAdapter.MyViewHolder> implements Filterable {
private Context context;
private List<GetBlog> blogdata;
private DatabaseReference mRef;

public MyBlogAdapter(Context c, List<GetBlog> blogdata) {
        context = c;
        this.blogdata = blogdata;
        }

@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.my_blog_view, parent, false);
        return new MyViewHolder(view);
        }

public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
        }

@Override
public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetBlog getBlog = blogdata.get(position);
        holder.txtblogtitle.setText(getBlog.getTitle());
        holder.txtblogauthor.setText(getBlog.getAuthor());
        holder.txtblogtimestamp.setText(getBlog.getBlogtime());
        holder.txtblogwritten.setText(getBlog.getBlogwritten());
        holder.txtideacategory.setText(getBlog.getIdeaCategory());
        holder.txtinvestment.setText(getBlog.getInvestment());
        holder.txturiblogger.setText(getBlog.getImgblogger());


        holder.txturiblog.setText(getBlog.getImgblog());
        holder.txtpushid.setText(getBlog.getPushId());
        holder.txtrating.setText(getBlog.getRating()+" :Likes");
        if(Integer.parseInt(getBlog.getRating())>=5)
        {
        holder.ratingBar.setRating(5);
        }
        else {
        holder.ratingBar.setRating(Float.parseFloat(getBlog.getRating()));
        }
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.loading);
        Glide.with(context).load(getBlog.getImgblogger()).apply(requestOptions).into(holder.imgblogger);
        RequestOptions requestOptions1 = new RequestOptions();
        requestOptions.placeholder(R.drawable.loading);
        Glide.with(context).load(getBlog.getImgblog()).apply(requestOptions1).into(holder.imgblog);
        }

@Override
public int getItemCount() {
        return blogdata.size();
        }

@Override
public Filter getFilter() {
        return null;
        }

public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView txtblogtitle, txtblogauthor, txtblogtimestamp, txtblogwritten, txtpushid,txtrating;
    private TextView txtuid, txturiblogger, txturiblog,txtideacategory,txtinvestment;
    private ImageView imgshare, imglike, imgblogger;
    private pl.droidsonroids.gif.GifImageView imgblog;
    private RatingBar ratingBar;
    private Button btndelete,btnedit;
    public MyViewHolder(final View itemView) {
        super(itemView);
        btndelete=itemView.findViewById(R.id.btndeletee);
        btnedit=itemView.findViewById(R.id.btnedit);
        ratingBar=itemView.findViewById(R.id.ratingstar);
        txtblogtitle = itemView.findViewById(R.id.txtblogtitle);
        txtideacategory=itemView.findViewById(R.id.txtideacategory);
        txtinvestment=itemView.findViewById(R.id.txtinvestment);
        txtrating=itemView.findViewById(R.id.txtratings);
        txtpushid = itemView.findViewById(R.id.txtpushid);
        txtuid = itemView.findViewById(R.id.txtuid);
        txturiblogger = itemView.findViewById(R.id.txturiblogger);
        txturiblog = itemView.findViewById(R.id.txturiblog);
        txtblogauthor = itemView.findViewById(R.id.txtblogauthor);
        txtblogtimestamp = itemView.findViewById(R.id.txtblogtimestamp);
        txtblogwritten = itemView.findViewById(R.id.txtblogwritten);
        imgshare = itemView.findViewById(R.id.imgshare);
        imglike = itemView.findViewById(R.id.imglike);
        imgblog = itemView.findViewById(R.id.imgblog);
        imgblogger = itemView.findViewById(R.id.imgblogger);
        imglike = itemView.findViewById(R.id.imglike);


        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                firebaseDatabase = FirebaseDatabase.getInstance();
                mRef = firebaseDatabase.getReference("posts");
                mRef.child(txtpushid.getText().toString()).removeValue();
                Toast.makeText(context, "Post Removed Successfully !", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PostBlogsActivity.class);
                intent.putExtra("title",txtblogtitle.getText().toString());
                intent.putExtra("ideacategory",txtideacategory.getText().toString());
                intent.putExtra("blogauthor",txtblogauthor.getText().toString());
                intent.putExtra("investment",txtinvestment.getText().toString());
                intent.putExtra("pushid",txtpushid.getText().toString());
                intent.putExtra("uid",txtuid.getText().toString());
                intent.putExtra("blogwritten",txtblogwritten.getText().toString());
                context.startActivity(intent);
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                mRef = firebaseDatabase.getReference("posts");
                mRef.child(txtpushid.getText().toString()).removeValue();
                Toast.makeText(context, "Post Removed Successfully !", Toast.LENGTH_SHORT).show();

            }
        });
        imgblogger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaults("bloggeruri", txturiblogger.getText().toString(), context);
                setDefaults("frombloggr", "Yes", context);

                android.app.FragmentManager fm = ((Activity) context).getFragmentManager();
                OtherImageView dialogFragment = new OtherImageView();

                dialogFragment.show(fm, "Sample Fragment");

            }
        });
        imgblog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaults("bloguri", txturiblog.getText().toString(), context);
                setDefaults("fromblog", "Yes", context);
                android.app.FragmentManager fm = ((Activity) context).getFragmentManager();
                OtherImageView dialogFragment = new OtherImageView();

                dialogFragment.show(fm, "Sample Fragment");

            }
        });
        imglike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratechecker(txtblogtimestamp.getText().toString());

                ChildExistOrNot(txtpushid.getText().toString());

                // Toast.makeText(context,"Chiled exist ",Toast.LENGTH_LONG).show();


            }
        });

    }

}

    public void setratinginfo(String pushid) {
        try {

            firebaseDatabase = FirebaseDatabase.getInstance();
            mRef = firebaseDatabase.getReference("postsInfo");
            mRef.child(getDefaults("userid", context)).child(pushid).setValue(pushid);
        } catch (NumberFormatException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();

    }

    public void ratechecker(String datetime) {

        Query query = FirebaseDatabase.getInstance().getReference("posts")
                .orderByChild("postDateTime")
                .equalTo(datetime);

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Add Rating!");
        progressDialog.setIcon(R.drawable.loading);
        progressDialog.setMessage("Please Wait........");
        progressDialog.show();
        ValueEventListener eventListener1 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //create new user
                    progressDialog.dismiss();

                    try {

                        //collectBlog((Map<String,Object>) dataSnapshot.getValue());
                        setDefaults("rating", collectBlog((Map<String, Object>) dataSnapshot.getValue()), context);
                      /*  rv = findViewById(R.id.my_recycler_view);
                        rv.setHasFixedSize(true);
                        rv.setLayoutManager(new LinearLayoutManager(ShowBlogsActivity.this));
                        blogAdapter=new BlogAdapter(ShowBlogsActivity.this,blogdata);
                        rv.setAdapter(blogAdapter);
                        */
                        // Toast.makeText(context,"Hello from inside!"+ dataSnapshot.getChildren(),Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    //  Toast.makeText(DashboardActivity.this,"Value "+name+" "+email,Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        query.addListenerForSingleValueEvent(eventListener1);

    }

    FirebaseDatabase firebaseDatabase;

    private String collectBlog(Map<String, Object> users) {
        String value = "";
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRef = firebaseDatabase.getReference("posts");
        ArrayList<String> phoneNumbers = new ArrayList<>();
        List<GetBlog> _listblog = new ArrayList<>();
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list

            //  _listblog.add(new GetBlog((String) singleUser.get("title"),(String) singleUser.get("authorName"),(String) singleUser.get("postDateTime"),(String) singleUser.get("blog"),(String) singleUser.get("blogImageUri"),(String) singleUser.get("userImage"),"",""));
            value = (String) String.valueOf(singleUser.get("rating"));
            singleUser.put("rating", Long.parseLong(value) + 1);
            //  Toast.makeText(ShowBlogsActivity.this,(String) singleUser.get("title"),Toast.LENGTH_SHORT).show();
            //  Toast.makeText(context,value,Toast.LENGTH_LONG).show();

        }
        //   mRef.getKey().child("rating").setValue(String.valueOf(Integer.parseInt(value)+1));
        return value;

    }

    public boolean ChildExistOrNot(final String value) {
        final boolean[] flag = new boolean[1];
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef=rootRef.child("postsInfo").child(getDefaults("userid",context));
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(value)) {
                    // run some code
                    flag[0] =true;
                    Toast.makeText(context, "You Already Liked this blog", Toast.LENGTH_LONG).show();

                }
                else {
                    try {
                        firebaseDatabase = FirebaseDatabase.getInstance();
                        mRef = firebaseDatabase.getReference("posts");
                        mRef.child(value).child("rating").setValue(String.valueOf(Integer.parseInt(getDefaults("rating", context)) + 1));
                        setratinginfo(value);
                    } catch (NumberFormatException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    flag[0] =false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return flag[0];
    }

}
