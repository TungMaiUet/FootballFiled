package com.example.tungmai.footballfiled.login;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.tungmai.footballfiled.R;
import com.facebook.FacebookSdk;

/**
 * Created by tungmai on 29/12/2016.
 */

public class MainActivity extends AppCompatActivity {

    private LoginFragment loginFragment=new LoginFragment();
    private RegisterFragment registerFragment=new RegisterFragment();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.color_backgroup_login));

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        initFragments();
    }

    private void initFragments() {
        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,loginFragment);
        fragmentTransaction.commit();
    }

    public void chageFragment(Fragment show,int animationEnter,int animationExit) {
        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(animationEnter,animationExit);

        fragmentTransaction.replace(R.id.framelayout,show).addToBackStack(null);
        fragmentTransaction.commit();
    }

    public LoginFragment getLoginFragment() {
        return loginFragment;
    }

    public RegisterFragment getRegisterFragment() {
        return registerFragment;
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
