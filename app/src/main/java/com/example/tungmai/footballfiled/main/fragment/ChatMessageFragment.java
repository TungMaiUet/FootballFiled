package com.example.tungmai.footballfiled.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tungmai.footballfiled.R;

/**
 * Created by tungmai on 26/01/2017.
 */

public class ChatMessageFragment extends Fragment implements View.OnClickListener {

    private View view;
    private RecyclerView recyclerView;
    private EditText edtInput;
    private ImageView ivSend;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_content_message, container, false);
        Toolbar toolbar= (Toolbar) view.findViewById(R.id.toolbar);
//        getActivity().setActionBar(toolbar);
        initViews();
        return view;
    }

    private void initViews() {
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerView_content_message);
        edtInput= (EditText) view.findViewById(R.id.edt_input_content);
        ivSend= (ImageView) view.findViewById(R.id.iv_send_message);
        ivSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_send_message:
                String message=edtInput.getText().toString().trim();
                if(message.isEmpty()){
                    Toast.makeText(getActivity(),"Bạn chưa nhập tin nhắn",Toast.LENGTH_SHORT).show();
                    return;
                }


                break;
        }
    }
}
