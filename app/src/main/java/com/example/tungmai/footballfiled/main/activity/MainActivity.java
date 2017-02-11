package com.example.tungmai.footballfiled.main.activity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tungmai.footballfiled.R;
import com.example.tungmai.footballfiled.firebasemain.MainFirebase;
import com.example.tungmai.footballfiled.main.adapter.PageTabAdapter;
import com.example.tungmai.footballfiled.main.customevent.LoadImageListener;
import com.example.tungmai.footballfiled.main.customevent.RoundedImageView;
import com.example.tungmai.footballfiled.main.dialog.WriteDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by tungmai on 08/01/2017.
 */

public class MainActivity extends AppCompatActivity {
    public final static String TAG = "MainActivity";
    public static final String NAME_USER = "name user";
    public static final String URL_IMAGE_USER = "url image user";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    private TextView tvNameUser;
    private RoundedImageView ivUser;

//    private ImageView tvImageUser;
//    private TextView tvNameUser;

    private WriteDialog writeDialog;
    private MainFirebase mainFirebase;
    private String idUserName;
    private static final int REQUEST_CODE_ACTIVITY_RESULT = 87;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        idUserName = intent.getStringExtra(com.example.tungmai.footballfiled.login.MainActivity.ID_USER);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainFirebase = new MainFirebase(this);

        initDrawerLayout();
        initTabsLayout();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initTabsLayout() {
        tabLayout = (TabLayout) findViewById(R.id.tabs_layout);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("Field"));
        tabLayout.addTab(tabLayout.newTab().setText("Rival"));
        tabLayout.addTab(tabLayout.newTab().setText("Person"));
        tabLayout.addTab(tabLayout.newTab().setText("Calendar"));

        tabLayout.setTabTextColors(getResources().getColorStateList(R.color.color_text_tablayout));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorBackgroudTabLayout));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        PageTabAdapter pageTabAdapter = new PageTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageTabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initDrawerLayout() {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setItemTextColor(getResources().getColorStateList(R.color.color_text_navigation));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.home:
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.message:
                        Intent intentMessage = new Intent(getApplication(), ChatMessageActivity.class);
                        intentMessage.putExtra(com.example.tungmai.footballfiled.login.MainActivity.ID_USER, idUserName);
                        intentMessage.putExtra(NAME_USER,nameUser);
                        intentMessage.putExtra(URL_IMAGE_USER,urlImage);
                        startActivity(intentMessage);
                        break;
                    case R.id.settings:
                        Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.trash:
                        Toast.makeText(getApplicationContext(), "Saved news", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        drawerLayout.closeDrawers();
                        finish();


                }
                return true;
            }
        });

        View header = navigationView.getHeaderView(0);
        ivUser = (RoundedImageView) header.findViewById(R.id.iv_image);
        tvNameUser = (TextView) header.findViewById(R.id.tv_email);
        getValue(idUserName);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private String nameUser;
    private String urlImage;

    public void getValue(String idUser) {
//        final ProgressDialog dialog=new ProgressDialog(getBaseContext());
//        dialog.setCancelable(false);
//        dialog.show();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("user/" + idUser);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameUser = (String) dataSnapshot.child("nameUser").getValue();
                urlImage = (String) dataSnapshot.child("imageUser").getValue();
                tvNameUser.setText(nameUser + "");
                if (urlImage.isEmpty())
                    ivUser.setImageDrawable(getResources().getDrawable(R.drawable.user));
                else {
                    Picasso.with(getBaseContext()).load(urlImage).into(ivUser);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_write:
                writeDialog = new WriteDialog();
                Bundle bundle = new Bundle();
                bundle.putString(com.example.tungmai.footballfiled.login.MainActivity.ID_USER, idUserName);
                writeDialog.setArguments(bundle);
//                writeDialog.setTargetFragment(this,REQUEST_CODE_ACTIVITY_RESULT);
                writeDialog.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_NoActionBar);
                writeDialog.show(getFragmentManager(), TAG);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private LoadImageListener loadImageListener;

    public void setLoadImageListener(LoadImageListener loadImageListener) {
        this.loadImageListener = loadImageListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        writeDialog.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                ClipData clipData = data.getClipData();
                writeDialog.setClipData(clipData);
                loadImageListener.loadImage();
            }
        }
    }
}