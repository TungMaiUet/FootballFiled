package com.example.tungmai.footballfiled.main.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tungmai.footballfiled.R;
import com.example.tungmai.footballfiled.firebasemain.MainFirebase;
import com.example.tungmai.footballfiled.main.activity.ChatMessageActivity;
import com.example.tungmai.footballfiled.main.adapter.ItemSearchAddMessageAdapter;
import com.example.tungmai.footballfiled.main.customevent.LoadImageListener;
import com.example.tungmai.footballfiled.main.model.ItemSearchAddMessage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Created by tungmai on 08/02/2017.
 */

public class AddMessageFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    public static final String TAG = "AddMessageFragment";
    private ImageView ivBack;
    private Toolbar toolbar;
    private ListView lvSearch;
    private ItemSearchAddMessageAdapter itemSearchAddMessageAdapter;
    private ArrayList<ItemSearchAddMessage> arrItemSearch;

    private View view;
    private RecyclerView recyclerView;
    private EditText edtInput;
    private ImageView ivSend;

    private String idRecipient;
    private SearchView searchView;
    private TextView tvSearch;

    private MainFirebase mainFirebase;
    private String nameRecipient;
    private String urlRecipient;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_message, container, false);
        ivBack = (ImageView) view.findViewById(R.id.iv_back);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        arrItemSearch = new ArrayList<>();
        itemSearchAddMessageAdapter = new ItemSearchAddMessageAdapter(getContext(), arrItemSearch);
        mainFirebase = new MainFirebase(getActivity().getBaseContext());
        initViews();
        return view;
    }

    private void initViews() {
        lvSearch = (ListView) view.findViewById(R.id.lv_search_add_message);
        tvSearch = (TextView) view.findViewById(R.id.tv_search);
        ivBack.setOnClickListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_content_message);
        edtInput = (EditText) view.findViewById(R.id.edt_input_content);
        ivSend = (ImageView) view.findViewById(R.id.iv_send_message);
        ivSend.setOnClickListener(this);
        lvSearch.setAdapter(itemSearchAddMessageAdapter);

        lvSearch.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_send_message:
                String message = edtInput.getText().toString().trim();
                Calendar calendar = Calendar.getInstance();
                String time = calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.MONTH) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
                if (message.isEmpty()) {
                    Toast.makeText(getActivity(), "Bạn chưa nhập tin nhắn", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (idRecipient == null) {
                    Toast.makeText(getActivity(), "Bạn chưa chọn người nhận tin nhắn", Toast.LENGTH_SHORT).show();
                    return;
                }

                ChatMessageActivity chatMessageActivity = (ChatMessageActivity) getActivity();
                String idSender = chatMessageActivity.getIdUser();
                String nameSender = chatMessageActivity.getNameUser();
                String urlImageSender = chatMessageActivity.getUrlImageUser();


                mainFirebase.sendMessage(idSender,nameSender,urlImageSender, idRecipient, nameRecipient, urlRecipient, message, time);
                edtInput.setText("");
                break;
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_search_add_message, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.collapseActionView();
        searchView = new SearchView(((AppCompatActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSearch(newText);
                return false;
            }
        });
    }

    public void getSearch(String text) {
        arrItemSearch.clear();
        lvSearch.setVisibility(View.VISIBLE);
        if (text.isEmpty()) {
            lvSearch.setVisibility(View.INVISIBLE);
            return;
        }
        text = text.toLowerCase();
        lvSearch.setVisibility(View.VISIBLE);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user");
        final String finalText = text;
        myRef.orderByChild("nameUser").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                          @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                                                          @Override
                                                                          public void onDataChange(DataSnapshot dataSnapshot) {
                                                                              HashMap<String, String> map = (HashMap<String, String>) dataSnapshot.getValue();
//                  Log.e(TAG,map.toString());
                                                                              Iterator myVeryOwnIterator = map.keySet().iterator();
                                                                              while (myVeryOwnIterator.hasNext()) {
                                                                                  String key = (String) myVeryOwnIterator.next();
                                                                                  Object valueTemp = (Object) map.get(key);
                                                                                  String strValue = valueTemp.toString();
                                                                                  Log.e(TAG, strValue);
                                                                                  int indexNameUser = strValue.lastIndexOf("nameUser=") + 9;
//                      int index=strValue.indexOf(",",indexNameUser-1);
//                      Log.e(TAG,index+"");
                                                                                  String value;
                                                                                  if (strValue.charAt(strValue.length() - 2) != '}') {
                                                                                      value = strValue.substring(indexNameUser, strValue.length() - 1).toLowerCase();
                                                                                  } else {
                                                                                      value = strValue.substring(indexNameUser, strValue.indexOf(",",indexNameUser)).toLowerCase();
                                                                                  }
                                                                                  int indexImage = strValue.lastIndexOf("imageUser=") + 10;
                                                                                  String image = strValue.substring(indexImage, strValue.indexOf(",", indexImage));

//
                                                                                  if (image == null) image = "";
                                                                                  if (value.contains(finalText)) {
                                                                                      ItemSearchAddMessage itemSearchAddMessage = new ItemSearchAddMessage(key, image, value);
                                                                                      arrItemSearch.add(itemSearchAddMessage);
                                                                                  }
                                                                              }
                                                                              itemSearchAddMessageAdapter.notifyDataSetChanged();
                                                                          }

                                                                          @Override
                                                                          public void onCancelled(DatabaseError databaseError) {

                                                                          }
                                                                      }

        );
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        tvSearch.setText(arrItemSearch.get(position).getName());
        nameRecipient = arrItemSearch.get(position).getName();
        idRecipient = arrItemSearch.get(position).getIdUser();
        urlRecipient = arrItemSearch.get(position).getUrlImage();
        searchView.setQuery(nameRecipient, false);
//        if (!searchView.isIconified()) {
//            searchView.clearFocus();
//            searchView.setIconified(true);
//            Log.e(TAG,searchView.isIconified()+"");
//        }
//        searchView.onActionViewCollapsed();
//        searchView.setVisibility(View.INVISIBLE);
        lvSearch.setVisibility(View.INVISIBLE);
    }
}
