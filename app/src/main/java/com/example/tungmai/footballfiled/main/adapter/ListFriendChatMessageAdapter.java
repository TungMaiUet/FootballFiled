package com.example.tungmai.footballfiled.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tungmai.footballfiled.R;
import com.example.tungmai.footballfiled.main.customevent.RoundedImageView;
import com.example.tungmai.footballfiled.main.model.ChatMessage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.CheckedOutputStream;

/**
 * Created by tungmai on 27/01/2017.
 */

public class ListFriendChatMessageAdapter extends ArrayAdapter {

    private ArrayList<ChatMessage> arrChatMessage;
    private Context context;
    private LayoutInflater inflater;

    public ListFriendChatMessageAdapter(Context context, ArrayList<ChatMessage> arrChatMessage) {
        super(context, R.layout.item_lv_chat_message, arrChatMessage);
        this.arrChatMessage = arrChatMessage;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrChatMessage.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_lv_chat_message, parent, false);
            viewHolder.ivContact = (RoundedImageView) convertView.findViewById(R.id.iv_message);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content_message);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ChatMessage message = arrChatMessage.get(position);

        viewHolder.tvName.setText(message.getNameRecipient());
        viewHolder.tvContent.setText(message.getContent());
        viewHolder.tvDate.setText(message.getTime());

        return convertView;
    }

    class ViewHolder {
        RoundedImageView ivContact;
        TextView tvName;
        TextView tvDate;
        TextView tvContent;
    }
}
