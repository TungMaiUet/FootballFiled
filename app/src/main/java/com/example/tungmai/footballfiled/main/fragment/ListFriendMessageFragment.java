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
        final String idUser = chatMessageActivity.getIdUser();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user/" + idUser + "/messagers/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    arrChatMessage.clear();
                    String key = data.getKey();
                    DataSnapshot dataSnapshot1 = dataSnapshot.child(key + "/introduction/");
                    String name = (String) dataSnapshot1.child("name").getValue();
                    String content = (String) dataSnapshot1.child("content").getValue();
                    String time = (String) dataSnapshot1.child("time").getValue();
                    String urlImage = (String) dataSnapshot1.child("urlImage").getValue();
//                    Log.e(TAG,name);
                    ChatMessage chatMessage = new ChatMessage("", name, "", "", content, time,urlImage,1);
                    arrChatMessage.add(chatMessage);
//                    DatabaseReference myRefM = database.getReference("user/" + idUser + "/messagers/" + key+"/introduction/");
//                    myRefM.limitToLast(1).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                           String key=dataSnapshot.getKey();
//                            DataSnapshot dataSnapshot1=dataSnapshot.child(key);
//                            Log.e(TAG,dataSnapshot1.toString());
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
                }
                listFriendChatMessageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
