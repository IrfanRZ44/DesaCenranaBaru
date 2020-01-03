package com.exomatik.desacenranabaru.ui.visi;

import android.view.View;

import androidx.annotation.NonNull;

import com.exomatik.desacenranabaru.R;
import com.exomatik.desacenranabaru.base.BaseFragment;
import com.exomatik.desacenranabaru.model.ModelProker;
import com.exomatik.desacenranabaru.model.ModelVisi;
import com.exomatik.desacenranabaru.ui.proker.ProkerFragment;
import com.exomatik.desacenranabaru.utils.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class VisiFragment extends BaseFragment {
    @Override protected Integer getLayoutResource() { return R.layout.fragment_visi; }
    private TextInputLayout etVisi, etMisi;
    private MaterialButton btnSimpan;

    @Override
    protected void myCodeHere() {
        init();
        setData();
        getDataVisi();
        onClick();
    }

    private void init() {
        etVisi = view.findViewById(R.id.etVisi);
        etMisi = view.findViewById(R.id.etMisi);
        btnSimpan = view.findViewById(R.id.btnSimpan);
    }

    private void setData() {
        if (userPreferences.getSaveString(Constant.savedUser) != null) {
            if (userPreferences.getSaveString(Constant.savedUser).equals(Constant.userName)) {
                btnSimpan.setVisibility(View.VISIBLE);
                etVisi.getEditText().setEnabled(true);
                etMisi.getEditText().setEnabled(true);
            }
        }
    }

    private void onClick() {
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekEditText();
            }
        });
    }

    private void getDataVisi() {
        showLoading(true);
        ValueEventListener valueEvent = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showLoading(false);
                if (dataSnapshot.exists()) {
                    ModelVisi dataResult = dataSnapshot.getValue(ModelVisi.class);

                    etVisi.getEditText().setText(dataResult.getVisi());
                    etMisi.getEditText().setText(dataResult.getMisi());
                } else {
                    etVisi.getEditText().setText(Constant.noVisi);
                    etMisi.getEditText().setText(Constant.noMisi);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                etVisi.getEditText().setText(Constant.noVisi);
                etMisi.getEditText().setText(Constant.noMisi);
                showLoading(false);
                makeSnackbar(databaseError.getMessage(), R.drawable.radius_bottom_gradient);
            }
        };

        firebaseUtils.getAllValue(Constant.visi, valueEvent);
    }

    private void cekEditText() {
        String visi = etVisi.getEditText().getText().toString();
        String misi = etMisi.getEditText().getText().toString();
        
        if (visi.isEmpty() || misi.isEmpty()){
            if (visi.isEmpty()){
                etVisi.getEditText().setError(Constant.dataKosong);
                etVisi.getEditText().clearFocus();
                etVisi.getEditText().requestFocus();
            }else if (misi.isEmpty()){
                etMisi.getEditText().setError(Constant.dataKosong);
                etMisi.getEditText().clearFocus();
                etMisi.getEditText().requestFocus();
            }
        }
        else {
            showLoading(true);
            btnSimpan.setEnabled(false);
            setValue(new ModelVisi(visi, misi));
        }
    }

    private void setValue(ModelVisi modelVisi) {
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
                btnSimpan.setEnabled(true);
            }
        };

        OnFailureListener onFailureListener = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showLoading(false);
                makeSnackbar(e.getMessage(), R.drawable.snakbar_red);
                btnSimpan.setEnabled(true);
            }
        };

        showLoading(true);
        btnSimpan.setEnabled(false);
        firebaseUtils.setValueVisi(Constant.visi, modelVisi, onFailureListener, onCompleteListener);
    }
}
