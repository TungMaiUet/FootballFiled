package com.example.tungmai.footballfiled.login;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.tungmai.footballfiled.R;
import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by tungmai on 29/12/2016.
 */

public class MainActivity extends AppCompatActivity {

    private LoginFragment loginFragment = new LoginFragment();
    private RegisterFragment registerFragment = new RegisterFragment();

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private static final String TAG = "MainActivity";

    private int stateLogin;
    public static final String ID_USER="user";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.color_backgroup_login));

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        stateLogin = 0;
        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.e(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    if (stateLogin == 0 && FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                        Intent intent = new Intent(MainActivity.this, com.example.tungmai.footballfiled.main.activity.MainActivity.class);
                        intent.putExtra(ID_USER,user.getUid());
                        startActivity(intent);
                    }
                    stateLogin++;

                } else {
                    stateLogin = 0;
                    // User is signed out
                    Log.e(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        initFragments();
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }


    private void initFragments() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, loginFragment);
        fragmentTransaction.commit();
    }

    public void chageFragment(Fragment show, int animationEnter, int animationExit) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(animationEnter, animationExit);

        fragmentTransaction.replace(R.id.framelayout, show).addToBackStack(null);
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

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }
}
