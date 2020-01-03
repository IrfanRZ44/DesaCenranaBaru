package com.exomatik.desacenranabaru.ui.proker;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.exomatik.desacenranabaru.R;
import com.exomatik.desacenranabaru.base.BaseActivity;
import com.exomatik.desacenranabaru.model.ModelProker;
import com.exomatik.desacenranabaru.ui.image.DetailFotoAct;
import com.exomatik.desacenranabaru.ui.main.MainAct;
import com.exomatik.desacenranabaru.utils.Constant;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

public class DetailJasaAct extends BaseActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    @Override protected Integer getLayoutResources() { return R.layout.act_detail_proker; }
    @Override protected Integer getThemeResources() { return R.style.CustomStyle; }
    private ModelProker dataProker;
    private TextView textNama, textTanggal, textDesc, textAlamat;
    private ImageView imgFoto;
    private GoogleMap mMap;
    private ImageButton imgBack, imgHapus;
    private ProgressDialog progressDialog;

    @Override
    protected void myCodeHere() {
        init();
        getDataIntent();
        setData();
        onClick();
    }

    private void init() {
        textNama = findViewById(R.id.text_nama);
        textTanggal = findViewById(R.id.text_tanggal);
        textDesc = findViewById(R.id.text_desc);
        textAlamat = findViewById(R.id.textAlamat);
        imgFoto = findViewById(R.id.imgFoto);
        imgBack = findViewById(R.id.imgBack);
        imgHapus = findViewById(R.id.imgHapus);
    }

    private void getDataIntent() {
        String nama = getIntent().getStringExtra(Constant.nama);
        String foto = getIntent().getStringExtra(Constant.foto);
        String lokasi = getIntent().getStringExtra(Constant.lokasi);
        String tanggal = getIntent().getStringExtra(Constant.tanggal);
        String desc = getIntent().getStringExtra(Constant.desc);
        String id = getIntent().getStringExtra(Constant.id);

        dataProker = new ModelProker(id, nama, tanggal, foto, lokasi, desc);
    }

    private void setData() {
        textNama.setText(dataProker.getNama());
        textTanggal.setText(dataProker.getTanggal());
        textDesc.setText(dataProker.getDesc());
        Picasso.with(this).load(dataProker.getFoto()).into(imgFoto);

        if (userPreferences.getSaveString(Constant.savedUser) != null){
            if (userPreferences.getSaveString(Constant.savedUser).equals(Constant.userName)){
                imgHapus.setVisibility(View.VISIBLE);
            }
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    private void onClick() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailJasaAct.this, MainAct.class));
                finish();
            }
        });

        imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataProker.getFoto() != null){
                    Intent intent = new Intent(DetailJasaAct.this, DetailFotoAct.class);
                    intent.putExtra(Constant.fotoProker, dataProker.getFoto());
                    startActivity(intent);
                }
            }
        });

        imgHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertSure();
            }
        });
    }

    private void alertSure() {
        AlertDialog.Builder alert = new AlertDialog.Builder(DetailJasaAct.this);
        alert.setTitle(Constant.wantDeleteProker);
        alert.setPositiveButton(Constant.iya, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                progressDialog = makeProgress(Constant.pleaseWait);
                progressDialog.show();
                hapusFoto();
            }
        });
        alert.setNegativeButton(Constant.tidak, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    private void hapusFoto(){
        OnCompleteListener onCompleteListener = new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    makeToast(Constant.deleteFotoSucces);
                    hapusValue();
                }
                else {
                    progressDialog.dismiss();
                    makeSnackbar(Constant.koneksiGagal, R.drawable.snakbar_red);
                }
            }
        };

        OnFailureListener onFailureListener = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                makeSnackbar(e.getMessage(), R.drawable.snakbar_red);
            }
        };

        firebaseUtils.hapusFoto(dataProker.getFoto(), onCompleteListener, onFailureListener);
    }

    private void hapusValue(){
        OnCompleteListener onCompleteListener = new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    makeToast(Constant.deleteValueSucces);
                    progressDialog.dismiss();
                    startActivity(new Intent(DetailJasaAct.this, MainAct.class));
                    finish();
                }
                else {
                    progressDialog.dismiss();
                    makeSnackbar(Constant.koneksiGagal, R.drawable.snakbar_red);
                }
            }
        };

        OnFailureListener onFailureListener = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                makeSnackbar(e.getMessage(), R.drawable.snakbar_red);
            }
        };

        firebaseUtils.hapusValue(Constant.proker, dataProker.getId(), onCompleteListener, onFailureListener);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String lat[] = dataProker.getLokasi().split(";");
        LatLng sydney = new LatLng(Float.parseFloat(lat[0]), Float.parseFloat(lat[1]));

        mMap.addMarker(new MarkerOptions().position(sydney).title(dataProker.getNama()));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        makeToast(Constant.koneksiGagal);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DetailJasaAct.this, MainAct.class));
        finish();
    }
}
