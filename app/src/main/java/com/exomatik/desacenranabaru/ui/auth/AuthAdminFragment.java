package com.exomatik.desacenranabaru.ui.auth;

import android.view.View;

import com.exomatik.desacenranabaru.R;
import com.exomatik.desacenranabaru.base.BaseFragment;
import com.exomatik.desacenranabaru.ui.proker.ProkerFragment;
import com.exomatik.desacenranabaru.utils.Constant;
import com.google.android.material.button.MaterialButton;

public class AuthAdminFragment extends BaseFragment {
    @Override protected Integer getLayoutResource() { return R.layout.fragment_admin; }
    private MaterialButton btnLogout;

    @Override
    protected void myCodeHere() {
        init();
        onClick();
    }

    private void init() {
        btnLogout = view.findViewById(R.id.btnLogout);
    }

    private void onClick() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPreferences.setSaveString(null, Constant.savedUser);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                        , new ProkerFragment()).commit();
            }
        });
    }
}
