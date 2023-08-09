package com.example.sample.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sample.Models.MessagesModel;
import com.example.sample.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class ChatAdapter extends RecyclerView.Adapter{
    ArrayList<MessagesModel> messagesModels;
    Context context;

    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<MessagesModel> messagesModels, Context context) {
        this.messagesModels = messagesModels;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(messagesModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER_VIEW_TYPE;
        }
        else {
            return RECEIVER_VIEW_TYPE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent,
                    false);
            return new SenderViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_receiver, parent,
                    false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessagesModel messagesModel = messagesModels.get(position);

        if(holder.getClass() == SenderViewHolder.class) {
            ((SenderViewHolder)holder).senderMessage.setText(messagesModel.getMessage());
            //formatting the time from firebase timestamp
            Long firebaseTimestampInMillis = messagesModel.getTimeStamp();
            Date date = new Date(firebaseTimestampInMillis);

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));

            String formattedTime = dateFormat.format(date);
            //setting the time in chat
            ((SenderViewHolder)holder).senderTime.setText(formattedTime.toString());
        } else {
            ((ReceiverViewHolder)holder).receiverMessage.setText(messagesModel.getMessage());
            Long timeStamp = Long.parseLong(messagesModel.getTimeStamp().toString());

            Long firebaseTimestampInMillis = messagesModel.getTimeStamp();
            Date date = new Date(firebaseTimestampInMillis);

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));

            String formattedTime = dateFormat.format(date);

            ((ReceiverViewHolder)holder).receiverTime.setText(formattedTime.toString());
        }
    }

    @Override
    public int getItemCount() {
        return messagesModels.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {

        TextView receiverMessage, receiverTime;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMessage = itemView.findViewById(R.id.messageReceiver);
            receiverTime = itemView.findViewById(R.id.timeReceiver);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {

        TextView senderMessage, senderTime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMessage = itemView.findViewById(R.id.senderMessage);
            senderTime = itemView.findViewById(R.id.senderTime);
        }
    }
}
