package com.exomatik.desacenranabaru.ui.wisata;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exomatik.desacenranabaru.R;
import com.exomatik.desacenranabaru.base.BaseActivity;
import com.exomatik.desacenranabaru.model.ModelWisata;
import com.exomatik.desacenranabaru.service.AviLoading.AVLoadingIndicatorView;
import com.exomatik.desacenranabaru.ui.image.DetailFotoAct;
import com.exomatik.desacenranabaru.ui.main.MainAct;
import com.exomatik.desacenranabaru.ui.proker.DetailProkerAct;
import com.exomatik.desacenranabaru.ui.proker.ProkerFragment;
import com.exomatik.desacenranabaru.ui.wisata.adapter.RecyclerFotoWisata;
import com.exomatik.desacenranabaru.utils.Constant;
import com.exomatik.desacenranabaru.utils.FileUtils;
import com.exomatik.desacenranabaru.utils.ItemClickSupport;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import id.zelory.compressor.Compressor;

public class DetailWisataAct extends BaseActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    @Override protected Integer getLayoutResources() { return R.layout.act_detail_wisata; }
    @Override protected Integer getThemeResources() { return R.style.CustomStyle; }
    private ModelWisata dataWisata;
    private TextView textNama, textDesc;
    private GoogleMap mMap;
    private ImageButton imgBack, imgHapus;
    private ProgressDialog progressDialog;
    private RelativeLayout rlAdd;
    private FloatingActionButton btnAdd;
    private final int PICK_IMAGE = 100;
    private AVLoadingIndicatorView progress;
    private File compressedImage, actualImage;
    private Uri imageUri;
    private RecyclerFotoWisata adapter;
    private RecyclerView recycler;

    @Override
    protected void myCodeHere() {
        init();
        getDataIntent();
        setAdapter();
        setData();
        onClick();
    }

    public void showLoading(boolean isTrue){
        if (isTrue){
            progress.setVisibility(View.VISIBLE);
        }
        else {
            progress.setVisibility(View.GONE);
        }
    }

    private void init() {
        btnAdd = findViewById(R.id.btnAdd);
        rlAdd = findViewById(R.id.rlAdd);
        textNama = findViewById(R.id.text_nama);
        textDesc = findViewById(R.id.text_desc);
        imgBack = findViewById(R.id.imgBack);
        imgHapus = findViewById(R.id.imgHapus);
        progress = findViewById(R.id.progress);
        recycler = findViewById(R.id.recycler);
    }

    private void getDataIntent() {
        String nama = getIntent().getStringExtra(Constant.nama);
        String lokasi = getIntent().getStringExtra(Constant.lokasi);
        String desc = getIntent().getStringExtra(Constant.desc);
        String id = getIntent().getStringExtra(Constant.id);
        ArrayList<String> listFoto = getIntent().getStringArrayListExtra(Constant.foto);

        dataWisata = new ModelWisata(id, nama, lokasi, desc, listFoto);
    }

    private void setAdapter() {
        adapter = new RecyclerFotoWisata(dataWisata.getListFoto(), DetailWisataAct.this);
        LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(DetailWisataAct.this, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(localLinearLayoutManager);
        recycler.setNestedScrollingEnabled(false);
        recycler.setAdapter(adapter);
    }

    private void setData() {
        textNama.setText(dataWisata.getNamaWisata());
        textDesc.setText(dataWisata.getDesc());

        if (userPreferences.getSaveString(Constant.savedUser) != null){
            if (userPreferences.getSaveString(Constant.savedUser).equals(Constant.userName)){
                imgHapus.setVisibility(View.VISIBLE);
                rlAdd.setVisibility(View.VISIBLE);
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
                startActivity(new Intent(DetailWisataAct.this, MainAct.class));
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading(true);
                startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE);
                showLoading(false);
            }
        });

        ItemClickSupport.addTo(recycler).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(DetailWisataAct.this, DetailFotoAct.class);
                intent.putExtra(Constant.request, 1);
                intent.putExtra(Constant.foto, dataWisata.getListFoto().get(position));
                startActivity(intent);
            }
        });

        ItemClickSupport.addTo(recycler).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                alertSureDeleteFoto(position);
                return false;
            }
        });
