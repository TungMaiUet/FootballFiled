package com.example.tungmai.footballfiled.login;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tungmai.footballfiled.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by tungmai on 29/12/2016.
 */

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView tvRemember;
    private TextView tvCreateAccount;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        callbackManager = CallbackManager.Factory.create();
        view = inflater.inflate(R.layout.fragment_login, container, false);
        Log.e(TAG, "CreateViewLogin");
        initViews();
        return view;
    }


    private void initViews() {
        loginButton = (LoginButton) view.findViewById(R.id.btn_login_by_facebook);
        getLoginDetails(loginButton);

        edtUsername = (EditText) view.findViewById(R.id.edt_username);
        edtPassword = (EditText) view.findViewById(R.id.edt_password);
        btnLogin = (Button) view.findViewById(R.id.btn_login);
        tvRemember = (TextView) view.findViewById(R.id.tv_forget_password);
        tvCreateAccount = (TextView) view.findViewById(R.id.tv_creat_account);
        btnLogin.setOnClickListener(clickViewLogin);
        tvRemember.setOnClickListener(clickViewLogin);
        tvCreateAccount.setOnClickListener(clickViewLogin);
    }

    private View.OnClickListener clickViewLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_login:
                    Intent intent=new Intent(getActivity(), com.example.tungmai.footballfiled.main.MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_forget_password:
                    break;
                case R.id.tv_creat_account:
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.chageFragment(mainActivity.getRegisterFragment(),R.anim.anim_login_register_come,R.anim.anim_login_register_gone);
                    break;
            }
        }
    };

//    protected void facebookSDKInitialize() {
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        callbackManager = CallbackManager.Factory.create();
//    }

    protected void getLoginDetails(LoginButton login_button) {
        // Callback registration
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult login_result) {
                Toast.makeText(getActivity().getBaseContext(), "Login success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                // code for cancellation
            }

            @Override
            public void onError(FacebookException exception) {
                //  code to handle error<
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }


    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }


}
