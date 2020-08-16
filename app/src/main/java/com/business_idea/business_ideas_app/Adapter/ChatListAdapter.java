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
import com.business_idea.business_ideas_app.ChatAreaActivity;
import com.business_idea.business_ideas_app.DataClasses.ChatListData;
import com.business_idea.business_ideas_app.R;
import com.business_idea.business_ideas_app.UserProfileActivity;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder> implements Filterable {
List<ChatListData> _list;
Context context;
public ChatListAdapter(Context c,List<ChatListData> _list)
{
   context=c;
   this._list=_list;
}


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.chatlist_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatListData cd=_list.get(position);
        holder.txtChatName.setText(cd.getChaterName());
        holder.txtChaterType.setText(cd.getChaterType());
        holder.txthiddenuri.setText(cd.getChaterImg());
        holder.txtchaterid.setText(cd.getChaterId());
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.person);

        Glide.with(context).load(cd.getChaterImg()).apply(requestOptions).into(holder.imgChater);
    }

    @Override
    public int getItemCount() {
        return _list.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtChatName,txtChaterType,txthiddenuri,txtchaterid;

        private de.hdodenhof.circleimageview.CircleImageView imgChater;

        public MyViewHolder(final View itemView) {
            super(itemView);
            txtChatName=itemView.findViewById(R.id.txtchatername);
            txthiddenuri=itemView.findViewById(R.id.txthiddenuri);
            txtChaterType=itemView.findViewById(R.id.txtChaterType);
            imgChater=itemView.findViewById(R.id.imgChat);
            txtchaterid=itemView.findViewById(R.id.txtChaterId);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, ChatAreaActivity.class);
                    intent.putExtra("Name",txtChatName.getText().toString());

                    intent.putExtra("ImageUrl",txthiddenuri.getText().toString());
                    context.startActivity(intent);



                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(context, UserProfileActivity.class);
                    intent.putExtra("useriddd",txtchaterid.getText().toString());


                    context.startActivity(intent);
                    return false;
                }
            });

        }

    }

}
