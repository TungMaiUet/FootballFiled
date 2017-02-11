package com.example.tungmai.footballfiled.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tungmai.footballfiled.R;
import com.example.tungmai.footballfiled.main.customevent.RoundedImageView;
import com.example.tungmai.footballfiled.main.model.ItemSearchAddMessage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tungmai on 08/02/2017.
 */

public class ItemSearchAddMessageAdapter extends ArrayAdapter {
    public static final String TAG = "ItemSearchAddMessageAdapter";
    private ArrayList<ItemSearchAddMessage> arrItem;
    private Context context;
    private LayoutInflater inflater;

    public ItemSearchAddMessageAdapter(Context context, ArrayList<ItemSearchAddMessage> arrItem) {
        super(context, R.layout.item_search_add_message, arrItem);
        this.arrItem = arrItem;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrItem.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_search_add_message, parent, false);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.iv_image);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ItemSearchAddMessage itemSearchAddMessage = arrItem.get(position);
        if (itemSearchAddMessage.getUrlImage().isEmpty()) {
            viewHolder.ivImage.setImageResource(R.drawable.user);
        } else {
            Picasso.with(getContext()).load(itemSearchAddMessage.getUrlImage()).into(viewHolder.ivImage);
        }
        viewHolder.tvName.setText(itemSearchAddMessage.getName());
        return convertView;
    }

    class ViewHolder {
        ImageView ivImage;
        TextView tvName;
    }
}
