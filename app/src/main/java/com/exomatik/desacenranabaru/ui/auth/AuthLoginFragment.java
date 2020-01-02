package com.exomatik.desacenranabaru.ui.auth;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.desacenranabaru.R;
import com.exomatik.desacenranabaru.base.BaseFragment;
import com.exomatik.desacenranabaru.service.AviLoading.AVLoadingIndicatorView;
import com.exomatik.desacenranabaru.ui.proker.ProkerFragment;
import com.exomatik.desacenranabaru.utils.Constant;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class AuthLoginFragment extends BaseFragment {
    @Override protected Integer getLayoutResource() { return R.layout.fragment_login; }
    private TextInputLayout etUser, etPass;
    private AVLoadingIndicatorView progress;
    private MaterialButton btnLogin;

    @Override
    protected void myCodeHere() {
        init();
        onClick();
    }

    private void init() {
        etUser = view.findViewById(R.id.etUser);
        etPass = view.findViewById(R.id.etPass);
        progress = view.findViewById(R.id.progress);
        btnLogin = view.findViewById(R.id.btnLogin);
    }

    private void onClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekEditText();
            }
        });

        etPass.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    cekEditText();
                }
                return false;
            }
        });
    }

    private void cekEditText() {
        etUser.setError(null);
        etPass.setError(null);
        String user = etUser.getEditText().getText().toString();
        String pass = etPass.getEditText().getText().toString();

        if (user.isEmpty() || pass.isEmpty() || !user.equals(Constant.userName) || !pass.equals(Constant.password)){
            if (user.isEmpty()){
                etUser.setError(Constant.usernameEmpty);
                etUser.requestFocus();
            }else if (pass.isEmpty()){
                etPass.setError(Constant.passwordEmpty);
                etPass.requestFocus();
            }else if (!user.equals(Constant.userName)){
                etUser.setError(Constant.usernameSalah);
                etUser.requestFocus();
            }else if (!pass.equals(Constant.password)){
                etPass.setError(Constant.passwordSalah);
                etPass.requestFocus();
            }
        }
        else {
            Toast.makeText(getContext(), Constant.loginSucces, Toast.LENGTH_SHORT).show();
            userPreferences.setSaveString(Constant.userName, Constant.savedUser);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                    , new ProkerFragment()).commit();
        }
    }
}
