package com.exomatik.desacenranabaru.ui.main;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.exomatik.desacenranabaru.R;
import com.exomatik.desacenranabaru.base.BaseActivity;
import com.exomatik.desacenranabaru.ui.proker.ProkerFragment;
import com.exomatik.desacenranabaru.ui.auth.AuthLoginFragment;
import com.exomatik.desacenranabaru.ui.auth.AuthAdminFragment;
import com.exomatik.desacenranabaru.utils.Constant;
import com.google.android.material.navigation.NavigationView;

public class MainAct extends BaseActivity {
    @Override protected Integer getThemeResources() { return R.style.CustomStyle; }
    @Override protected Integer getLayoutResources() { return R.layout.act_main; }
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private AppCompatTextView textHeader;
    private AppCompatImageButton btnProfile;
    private ProkerFragment fragmentKegiatan;
    private AuthLoginFragment fragmentLogin;
    private AuthAdminFragment fragmentAdmin;

    @Override
    protected void myCodeHere() {
        init();
        setData();
        setFragment();
        onClick();
    }

    private void init(){
        toolbar = findViewById(R.id.toolBar);
        drawer = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        textHeader = findViewById(R.id.toolbarTitle);
        btnProfile = findViewById(R.id.btnProfile);
    }

    private void setData() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        fragmentKegiatan = new ProkerFragment();
        fragmentLogin = new AuthLoginFragment();
        fragmentAdmin = new AuthAdminFragment();
    }

    private void setFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                , fragmentKegiatan).commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void onClick(){
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userPreferences.getSaveString(Constant.savedUser).equals(Constant.userName)){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                            , fragmentAdmin).commit();
                }
                else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                            , fragmentLogin).commit();
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.navProker){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                            , fragmentKegiatan).commit();

                }
                else if (id == R.id.navProfil){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                            , fragmentAdmin).commit();
                }

                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }
}
