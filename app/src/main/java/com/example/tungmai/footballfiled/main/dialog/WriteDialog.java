package com.example.tungmai.footballfiled.main.dialog;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tungmai.footballfiled.R;
import com.example.tungmai.footballfiled.firebasemain.MainFirebase;
import com.example.tungmai.footballfiled.main.activity.MainActivity;
import com.example.tungmai.footballfiled.main.adapter.ImageDialogAdapter;
import com.example.tungmai.footballfiled.main.customevent.LoadImageListener;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by tungmai on 08/01/2017.
 */

public class WriteDialog extends DialogFragment {
    private View view;
    private ImageView ivBack;
    private Spinner spinner;
    private EditText edtTitle;
    private EditText edtPhone;
    private EditText edtNameGround;
    private EditText edtAddress;
    private EditText edtDescribe;
    private Button btnOk;
    private Button btnAddImage;
    private GridView gridView;
    private MainFirebase mainFirebase;

    private String idUser;
    private ClipData clipData;
    private ArrayList<Uri> arrUriImage;
    private ImageDialogAdapter imageDialogAdapter;

    private String[] arrSpinner = {
            "Tim doi thu", "Tim san bong", "Tim nguoi da", "Tim doi da"
    };
    private View.OnClickListener clickDialog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    getDialog().dismiss();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_wirte_news, container, false);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.AnimDialogWrite;
        mainFirebase = new MainFirebase(getActivity().getBaseContext());
        idUser = getArguments().getString(com.example.tungmai.footballfiled.login.MainActivity.ID_USER);
        arrUriImage = new ArrayList<>();
        imageDialogAdapter = new ImageDialogAdapter(getActivity().getBaseContext(), arrUriImage);
        initViews();
        return view;
    }


    private void initViews() {
        ivBack = (ImageView) view.findViewById(R.id.iv_back);
        ivBack.setOnClickListener(clickDialog);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        //Gán Data source (arr) vào Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getDialog().getContext(), android.R.layout.simple_spinner_item, arrSpinner);
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spinner.setAdapter(adapter);

        edtTitle = (EditText) view.findViewById(R.id.edt_title);
        edtPhone = (EditText) view.findViewById(R.id.edt_phone);
        edtNameGround = (EditText) view.findViewById(R.id.edt_name);
        edtAddress = (EditText) view.findViewById(R.id.edt_address);

        edtDescribe = (EditText) view.findViewById(R.id.edt_describe);
        btnOk = (Button) view.findViewById(R.id.btn_ok);
        btnAddImage = (Button) view.findViewById(R.id.btn_add_image);
        gridView = (GridView) view.findViewById(R.id.gridview);
        gridView.setAdapter(imageDialogAdapter);

        btnOk.setOnClickListener(clickOk);
        btnAddImage.setOnClickListener(clickOk);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setLoadImageListener(new LoadImageListener() {
            @Override
            public void loadImage() {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    arrUriImage.add(uri);
                }
                Log.e(TAG, arrUriImage.size()+"");
                imageDialogAdapter.notifyDataSetChanged();

            }
        });

    }

    private static final String TAG = "WriteDialog";
    private View.OnClickListener clickOk = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_ok:
                    final ProgressDialog progressDialog=new ProgressDialog(getActivity());
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Vui lòng đợi");
                    progressDialog.show();


                    int typeSpinner = spinner.getSelectedItemPosition();

                    String title = edtTitle.getText().toString();
                    String phone = edtPhone.getText().toString();
                    String nameGround = edtNameGround.getText().toString();
                    String address = edtAddress.getText().toString();
                    String desscribe = edtDescribe.getText().toString();

                    if (title.isEmpty() || phone.isEmpty() || nameGround.isEmpty() || address.isEmpty() || desscribe.isEmpty()) {
                        Toast.makeText(getDialog().getContext(), "Không được bỏ trống", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Calendar calendar = Calendar.getInstance();
                    String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
                    String date = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
                    mainFirebase.writeNoti(idUser, typeSpinner, title, phone, nameGround, address, desscribe, time, date, 0,arrUriImage,progressDialog);
                    getDialog().dismiss();
                    break;
                case R.id.btn_add_image:
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    getActivity().startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                    break;
            }

        }
    };

    public ClipData getClipData() {
        return clipData;
    }

    public void setClipData(ClipData clipData) {
        this.clipData = clipData;
    }
}
