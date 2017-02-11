package com.example.tungmai.footballfiled.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tungmai.footballfiled.R;
import com.example.tungmai.footballfiled.main.customevent.OnItemClick;
import com.example.tungmai.footballfiled.main.model.ItemInforTab;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tungmai on 10/01/2017.
 */

public class ItemTabAdapter extends RecyclerView.Adapter<ItemTabAdapter.ItemTabInfor> {
    private final static String TAG = "ItemTabAdapter";
    private Context context;
    private ArrayList<ItemInforTab> arrDatas;
    private LayoutInflater inflater;

//    private OnItemClick mOnItemClick;

    public ItemTabAdapter(Context context, ArrayList<ItemInforTab> arrDatas) {
        this.context = context;
        this.arrDatas = arrDatas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ItemTabInfor onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_search_ground, parent, false);
        return new ItemTabInfor(itemView);
    }

    @Override
    public void onBindViewHolder(ItemTabInfor holder, int position) {
        ItemInforTab item = arrDatas.get(position);
        String[] arrUrl = getArrUrl(item.getImages());
        holder.tvTitle.setText(item.getTitle());
        holder.tvName.setText(item.getUserName() + " đã đăng vào lúc " + item.getTime());
        holder.tvNameGroup.setText(item.getNameGroup());
        holder.tvPhone.setText(item.getPhone());
        holder.tvAdress.setText(item.getAddress());
        holder.tvTime.setText(item.getTime());
        holder.tvContent.setText(item.getDescribe());
        if (arrUrl[0].isEmpty()) {
            holder.ivImage_1.setVisibility(View.GONE);
            holder.ivImage_1_2.setVisibility(View.GONE);
            holder.ivImage_2.setVisibility(View.GONE);
            holder.ivImage_3.setVisibility(View.GONE);
        } else if (arrUrl.length == 1) {
            Picasso.with(context).load(arrUrl[0]).into(holder.ivImage_1);
            holder.ivImage_1_2.setVisibility(View.GONE);
            holder.ivImage_2.setVisibility(View.GONE);
            holder.ivImage_3.setVisibility(View.GONE);
        } else if (arrUrl.length == 2) {
            Picasso.with(context).load(arrUrl[0]).into(holder.ivImage_1);
            Picasso.with(context).load(arrUrl[1]).into(holder.ivImage_1_2);
            holder.ivImage_2.setVisibility(View.GONE);
            holder.ivImage_3.setVisibility(View.GONE);
        } else if (arrUrl.length == 3) {
            Picasso.with(context).load(arrUrl[0]).into(holder.ivImage_1);
            Picasso.with(context).load(arrUrl[1]).into(holder.ivImage_2_1);
            Picasso.with(context).load(arrUrl[2]).into(holder.ivImage_2_2);
            holder.ivImage_3.setVisibility(View.GONE);
        } else if (arrUrl.length == 4) {
            Picasso.with(context).load(arrUrl[0]).into(holder.ivImage_1);
            Picasso.with(context).load(arrUrl[1]).into(holder.ivImage_3_1);
            Picasso.with(context).load(arrUrl[2]).into(holder.ivImage_3_2);
            Picasso.with(context).load(arrUrl[3]).into(holder.ivImage_3_2);
            holder.ivImage_2.setVisibility(View.GONE);
        } else if (arrUrl.length > 4) {
            Picasso.with(context).load(arrUrl[0]).into(holder.ivImage_1);
            Picasso.with(context).load(arrUrl[1]).into(holder.ivImage_3_1);
            Picasso.with(context).load(arrUrl[2]).into(holder.ivImage_3_2);
            holder.ivImage_2.setVisibility(View.GONE);
            Picasso.with(context).load(arrUrl[3]).into(holder.ivImage_3_2);
        }

        setOnCLickView(holder, position);
    }

    private void setOnCLickView(ItemTabInfor holder, int position) {
        holder.ivImage_1.setOnClickListener(clickView);
        holder.ivImage_1_2.setOnClickListener(clickView);
        holder.ivImage_2_1.setOnClickListener(clickView);
        holder.ivImage_2_2.setOnClickListener(clickView);
        holder.ivImage_3_1.setOnClickListener(clickView);
        holder.ivImage_3_2.setOnClickListener(clickView);
        holder.ivImage_3_3.setOnClickListener(clickView);
    }

    private View.OnClickListener clickView = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_tab_image_1:

            break;
        }
    }
};




//    public void setOnItemClick(OnItemClick onItemClick) {
//        mOnItemClick = onItemClick;
//    }

public String[]getArrUrl(String arrUrl){
        String[]arrString=arrUrl.split(";");
        return arrString;
        }

@Override
public int getItemCount(){
        return arrDatas.size();
        }

class ItemTabInfor extends RecyclerView.ViewHolder {
    private TextView tvTitle;
    private TextView tvName;
    private TextView tvNameGroup;
    private TextView tvPhone;
    private TextView tvAdress;
    private TextView tvTime;
    private TextView tvContent;
    private ImageView ivImage_1;
    private ImageView ivImage_1_2;
    private LinearLayout ivImage_2;
    private ImageView ivImage_2_1;
    private ImageView ivImage_2_2;
    private LinearLayout ivImage_3;
    private ImageView ivImage_3_1;
    private ImageView ivImage_3_2;
    private ImageView ivImage_3_3;

    public ItemTabInfor(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvName = (TextView) itemView.findViewById(R.id.tv_name_time);
        tvNameGroup = (TextView) itemView.findViewById(R.id.tv_name_football_ground);
        tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
        tvAdress = (TextView) itemView.findViewById(R.id.tv_address);
        tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        ivImage_1 = (ImageView) itemView.findViewById(R.id.iv_tab_image_1);
        ivImage_1_2 = (ImageView) itemView.findViewById(R.id.iv_tab_image_1_2);
        ivImage_2 = (LinearLayout) itemView.findViewById(R.id.iv_tab_image_2);
        ivImage_2_1 = (ImageView) itemView.findViewById(R.id.iv_tab_image_2_1);
        ivImage_2_2 = (ImageView) itemView.findViewById(R.id.iv_tab_image_2_2);
        ivImage_3 = (LinearLayout) itemView.findViewById(R.id.iv_tab_image_3);
        ivImage_3_1 = (ImageView) itemView.findViewById(R.id.iv_tab_image_3_1);
        ivImage_3_2 = (ImageView) itemView.findViewById(R.id.iv_tab_image_3_2);
        ivImage_3_3 = (ImageView) itemView.findViewById(R.id.iv_tab_image_3_3);
    }

}

}
