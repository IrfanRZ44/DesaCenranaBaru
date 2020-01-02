package com.exomatik.desacenranabaru.ui.proker;

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
import com.exomatik.desacenranabaru.service.PickLocation.PickLocationAct;
import com.exomatik.desacenranabaru.utils.Constant;
import com.exomatik.desacenranabaru.utils.FileUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class TambahProkerFragment extends BaseFragment {
    @Override
    protected Integer getLayoutResource() { return R.layout.fragment_tambah_proker; }
    public static String locationPoint;
    private MaterialButton btnDone;
    private TextInputLayout etDesc, etTanggal, etLokasi, etNama;
    private CircleImageView btnPickTanggal, btnPickAlamat;
    private ImageButton btnPickFoto;
    private final int PICK_IMAGE = 100;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private File compressedImage, actualImage;
    private Uri imageUri;

    @Override
    protected void myCodeHere() {
        init();
        setData();
        onClick();
    }

    private void init() {
        etDesc = view.findViewById(R.id.etDesc);
        etTanggal = view.findViewById(R.id.etTanggal);
        etLokasi = view.findViewById(R.id.etLokasi);
        etNama = view.findViewById(R.id.etNama);
        btnDone = view.findViewById(R.id.btnDone);
        btnPickTanggal = view.findViewById(R.id.btnPickTanggal);
        btnPickAlamat = view.findViewById(R.id.btnPickAlamat);
        btnPickFoto = view.findViewById(R.id.btnPickFoto);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
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

        btnPickFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading(true);
                startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE);
            }
        });

        btnPickTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });

        btnPickAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), PickLocationAct.class));
            }
        });
    }

    private void cekEditText() {
        String nama = etNama.getEditText().getText().toString();
        if (nama.isEmpty()){
            etNama.setError(Constant.dataKosong);
        }
        else {
            setFoto();
        }
    }

    private void setFoto(){
        showLoading(true);
        btnDone.setEnabled(false);

        OnSuccessListener<UploadTask.TaskSnapshot> onSuccessListener = new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                makeToast(Constant.uploadFotoSucces);
                getUrlFoto(taskSnapshot);
            }
        };

        OnFailureListener onFailureListener = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showLoading(false);
                makeSnackbar(e.getMessage().toString(), R.drawable.snakbar_red);
                btnDone.setEnabled(true);
            }
        };



        firebaseUtils.setFoto(Long.toString(System.currentTimeMillis()), imageUri, Constant.fotoProker
                , onSuccessListener, onFailureListener);
    }

    private void getUrlFoto(UploadTask.TaskSnapshot taskSnapshot){

        OnSuccessListener<Uri> onSuccessListener = new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                makeToast(Constant.getFotoSucces);
                setValue(uri.toString());
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

        firebaseUtils.getUrlFoto(taskSnapshot, onSuccessListener, onFailureListener);
    }

    private void setValue(String urlFoto) {
        String nama = etNama.getEditText().getText().toString();
        String tanggal = etTanggal.getEditText().getText().toString();
        String lokasi = etLokasi.getEditText().getText().toString();
        String desc = etDesc.getEditText().getText().toString();
        ModelProker dataSend = new ModelProker(Long.toString(System.currentTimeMillis()), nama
                , tanggal, urlFoto, lokasi, desc);
        OnCompleteListener onCompleteListener = new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                showLoading(false);
                if (task.isSuccessful()) {
                    makeToast(Constant.addValueSucces);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                            , new ProkerFragment()).commit();
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
        firebaseUtils.setValue(Constant.proker, dataSend.getId(), dataSend, onFailureListener, onCompleteListener);
    }

    private void getDate() {
        Calendar localCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(), R.style.MyProgressDialogTheme, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker paramAnonymousDatePicker, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
                Calendar localCalendar = Calendar.getInstance();
                localCalendar.set(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3);
                etTanggal.getEditText().setText(dateFormatter.format(localCalendar.getTime()));
            }
        }, localCalendar.get(Calendar.YEAR)
                , localCalendar.get(Calendar.MONTH)
                , localCalendar.get(Calendar.DATE));
        datePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((resultCode == -1) && (requestCode == PICK_IMAGE)) {
            try {
                actualImage = FileUtils.from(getActivity(), data.getData());
                compressedImage = new Compressor(getActivity()).compressToFile(actualImage);
                imageUri = Uri.fromFile(compressedImage);
                btnPickFoto.setBackground(null);

                Picasso.with(getActivity()).load(imageUri).into(btnPickFoto);
            } catch (IOException e) {
                makeToast(e.getMessage().toString());
            }
        }
        showLoading(false);
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
