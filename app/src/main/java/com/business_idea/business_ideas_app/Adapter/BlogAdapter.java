package com.business_idea.business_ideas_app.Adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.business_idea.business_ideas_app.DashboardActivity;
import com.business_idea.business_ideas_app.DataClasses.GetBlog;
import com.business_idea.business_ideas_app.Fragment.OtherImageView;
import com.business_idea.business_ideas_app.MainActivity;
import com.business_idea.business_ideas_app.R;

import java.util.List;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.MyViewHolder> implements Filterable {
private Context context;
private List<GetBlog> blogdata;
    public BlogAdapter(Context c,List<GetBlog> blogdata) {
        context=c;
        this.blogdata=blogdata;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.blog_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetBlog getBlog=blogdata.get(position);
        holder.txtblogtitle.setText(getBlog.getTitle());
        holder.txtblogauthor.setText(getBlog.getAuthor());
        holder.txtblogtimestamp.setText(getBlog.getBlogtime());
        holder.txtblogwritten.setText(getBlog.getBlogwritten());
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.loading);
        Glide.with(context).load(getBlog.getImgblogger()).apply(requestOptions).into(holder.imgblogger);
        RequestOptions requestOptions1=new RequestOptions();
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
        private TextView txtblogtitle,txtblogauthor,txtblogtimestamp,txtblogwritten;
        private ImageView imgshare,imglike,imgblogger;
private pl.droidsonroids.gif.GifImageView imgblog;

        public MyViewHolder(final View itemView) {
            super(itemView);
txtblogtitle=itemView.findViewById(R.id.txtblogtitle);
txtblogauthor=itemView.findViewById(R.id.txtblogauthor);
txtblogtimestamp=itemView.findViewById(R.id.txtblogtimestamp);
txtblogwritten=itemView.findViewById(R.id.txtblogwritten);
imgshare=itemView.findViewById(R.id.imgshare);
imglike=itemView.findViewById(R.id.imglike);
imgblog=itemView.findViewById(R.id.imgblog);
imgblogger=itemView.findViewById(R.id.imgblogger);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   /* Intent intent = new Intent(context, Patient_ProfileActivity.class);
                    intent.putExtra("Name",name.getText().toString());
                    intent.putExtra("Detail",Id.getText().toString());
                    intent.putExtra("ImageUrl",hiddenfield.getText().toString());
                    context.startActivity(intent);*/



                }
            });
            imgblogger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    android.app.FragmentManager fm = ((Activity) context).getFragmentManager();
                    OtherImageView dialogFragment = new OtherImageView();
                    dialogFragment.show(fm, "Sample Fragment");

                }
            });
            imgblog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

    }

}
