package com.example.tungmai.footballfiled.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tungmai.footballfiled.R;
import com.example.tungmai.footballfiled.main.model.ChatMessage;

import java.util.ArrayList;

/**
 * Created by tungmai on 25/01/2017.
 */

public class MessageChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<ChatMessage> arrChatMessage;
    private LayoutInflater inflater;

    public static final int SENDER = 0;
    public static final int RECIPIENT = 1;

    public MessageChatAdapter(Context context, ArrayList<ChatMessage> arrChatMessage) {
        this.context = context;
        this.arrChatMessage = arrChatMessage;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (arrChatMessage.get(position).getmRecipientOrSenderStatus() == SENDER) {
            return SENDER;
        } else {
            return RECIPIENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        if (viewType == SENDER) {
            view = inflater.inflate(R.layout.item_content_message_sender, parent, false);
            viewHolder = new ViewHolderSender(view);
        } else {
            view = inflater.inflate(R.layout.item_content_message_recipient, parent, false);
            viewHolder = new ViewHolderRecipient(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case SENDER:
                ViewHolderSender viewHolderSender = (ViewHolderSender) viewHolder;
                configureSenderView(viewHolderSender, position);
                break;
            case RECIPIENT:
                ViewHolderRecipient viewHolderRecipient = (ViewHolderRecipient) viewHolder;
                configureRecipientView(viewHolderRecipient, position);
                break;
        }

    }

    private void configureRecipientView(ViewHolderRecipient viewHolderRecipient, int position) {
        ChatMessage chatMessage = arrChatMessage.get(position);
        viewHolderRecipient.tvContent.setText(chatMessage.getContent());
    }

    private void configureSenderView(ViewHolderSender viewHolderSender, int position) {
        ChatMessage chatMessage = arrChatMessage.get(position);
        viewHolderSender.tvContent.setText(chatMessage.getContent());
//        viewHolderSender.tvStateMessage.setText();

    }

    @Override
    public int getItemCount() {
        return arrChatMessage.size();
    }

    class ViewHolderSender extends RecyclerView.ViewHolder {
        private TextView tvTime;
        private TextView tvStateMessage;
        private TextView tvContent;

        public ViewHolderSender(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvStateMessage = (TextView) itemView.findViewById(R.id.tv_state_message);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }

    class ViewHolderRecipient extends RecyclerView.ViewHolder {

        private TextView tvTime;
        private TextView tvContent;

        public ViewHolderRecipient(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);

        }
    }
}
