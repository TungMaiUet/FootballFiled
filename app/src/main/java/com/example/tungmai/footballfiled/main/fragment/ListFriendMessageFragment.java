package com.example.tungmai.footballfiled.main.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tungmai.footballfiled.R;
import com.example.tungmai.footballfiled.main.activity.ChatMessageActivity;
import com.example.tungmai.footballfiled.main.activity.MainActivity;
import com.example.tungmai.footballfiled.main.adapter.ListFriendChatMessageAdapter;
import com.example.tungmai.footballfiled.main.model.ChatMessage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tungmai on 26/01/2017.
 */

public class ListFriendMessageFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {
    public static final String TAG = "ListFriendMessageFragment";

    private View view;
    private ListView lvFriendChat;

    private ListFriendChatMessageAdapter listFriendChatMessageAdapter;
    private ArrayList<ChatMessage> arrChatMessage;
    private FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_message, container, false);
        arrChatMessage = new ArrayList<>();
//        arrChatMessage.add(new ChatMessage("khdfaskdfh124","fdsafsafa","add","sdvxc","fsdafa","sfdafa",0));
        listFriendChatMessageAdapter = new ListFriendChatMessageAdapter(getActivity().getBaseContext(), arrChatMessage);
        initViews();
        getData();
        return view;
    }

    private void initViews() {
        lvFriendChat = (ListView) view.findViewById(R.id.lv_friend_chat);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingactionbutton);
        lvFriendChat.setAdapter(listFriendChatMessageAdapter);

        lvFriendChat.setOnItemClickListener(this);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatMessageActivity chatMessageActivity = (ChatMessageActivity) getActivity();
                chatMessageActivity.initAddMessageFragment();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void getData() {
        ChatMessageActivity chatMessageActivity = (ChatMessageActivity) getActivity();
        String idUser = chatMessageActivity.getIdUser();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user/" + idUser + "/messagers/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                    DataSnapshot dataSnapshot1=dataSnapshot.child(dataSnapshot.getKey());
//                    String keyMessager = dataSnapshot.child((String) dataSnapshot.getValue()).getKey();
                    Log.e(TAG, dataSnapshot1.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
