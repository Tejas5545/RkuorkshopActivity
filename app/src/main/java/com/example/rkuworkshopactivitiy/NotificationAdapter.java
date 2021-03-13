package com.example.rkuworkshopactivitiy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    ArrayList<Notifications> notifications;
    public NotificationAdapter(ArrayList<Notifications> notifications){
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.MyViewHolder holder, int position) {
        holder.txtTitle.setText(notifications.get(position).getTitle());
        holder.txtMessage.setText(notifications.get(position).getMessage());

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtMessage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtMessage = itemView.findViewById(R.id.txtMessage);
        }
    }
}
