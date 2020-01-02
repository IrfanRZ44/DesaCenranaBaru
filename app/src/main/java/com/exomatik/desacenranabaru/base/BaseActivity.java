package com.exomatik.desacenranabaru.base;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.exomatik.desacenranabaru.utils.UserPreferences;

abstract public class BaseActivity extends AppCompatActivity {
    protected UserPreferences userPreferences;
    protected abstract void myCodeHere();
    protected abstract Integer getLayoutResources();
    protected abstract Integer getThemeResources();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(getThemeResources());
        setContentView(getLayoutResources());
        userPreferences = new UserPreferences(this);
        myCodeHere();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

//
//    override fun onDestroy() {
//        super.onDestroy()
//        FirebaseUtils.cancelQuery()
//    }
}