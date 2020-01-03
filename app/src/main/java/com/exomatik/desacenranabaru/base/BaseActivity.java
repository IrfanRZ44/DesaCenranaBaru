package com.exomatik.desacenranabaru.base;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.exomatik.desacenranabaru.R;
import com.exomatik.desacenranabaru.utils.FirebaseUtils;
import com.exomatik.desacenranabaru.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;

abstract public class BaseActivity extends AppCompatActivity {
    protected UserPreferences userPreferences;
    protected FirebaseUtils firebaseUtils;
    protected abstract void myCodeHere();
    protected abstract Integer getLayoutResources();
    protected abstract Integer getThemeResources();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(getThemeResources());
        setContentView(getLayoutResources());
        init();
        myCodeHere();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void init() {
        userPreferences = new UserPreferences(this);
        firebaseUtils = new FirebaseUtils(this);
    }

    public void makeSnackbar(String text, int background) {
        View view = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);

        // Get the Snackbar view
        View v = snackbar.getView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackground(ContextCompat.getDrawable(this, background));
        }
        TextView tv = (TextView) v.findViewById(com.google.android.material.R.id.snackbar_text);

        tv.setTextColor(Color.parseColor("#FFFFFF"));

        snackbar.setText(text);
        snackbar.show();
    }

    public ProgressDialog makeProgress(String text){
        ProgressDialog progress = new ProgressDialog(this, R.style.MyProgressDialogTheme);
        progress.setMessage(text);
        progress.setCancelable(false);

        return progress;
    }

    public void makeToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}