//        imgFoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (dataWisata.getFoto() != null){
//                    Intent intent = new Intent(DetailWisataAct.this, DetailFotoAct.class);
//                    intent.putExtra(Constant.fotoProker, dataWisata.getFoto());
//                    startActivity(intent);
//                }
//            }
//        });

        imgHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataWisata.getListFoto() != null){
                    makeSnackbar(Constant.adaFoto, R.drawable.snakbar_red);
                }
                else {
                    alertSureDeleteValue();
                }
            }
        });
    }

    private void alertSureDeleteValue() {
        AlertDialog.Builder alert = new AlertDialog.Builder(DetailWisataAct.this);
        alert.setTitle(Constant.wantDeleteProker);
        alert.setPositiveButton(Constant.iya, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                progressDialog = makeProgress(Constant.pleaseWait);
                progressDialog.show();
                hapusValue();
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

    private void alertSureDeleteFoto(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(DetailWisataAct.this);
        alert.setTitle(Constant.wantDeleteFoto);
        alert.setPositiveButton(Constant.iya, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                showLoading(true);
                hapusFoto(position);
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

    private void hapusValue() {
        showLoading(true);
        OnCompleteListener onCompleteListener = new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    makeToast(Constant.deleteValueSucces);
                    Intent intent = new Intent(DetailWisataAct.this, MainAct.class);
                    startActivity(intent);
                    finish();
                    showLoading(false);
                }
                else {
                    showLoading(false);
                    makeSnackbar(Constant.koneksiGagal, R.drawable.snakbar_red);
                }
            }
        };

        OnFailureListener onFailureListener = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showLoading(false);
                makeSnackbar(e.getMessage(), R.drawable.snakbar_red);
            }
        };

        firebaseUtils.hapusValue(Constant.wisata
                , dataWisata.getId()
                , onCompleteListener, onFailureListener);
    }

    private void setFoto(){
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
                btnAdd.setEnabled(true);
            }
        };

        firebaseUtils.setFoto(Long.toString(System.currentTimeMillis()), imageUri, Constant.fotoWisata
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
                btnAdd.setEnabled(true);
            }
        };

        firebaseUtils.getUrlFoto(taskSnapshot, onSuccessListener, onFailureListener);
    }

    private void setValue(String urlFoto) {
        ArrayList<String> listFoto = new ArrayList<>();
        if (dataWisata.getListFoto() != null){
            listFoto = dataWisata.getListFoto();
        }
        listFoto.add(urlFoto);

        dataWisata.setListFoto(listFoto);
        OnCompleteListener onCompleteListener = new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                showLoading(false);
                if (task.isSuccessful()) {
                    makeToast(Constant.addValueSucces);
                } else {
                    makeSnackbar(Constant.addValueFailed, R.drawable.snakbar_red);
                }
                setAdapter();
                btnAdd.setEnabled(true);
            }
        };

        OnFailureListener onFailureListener = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showLoading(false);
                makeSnackbar(e.getMessage(), R.drawable.snakbar_red);
                btnAdd.setEnabled(true);
            }
        };

        showLoading(true);
        btnAdd.setEnabled(false);
        firebaseUtils.setValue(Constant.wisata, dataWisata.getId(), dataWisata, onFailureListener, onCompleteListener);
    }

    private void hapusFoto(final int position){
        OnCompleteListener onCompleteListener = new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    makeToast(Constant.deleteFotoSucces);
                    hapusValueFoto(position);
                }
                else {
                    showLoading(false);
                    makeSnackbar(Constant.koneksiGagal, R.drawable.snakbar_red);
                }
            }
        };

        OnFailureListener onFailureListener = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showLoading(false);
                makeSnackbar(e.getMessage(), R.drawable.snakbar_red);
            }
        };

        firebaseUtils.hapusFoto(dataWisata.getListFoto().get(position), onCompleteListener, onFailureListener);
    }

    private void hapusValueFoto(final int position){
        OnCompleteListener onCompleteListener = new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    makeToast(Constant.deleteValueSucces);
                    dataWisata.getListFoto().remove(position);
                    setAdapter();
                    setBackValue();
                    showLoading(false);
                }
                else {
                    showLoading(false);
                    makeSnackbar(Constant.koneksiGagal, R.drawable.snakbar_red);
                }
            }
        };

        OnFailureListener onFailureListener = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showLoading(false);
                makeSnackbar(e.getMessage(), R.drawable.snakbar_red);
            }
        };

        firebaseUtils.hapusValueWithMoreChild(Constant.wisata
                , dataWisata.getId()
                , Constant.listFoto
                , Integer.toString(position)
                , onCompleteListener, onFailureListener);
    }

    private void setBackValue() {
        OnCompleteListener onCompleteListener = new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

            }
        };

        OnFailureListener onFailureListener = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        };

        firebaseUtils.setValue(Constant.wisata, dataWisata.getId(), dataWisata, onFailureListener, onCompleteListener);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String lat[] = dataWisata.getLokasi().split(";");
        LatLng sydney = new LatLng(Float.parseFloat(lat[0]), Float.parseFloat(lat[1]));

        mMap.addMarker(new MarkerOptions().position(sydney).title(dataWisata.getNamaWisata()));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        makeToast(Constant.koneksiGagal);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DetailWisataAct.this, MainAct.class));
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((resultCode == -1) && (requestCode == PICK_IMAGE)) {
            try {
                showLoading(true);
                btnAdd.setEnabled(false);
                actualImage = FileUtils.from(DetailWisataAct.this, data.getData());
                compressedImage = new Compressor(DetailWisataAct.this).compressToFile(actualImage);
                imageUri = Uri.fromFile(compressedImage);
                setFoto();
            } catch (IOException e) {
                showLoading(false);
                btnAdd.setEnabled(true);
                makeToast(e.getMessage().toString());
            }
        }
    }
}
