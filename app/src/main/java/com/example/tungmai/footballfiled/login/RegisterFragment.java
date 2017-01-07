package com.example.tungmai.footballfiled.login;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tungmai.footballfiled.R;

/**
 * Created by tungmai on 29/12/2016.
 */

public class RegisterFragment extends Fragment {

    private View view;
    private EditText edtUsename;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnRegister;
    private TextView tvBackLogin;
    private RadioGroup rdgRegister;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.color_backgroup_login));

        view = inflater.inflate(R.layout.fragment_register, container, false);
        initViews();
        return view;
    }

    private void initViews() {
        edtUsename = (EditText) view.findViewById(R.id.edt_username);
        edtEmail = (EditText) view.findViewById(R.id.edt_email);
        edtPassword = (EditText) view.findViewById(R.id.edt_password);
        btnRegister= (Button) view.findViewById(R.id.btn_register);
        tvBackLogin= (TextView) view.findViewById(R.id.tv_back);
        rdgRegister= (RadioGroup) view.findViewById(R.id.rdg_register);
        rdgRegister.check(R.id.rdb_customer);
        tvBackLogin.setText(Html.fromHtml("<u>Back Login</u>"));
        btnRegister.setOnClickListener(clickViewRegister);
        tvBackLogin.setOnClickListener(clickViewRegister);
    }
    private View.OnClickListener clickViewRegister=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_register:
                    String userName=edtUsename.getText().toString().trim();
                    String email=edtEmail.getText().toString().trim();
                    String password=edtPassword.getText().toString();
                    int typeUser=-1;
                    if(rdgRegister.getCheckedRadioButtonId()==R.id.rdb_customer){
                        typeUser=0;
                    }else{
                        typeUser=1;
                    }
                    if(userName.isEmpty()||email.isEmpty()||password.isEmpty()){
                        Toast.makeText(getActivity().getBaseContext(),"Input again",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    break;
                case R.id.tv_back:
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.chageFragment(mainActivity.getLoginFragment(),R.anim.anim_register_login_come,R.anim.anim_register_login_gone);
                    break;
            }
        }
    };

}
