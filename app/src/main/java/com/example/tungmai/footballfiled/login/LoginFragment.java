package com.example.tungmai.footballfiled.login;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

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
    private ProgressDialog dialog;

//    private FirebaseAuth.AuthStateListener mAuthListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        callbackManager = CallbackManager.Factory.create();
        view = inflater.inflate(R.layout.fragment_login, container, false);

        dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);

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
            MainActivity mainActivity = (MainActivity) getActivity();
            switch (v.getId()) {
                case R.id.btn_login:
                    String email = edtUsername.getText().toString();
                    String password = edtPassword.getText().toString();
                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(getActivity().getBaseContext(), "Email hoặc password không được để trống", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    dialog.show();
                    mainActivity.getFirebaseAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dialog.dismiss();

                            if (task.isSuccessful()) {
                                if (!FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                                    Toast.makeText(getActivity().getBaseContext(), "Email chưa được xác thực", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {

                                    Intent intent = new Intent(getActivity(), com.example.tungmai.footballfiled.main.activity.MainActivity.class);
                                    intent.putExtra(MainActivity.ID_USER,task.getResult().getUser().getUid());
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(getActivity().getBaseContext(), "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                    break;
                case R.id.tv_forget_password:
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    String emailAddress = edtUsername.getText().toString();
                    if (emailAddress.isEmpty()) {
                        Toast.makeText(getActivity().getBaseContext(), "Email không được để trống", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity().getBaseContext(), "Đã gửi tới email của bạn", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    break;
                case R.id.tv_creat_account:
                    mainActivity.chageFragment(mainActivity.getRegisterFragment(), R.anim.anim_login_register_come, R.anim.anim_login_register_gone);
                    break;
            }
        }
    };

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        MainActivity mainActivity = (MainActivity) getActivity();

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mainActivity.getFirebaseAuth().signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                        }else{
                            Log.w(TAG, "signInWithCredentialSuccessful");
                        }

                        // ...
                    }
                });
    }

//    protected void facebookSDKInitialize() {
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        callbackManager = CallbackManager.Factory.create();
//    }

    protected void getLoginDetails(LoginButton login_button) {

        loginButton.setReadPermissions("email", "public_profile");
        // Callback registration
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult login_result) {
                Toast.makeText(getActivity().getBaseContext(), "Login success", Toast.LENGTH_SHORT).show();
                handleFacebookAccessToken(login_result.getAccessToken());
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
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }


}
