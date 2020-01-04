package com.exomatik.desacenranabaru.ui.wisata;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;

import com.exomatik.desacenranabaru.R;
import com.google.android.material.button.MaterialButton;

public class DialogTambahFotoWisata extends DialogFragment {
    public static Activity activity;
    private AppCompatImageButton btnPickFoto;
    private MaterialButton btnBatal;
    private final int PICK_IMAGE = 100;

    public DialogTambahFotoWisata newInstance() {
        return new DialogTambahFotoWisata();
    }
    @RequiresApi(api = 23)
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View localView = paramLayoutInflater.inflate(R.layout.dialog_tambah_foto_wisata, paramViewGroup, false);


        init(localView);
        onClick();

        return localView;
    }

    private void init(View localView) {
        btnPickFoto = localView.findViewById(R.id.btnPickFoto);
        btnBatal = localView.findViewById(R.id.btnBatal);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void onClick() {
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnPickFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.INTERNAL_CONTENT_URI), PICK_IMAGE);
            }
        });
    }
}