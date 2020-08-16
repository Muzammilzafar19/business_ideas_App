package com.business_idea.business_ideas_app.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.business_idea.business_ideas_app.DataClasses.SponserData;
import com.business_idea.business_ideas_app.R;
import com.business_idea.business_ideas_app.UserProfileActivity;

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
       holder.txtid.setText(sd.getSponserId());
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
        private TextView txtName,txtType,txtid;

        private de.hdodenhof.circleimageview.CircleImageView imgperson;

        public MyViewHolder(final View itemView) {
            super(itemView);
     txtName=itemView.findViewById(R.id.txtsponsername);
txtType=itemView.findViewById(R.id.txtsponserType);
imgperson=itemView.findViewById(R.id.imgsponser);
txtid=itemView.findViewById(R.id.txtsponserid);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent=new Intent(context, UserProfileActivity.class);
                    intent.putExtra("useriddd",txtid.getText().toString());
                    context.startActivity(intent);
                    return false;
                }
            });

        }

    }



}
