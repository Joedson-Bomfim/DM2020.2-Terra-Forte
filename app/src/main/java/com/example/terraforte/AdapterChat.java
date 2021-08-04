package com.example.terraforte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.Myholer> {

    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIHGT = 1;
    Context context;
    List<ModelChat> chatList;

    FirebaseUser fUser;

    public AdapterChat(Context context, List<ModelChat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public Myholer onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i==MSG_TYPE_RIHGT) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_right, viewGroup, false);
            return new Myholer(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_left, viewGroup, false);
            return new Myholer(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Myholer myholer, int i) {
        String message = chatList.get(i).getMessage();
        String timeStamp = chatList.get(i).getTimestamp();

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(timeStamp));
        //String dateTime = DateFormat.getInstance().format(cal).toString();

        myholer.messageTv.setText(message);
        //myholer.timeTv.setText(dateTime);

        if(i==chatList.size()-1) {
            if(chatList.get(i).isSeen()) {
                myholer.isSeenTv.setText("Vizualisto");
            }else {
                myholer.isSeenTv.setText("Entregue");
            }
        }else {
            myholer.isSeenTv.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        fUser = FirebaseAuth.getInstance().getCurrentUser();

        if(chatList.get(position).getSender().equals(fUser.getUid())) {
            return MSG_TYPE_RIHGT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }

    class Myholer extends RecyclerView.ViewHolder{

        TextView messageTv, timeTv, isSeenTv;

        public Myholer(@NonNull View itemView) {
            super(itemView);

            messageTv = itemView.findViewById(R.id.messageTv);
            timeTv = itemView.findViewById(R.id.timeTv);
            isSeenTv = itemView.findViewById(R.id.isSentTv);
        }
    }
}
