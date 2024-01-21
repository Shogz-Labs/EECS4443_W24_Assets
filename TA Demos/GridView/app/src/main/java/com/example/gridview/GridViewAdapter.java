package com.example.gridview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.CustomViewHolder>{
    Context context;
    ArrayList<String> friends;
    ArrayList<Integer> images;
    ArrayList<String> status;
    LayoutInflater inflater;

    Bundle b = new Bundle();
    public GridViewAdapter(Context context, ArrayList<String> friends, ArrayList<Integer> images, ArrayList<String> status) {
        this.context = context;
        this.friends = friends;
        this.images = images;
        this.status = status;
        this.inflater = LayoutInflater.from(context);
    }



    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.friend_list_item, parent, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.avatar.setImageResource(this.images.get(position));
        holder.username.setText(this.friends.get(position));
        holder.user_status.setText(this.status.get(position));
        b.putInt(holder.username.getText().toString(), this.images.get(position));



    }

    @Override
    public int getItemCount() {
        return this.friends.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        TextView username;
        TextView user_status;


        @SuppressLint("ClickableViewAccessibility")
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.FriendImageView);
            username = itemView.findViewById(R.id.FriendName);
            user_status = itemView.findViewById(R.id.FriendStatus);
            itemView.setOnTouchListener((view, motionEvent) -> {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    System.out.println(b.getInt(username.getText().toString()));
                }
                return false;
            });
        }
    }
}
