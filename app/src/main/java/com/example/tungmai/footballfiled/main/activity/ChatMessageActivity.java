package com.example.tungmai.footballfiled.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.tungmai.footballfiled.R;
import com.example.tungmai.footballfiled.main.fragment.AddMessageFragment;
import com.example.tungmai.footballfiled.main.fragment.ChatMessageFragment;
import com.example.tungmai.footballfiled.main.fragment.ListFriendMessageFragment;

/**
 * Created by tungmai on 26/01/2017.
 */

public class ChatMessageActivity extends AppCompatActivity {

    private ChatMessageFragment chatMessageFragment;
    private ListFriendMessageFragment listFriendMessageFragment;
    private AddMessageFragment addMessageFragment;

    private String idUser;
    private String nameUser;
    private String urlImageUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent intent = getIntent();
        idUser = intent.getStringExtra(com.example.tungmai.footballfiled.login.MainActivity.ID_USER);
        nameUser=intent.getStringExtra(MainActivity.NAME_USER);
        urlImageUser=intent.getStringExtra(MainActivity.URL_IMAGE_USER);

        initFragments();
    }

    private void initFragments() {
        chatMessageFragment = new ChatMessageFragment();
        listFriendMessageFragment = new ListFriendMessageFragment();
        addMessageFragment = new AddMessageFragment();
        initListMessageFragment();
    }

    public void initChatMessageFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, chatMessageFragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void initListMessageFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, listFriendMessageFragment);
        fragmentTransaction.commit();
    }

    public void initAddMessageFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, addMessageFragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

    public String getIdUser() {
        return idUser;
    }

    public String getUrlImageUser() {
        return urlImageUser;
    }

    public String getNameUser() {
        return nameUser;
    }
}
