package com.exomatik.desacenranabaru.ui.wisata;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.exomatik.desacenranabaru.R;
import com.exomatik.desacenranabaru.base.BaseFragment;
import com.exomatik.desacenranabaru.model.ModelProker;
import com.exomatik.desacenranabaru.model.ModelWisata;
import com.exomatik.desacenranabaru.ui.location.PickLocationAct;
import com.exomatik.desacenranabaru.ui.proker.ProkerFragment;
import com.exomatik.desacenranabaru.utils.Constant;
import com.exomatik.desacenranabaru.utils.FileUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class TambahWisataFragment extends BaseFragment {
    @Override protected Integer getLayoutResource() { return R.layout.fragment_tambah_wisata; }
    public static String locationPoint;
    private MaterialButton btnDone;
    private TextInputLayout etDesc, etLokasi, etNama;
    private CircleImageView btnPickAlamat;

    @Override
    protected void myCodeHere() {
        init();
        setData();
        onClick();
    }

    private void init() {
        etDesc = view.findViewById(R.id.etDesc);
        etLokasi = view.findViewById(R.id.etLokasi);
        etNama = view.findViewById(R.id.etNama);
        btnDone = view.findViewById(R.id.btnDone);
        btnPickAlamat = view.findViewById(R.id.btnPickAlamat);
    }

    private void setData() {

    }

    private void onClick() {
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekEditText();
            }
        });

        btnPickAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PickLocationAct.class);
                intent.putExtra(Constant.lokasi, 1);
                getActivity().startActivity(intent);
            }
        });
    }

    private void cekEditText() {
        String nama = etNama.getEditText().getText().toString();
        if (nama.isEmpty() || locationPoint == null || nama.length() < 2){
            if (nama.isEmpty()){
                etNama.getEditText().setError(Constant.dataKosong);
                etNama.getEditText().clearFocus();
                etNama.getEditText().requestFocus();
            }else if (nama.length() < 2){
                etNama.setError(Constant.namaMin2);
                etNama.getEditText().clearFocus();
                etNama.getEditText().requestFocus();
            }
            else if (locationPoint == null){
                makeToast(Constant.locationKosong);
            }
        }
        else {
            setValue();
        }
    }

    private void setValue() {
        String nama = etNama.getEditText().getText().toString();
        String lokasi = etLokasi.getEditText().getText().toString();
        String desc = etDesc.getEditText().getText().toString();
        ArrayList<String> list = new ArrayList<>();
        ModelWisata dataSend = new ModelWisata(Long.toString(System.currentTimeMillis()), nama
                , lokasi, desc, list);
        OnCompleteListener onCompleteListener = new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                showLoading(false);
                if (task.isSuccessful()) {
                    makeToast(Constant.addValueSucces);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                            , new WisataFragment()).commit();
                } else {
                    makeSnackbar(Constant.addValueFailed, R.drawable.snakbar_red);
                }
                btnDone.setEnabled(true);
            }
        };

        OnFailureListener onFailureListener = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showLoading(false);
                makeSnackbar(e.getMessage(), R.drawable.snakbar_red);
                btnDone.setEnabled(true);
            }
        };

        showLoading(true);
        btnDone.setEnabled(false);
        firebaseUtils.setValue(Constant.wisata, dataSend.getId(), dataSend, onFailureListener, onCompleteListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        locationPoint = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (locationPoint != null){
            etLokasi.getEditText().setText(locationPoint);
        }
    }
}
