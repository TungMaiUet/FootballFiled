package com.example.tungmai.footballfiled.main.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.tungmai.footballfiled.R;
import com.example.tungmai.footballfiled.main.adapter.ItemTabAdapter;
import com.example.tungmai.footballfiled.main.model.ItemInforTab;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by tungmai on 08/01/2017.
 */

public class Tab1 extends Fragment {

    private View view;
    private Spinner spinnerProvince;
    private Spinner spinnerDistrict;
    private RecyclerView recyclerView;
    private ItemTabAdapter itemTabAdapter;

    private ArrayList<String> arrSpinnerProvince = new ArrayList<>();
    private ArrayList<String> arrSpinnerDistrict = new ArrayList<>();

    private ArrayList<ItemInforTab> arrItem;
    private static final String TAG = "Tab1";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tablayout1, container, false);
        arrItem = new ArrayList<>();
        itemTabAdapter = new ItemTabAdapter(getActivity(), arrItem);
        getDataToFirebase();
        initViews();
        return view;
    }

    private void getDataToFirebase() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("writen");
        final DatabaseReference myRefUser = database.getReference("user");
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.show();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                arrItem.clear();
                int count = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    final ItemInforTab value = data.getValue(ItemInforTab.class);
                    final int finalCount = count;
                    myRefUser.child(value.getIdUser()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshotUser) {
                            String userName = (String) dataSnapshotUser.child("nameUser").getValue();
                            value.setUserName(userName);
                            Log.e(TAG,finalCount+":"+dataSnapshot.getChildrenCount()+"");
                            arrItem.add(value);
                            if (finalCount == dataSnapshot.getChildrenCount() - 1) {
                                Log.e(TAG,dataSnapshot.getChildren()+"");
                                progressDialog.dismiss();
                                itemTabAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    count++;
//                    Log.e(TAG,value.getUserName());
                }
//                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void initViews() {
        spinnerProvince = (Spinner) view.findViewById(R.id.spinner_province);
        ArrayAdapter<String> adapterProvince = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrSpinnerProvince);
        adapterProvince.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerProvince.setAdapter(adapterProvince);

        spinnerDistrict = (Spinner) view.findViewById(R.id.spinner_district);
        ArrayAdapter<String> adapterDistrict = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrSpinnerDistrict);
        adapterDistrict.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerProvince.setAdapter(adapterDistrict);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemTabAdapter);

    }


}
