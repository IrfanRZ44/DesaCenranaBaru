package com.exomatik.desacenranabaru.ui.struktur;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.exomatik.desacenranabaru.R;
import com.exomatik.desacenranabaru.base.BaseFragment;
import com.exomatik.desacenranabaru.model.ModelStruktur;
import com.exomatik.desacenranabaru.utils.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class EditStrukturFragment extends BaseFragment {
    @Override protected Integer getLayoutResource() { return R.layout.fragment_edit_struktur; }
    private ArrayList<ModelStruktur> listStruktur;
    private MaterialButton btnDone;
    private TextInputLayout etNamaKepalaDesa, etKontakKepalaDesa, etNamaKepalaSatgas
            , etKontakKepalaSatgas, etNamaSekertarisDesa, etKontakSekertarisDesa, etNamaDusunMalaka
            , etKontakDusunMalaka, etNamaDusunArokke, etKontakDusunArokke, etNamaDusunTanete
            , etKontakDusunTanete, etNamaDusunMatanre, etKontakDusunMatanre, etNamaDusunMaccini, etKontakDusunMaccini;

    public EditStrukturFragment(ArrayList<ModelStruktur> listStruktur) {
        this.listStruktur = listStruktur;
    }

    @Override
    protected void myCodeHere() {
        init();
        setData();
        onClick();
    }

    private void init() {
        etNamaKepalaDesa = view.findViewById(R.id.etNamaKepalaDesa);
        etKontakKepalaDesa = view.findViewById(R.id.etKontakKepalaDesa);
        etNamaKepalaSatgas = view.findViewById(R.id.etNamaKepalaSatgas);
        etKontakKepalaSatgas = view.findViewById(R.id.etKontakKepalaSatgas);
        etNamaSekertarisDesa = view.findViewById(R.id.etNamaSekertarisDesa);
        etKontakSekertarisDesa = view.findViewById(R.id.etKontakSekertarisDesa);
        etNamaDusunMalaka = view.findViewById(R.id.etNamaDusunMalaka);
        etKontakDusunTanete = view.findViewById(R.id.etKontakDusunTanete);
        etNamaDusunMatanre = view.findViewById(R.id.etNamaDusunMatanre);
        etKontakDusunMatanre = view.findViewById(R.id.etKontakDusunMatanre);
        etNamaDusunMaccini = view.findViewById(R.id.etNamaDusunMaccini);
        etKontakDusunMaccini = view.findViewById(R.id.etKontakDusunMaccini);
        etKontakDusunMalaka = view.findViewById(R.id.etKontakDusunMalaka);
        etNamaDusunArokke = view.findViewById(R.id.etNamaDusunArokke);
        etKontakDusunArokke = view.findViewById(R.id.etKontakDusunArokke);
        etNamaDusunTanete = view.findViewById(R.id.etNamaDusunTanete);
        btnDone = view.findViewById(R.id.btnDone);
    }

    private void setData() {
        etNamaKepalaDesa.getEditText().setText(listStruktur.get(0).getNama());
        etKontakKepalaDesa.getEditText().setText(listStruktur.get(0).getKontak());
        etNamaKepalaSatgas.getEditText().setText(listStruktur.get(1).getNama());
        etKontakKepalaSatgas.getEditText().setText(listStruktur.get(1).getKontak());
        etNamaSekertarisDesa.getEditText().setText(listStruktur.get(2).getNama());
        etKontakSekertarisDesa.getEditText().setText(listStruktur.get(2).getKontak());
        etNamaDusunMalaka.getEditText().setText(listStruktur.get(3).getNama());
        etKontakDusunMalaka.getEditText().setText(listStruktur.get(3).getKontak());
        etNamaDusunArokke.getEditText().setText(listStruktur.get(4).getNama());
        etKontakDusunArokke.getEditText().setText(listStruktur.get(4).getKontak());
        etNamaDusunTanete.getEditText().setText(listStruktur.get(5).getNama());
        etKontakDusunTanete.getEditText().setText(listStruktur.get(5).getKontak());
        etNamaDusunMatanre.getEditText().setText(listStruktur.get(6).getNama());
        etKontakDusunMatanre.getEditText().setText(listStruktur.get(6).getKontak());
        etNamaDusunMaccini.getEditText().setText(listStruktur.get(7).getNama());
        etKontakDusunMaccini.getEditText().setText(listStruktur.get(7).getKontak());
    }

    private void onClick() {
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading(true);
                btnDone.setEnabled(false);
                setValue();
            }
        });

        etKontakDusunMaccini.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    showLoading(true);
                    btnDone.setEnabled(false);
                    setValue();
                }
                return false;
            }
        });
    }

    private void setValue() {
        String namaDesa = etNamaKepalaDesa.getEditText().getText().toString();
        String kontakDesa = etKontakKepalaDesa.getEditText().getText().toString();
        String namaSatgas = etNamaKepalaSatgas.getEditText().getText().toString();
        String kontakSatgas = etKontakKepalaSatgas.getEditText().getText().toString();
        String namaSekertaris = etNamaSekertarisDesa.getEditText().getText().toString();
        String kontakSekertaris = etKontakSekertarisDesa.getEditText().getText().toString();
        String namaMalaka = etNamaDusunMalaka.getEditText().getText().toString();
        String kontakMalaka = etKontakDusunMalaka.getEditText().getText().toString();
        String namaArokke = etNamaDusunArokke.getEditText().getText().toString();
        String kontakArokke = etKontakDusunArokke.getEditText().getText().toString();
        String namaTanete = etNamaDusunTanete.getEditText().getText().toString();
        String kontakTanete = etKontakDusunTanete.getEditText().getText().toString();
        String namaMatanre = etNamaDusunMatanre.getEditText().getText().toString();
        String kontakMatanre = etKontakDusunMatanre.getEditText().getText().toString();
        String namaMaccini = etNamaDusunMaccini.getEditText().getText().toString();
        String kontakMaccini = etKontakDusunMaccini.getEditText().getText().toString();

        ArrayList<ModelStruktur> list = new ArrayList<>();
        list.add(new ModelStruktur(Constant.jabatan1, namaDesa, kontakDesa));
        list.add(new ModelStruktur(Constant.jabatan2, namaSatgas, kontakSatgas));
        list.add(new ModelStruktur(Constant.jabatan3, namaSekertaris, kontakSekertaris));
        list.add(new ModelStruktur(Constant.jabatan4, namaMalaka, kontakMalaka));
        list.add(new ModelStruktur(Constant.jabatan5, namaArokke, kontakArokke));
        list.add(new ModelStruktur(Constant.jabatan6, namaTanete, kontakTanete));
        list.add(new ModelStruktur(Constant.jabatan7, namaMatanre, kontakMatanre));
        list.add(new ModelStruktur(Constant.jabatan8, namaMaccini, kontakMaccini));

        for (int a = 0; a <= 7; a++){
            if (a == 7){
                sendValue(list.get(a), list.get(a).getIdJabatan(), true);
            }
            else {
                sendValue(list.get(a), list.get(a).getIdJabatan(), false);
            }
        }

    }

    private void sendValue(Object object, final String id, final boolean lastPosition){
        OnCompleteListener onCompleteListener = new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    makeToast(Constant.addValueSucces);

                    if (lastPosition){
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                                , new StrukturFragment()).commit();
                    }
                } else {
                    makeSnackbar(Constant.addValueFailed, R.drawable.snakbar_red);
                }

                if (lastPosition){
                    btnDone.setEnabled(true);
                    showLoading(false);
                }
            }
        };

        OnFailureListener onFailureListener = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                makeSnackbar(e.getMessage(), R.drawable.snakbar_red);
                if (lastPosition){
                    showLoading(false);
                    btnDone.setEnabled(true);
                }
            }
        };

        firebaseUtils.setValue(Constant.struktur, id, object, onFailureListener, onCompleteListener);
    }
}
