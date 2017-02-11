package com.example.tungmai.footballfiled.login;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tungmai.footballfiled.R;
import com.example.tungmai.footballfiled.firebasemain.MainFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tungmai on 29/12/2016.
 */

public class RegisterFragment extends Fragment {
    private static final String TAG = "RegisterFragment";
    private static final int RESULT_LOAD_IMG = 432;

    private View view;
    private EditText edtUsename;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnRegister;
    private TextView tvBackLogin;
    private RadioGroup rdgRegister;

    private String userName;
    private String email;
    private String password;
    private TextView tvAddImage;
    private ImageView ivImage;

    private int typeUser;

    private int result;

    private ProgressDialog progressDialog;
    private MainFirebase sendFirebase;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.color_backgroup_login));

        view = inflater.inflate(R.layout.fragment_register, container, false);
        sendFirebase = new MainFirebase(getActivity());
        initViews();
        return view;
    }

    private void initViews() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        tvAddImage = (TextView) view.findViewById(R.id.tv_add_image);
        ivImage = (ImageView) view.findViewById(R.id.iv_image);
        edtUsename = (EditText) view.findViewById(R.id.edt_username);
        edtEmail = (EditText) view.findViewById(R.id.edt_email);
        edtPassword = (EditText) view.findViewById(R.id.edt_password);
        btnRegister = (Button) view.findViewById(R.id.btn_register);
        tvBackLogin = (TextView) view.findViewById(R.id.tv_back);
        rdgRegister = (RadioGroup) view.findViewById(R.id.rdg_register);
        rdgRegister.check(R.id.rdb_customer);
        tvBackLogin.setText(Html.fromHtml("<u>Back Login</u>"));
        tvAddImage.setOnClickListener(clickViewRegister);
        btnRegister.setOnClickListener(clickViewRegister);
        tvBackLogin.setOnClickListener(clickViewRegister);
    }

    private View.OnClickListener clickViewRegister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final MainActivity mainActivity = (MainActivity) getActivity();
            switch (v.getId()) {
                case R.id.btn_register:
                    userName = edtUsename.getText().toString().trim();
                    email = edtEmail.getText().toString().trim();
                    password = edtPassword.getText().toString();
                    typeUser = -1;
                    if (rdgRegister.getCheckedRadioButtonId() == R.id.rdb_customer) {
                        typeUser = 0;
                    } else {
                        typeUser = 1;
                    }
                    if (userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(getActivity().getBaseContext(), "Input again", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    progressDialog.show();

//                    if (!isEmailValid(email)) {
//                        Toast.makeText(getActivity().getBaseContext(), "Email không tồn tại", Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
//                        return;
//                    } else
                    mainActivity.getFirebaseAuth().createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    Toast.makeText(getActivity().getBaseContext(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();

                                    if (!task.isSuccessful()) {
                                        if (!task.isSuccessful()) {
                                            try {
                                                throw task.getException();
                                            } catch (FirebaseAuthWeakPasswordException e) {
                                                edtPassword.setError(getString(R.string.error_weak_password));
                                                edtPassword.requestFocus();
                                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                                edtEmail.setError(getString(R.string.error_invalid_email));
                                                edtEmail.requestFocus();
                                            } catch (FirebaseAuthUserCollisionException e) {
                                                edtEmail.setError(getString(R.string.error_user_exists));
                                                edtEmail.requestFocus();
                                            } catch (Exception e) {
                                                Log.e(TAG, e.getMessage());
                                            }
                                        }
                                    } else {
                                        ProgressDialog dialog=new ProgressDialog(getActivity());
                                        dialog.setCancelable(false);
                                        dialog.show();
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        user.sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d(TAG, "Email sent.");
                                                        }
                                                    }
                                                });
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(userName)
                                                .build();
//                                            Log.e(TAG,profileUpdates.getDisplayName());
                                        mainActivity.chageFragment(mainActivity.getLoginFragment(), R.anim.anim_register_login_come, R.anim.anim_register_login_gone);
                                        String idUser = task.getResult().getUser().getUid();
                                        sendFirebase.createRegister(idUser, userName, typeUser,imageUriUser,dialog);

                                    }
                                }
                            });

                    break;
                case R.id.tv_back:
                    mainActivity.chageFragment(mainActivity.getLoginFragment(), R.anim.anim_register_login_come, R.anim.anim_register_login_gone);
                    break;
                case R.id.tv_add_image:
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

                    break;
            }
        }
    };
    private Uri imageUriUser;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == getActivity().RESULT_OK)
        if (resultCode == getActivity().RESULT_OK) {
            imageUriUser = data.getData();
//                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
//                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            ivImage.setImageURI(imageUriUser);
        } else {
            Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }

    }

    //check email exist
    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }

}
