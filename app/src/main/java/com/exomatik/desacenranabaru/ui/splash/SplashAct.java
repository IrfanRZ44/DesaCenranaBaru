package com.exomatik.desacenranabaru.ui.splash;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.exomatik.desacenranabaru.R;
import com.exomatik.desacenranabaru.base.BaseActivity;
import com.exomatik.desacenranabaru.ui.main.MainAct;

public class SplashAct extends BaseActivity {
    @Override protected Integer getThemeResources() { return R.style.CustomStyle; }
    @Override protected Integer getLayoutResources() { return R.layout.act_splash; }
    public static final int REQUEST_PERM_LOCATION = 99;

    @Override
    protected void myCodeHere() {
        if (checkLocationPermission()) {
            nextScene();
        } else {
            checkLocationPermission();
        }

    }

    private void nextScene(){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent homeIntent = new Intent(SplashAct.this, MainAct.class);
                startActivity(homeIntent);
                finish();
            }
        }, 2000L);
    }

    public boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(SplashAct.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERM_LOCATION);
            return false;
        }else {
            return true;
        }
    }
}
