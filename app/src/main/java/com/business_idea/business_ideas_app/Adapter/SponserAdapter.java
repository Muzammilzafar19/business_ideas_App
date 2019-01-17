package com.business_idea.business_ideas_app.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.business_idea.business_ideas_app.DashboardActivity;
import com.business_idea.business_ideas_app.DataClasses.GetBlog;
import com.business_idea.business_ideas_app.DataClasses.SponserData;
import com.business_idea.business_ideas_app.R;

import java.util.List;

public class SponserAdapter extends RecyclerView.Adapter<SponserAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<SponserData> sponserdata;

    public SponserAdapter(Context c,List<SponserData> sponserdata)
    {
        context=c;
        this.sponserdata=sponserdata;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.sponser_view, parent, false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull SponserAdapter.MyViewHolder holder, int position) {
       SponserData sd=sponserdata.get(position);
       holder.txtName.setText(sd.getSponserName());
       holder.txtType.setText(sd.getSponserType());
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.person);

        Glide.with(context).load(sd.getSponserImgUri()).apply(requestOptions).into(holder.imgperson);
    }

    @Override
    public int getItemCount() {
        return sponserdata.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName,txtType;

        private de.hdodenhof.circleimageview.CircleImageView imgperson;

        public MyViewHolder(final View itemView) {
            super(itemView);
     txtName=itemView.findViewById(R.id.txtsponsername);
txtType=itemView.findViewById(R.id.txtsponserType);
imgperson=itemView.findViewById(R.id.imgsponser);


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

        }

    }



}
