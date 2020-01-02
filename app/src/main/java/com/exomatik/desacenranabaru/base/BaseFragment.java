package com.exomatik.desacenranabaru.base;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.exomatik.desacenranabaru.R;
import com.exomatik.desacenranabaru.service.AviLoading.AVLoadingIndicatorView;
import com.exomatik.desacenranabaru.utils.FirebaseUtils;
import com.exomatik.desacenranabaru.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;

abstract public class BaseFragment extends Fragment {
    protected UserPreferences userPreferences;
    protected FirebaseUtils firebaseUtils;
    protected View view;
    protected abstract Integer getLayoutResource();
    protected abstract void myCodeHere();
    protected AVLoadingIndicatorView progress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutResource(), container, false);

        init();
        myCodeHere();

        return view;
    }

    private void init() {
        progress = view.findViewById(R.id.progress);

        userPreferences = new UserPreferences(getContext());
        firebaseUtils = new FirebaseUtils(getContext());
    }

    public void showLoading(boolean isTrue){
        if (isTrue){
            progress.setVisibility(View.VISIBLE);
        }
        else {
            progress.setVisibility(View.GONE);
        }
    }

    public void makeSnackbar(String text, int background) {
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);

        // Get the Snackbar view
        View v = snackbar.getView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackground(ContextCompat.getDrawable(getContext(), background));
        }
        TextView tv = (TextView) v.findViewById(com.google.android.material.R.id.snackbar_text);

        tv.setTextColor(Color.parseColor("#FFFFFF"));

        snackbar.setText(text);
        snackbar.show();
    }

    public ProgressDialog makeProgress(String text){
        ProgressDialog progress = new ProgressDialog(getContext(), R.style.MyProgressDialogTheme);
        progress.setMessage(text);
        progress.setCancelable(false);

        return progress;
    }

    public void makeToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
