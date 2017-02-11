package com.example.tungmai.footballfiled.main.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.tungmai.footballfiled.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tungmai on 17/01/2017.
 */

public class ImageDialogAdapter extends ArrayAdapter {
    private static final String TAG = "ImageDialogAdapter";
    private ArrayList<Uri> arrUri;
    private LayoutInflater inflater;
    private Context context;

    public ImageDialogAdapter(Context context, ArrayList<Uri> objects) {
        super(context, R.layout.item_gridview_image_dialog, objects);
        arrUri = objects;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_gridview_image_dialog, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.iv_image);
            viewHolder.ivClose = (ImageView) convertView.findViewById(R.id.iv_close);
            viewHolder.ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arrUri.remove(position);
                    notifyDataSetChanged();
                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ivImage.setImageURI(arrUri.get(position));
//        Picasso.with(context).load(arrUri.get(position));
        return convertView;
    }

    @Override
    public int getCount() {
        return arrUri.size();
    }


    class ViewHolder {
        ImageView ivImage;
        ImageView ivClose;
    }
}
