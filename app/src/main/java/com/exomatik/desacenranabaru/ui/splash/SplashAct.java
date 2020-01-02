package com.exomatik.desacenranabaru.ui.splash;

import android.content.Intent;
import android.os.Handler;

import com.exomatik.desacenranabaru.R;
import com.exomatik.desacenranabaru.base.BaseActivity;
import com.exomatik.desacenranabaru.ui.main.MainAct;

public class SplashAct extends BaseActivity {
    @Override protected Integer getThemeResources() { return R.style.CustomStyle; }
    @Override protected Integer getLayoutResources() { return R.layout.act_splash; }

    @Override
    protected void myCodeHere() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent homeIntent = new Intent(SplashAct.this, MainAct.class);
                startActivity(homeIntent);
                finish();
            }
        }, 2000L);
    }
}
