package com.exomatik.desacenranabaru.ui.image;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.exomatik.desacenranabaru.R;
import com.exomatik.desacenranabaru.base.BaseActivity;
import com.exomatik.desacenranabaru.utils.Constant;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

public class DetailFotoAct extends BaseActivity {
    @Override protected Integer getThemeResources() { return R.style.CustomStyle; }
    @Override protected Integer getLayoutResources() { return R.layout.act_detail_foto; }
    private PhotoView img;
    private String urlFoto;
    private ImageButton imgBack;

    @Override
    protected void myCodeHere() {
        init();
        setData();
        onClick();
    }

    private void init() {
        img = findViewById(R.id.img);
        imgBack = findViewById(R.id.imgBack);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void setData() {
        urlFoto = getIntent().getStringExtra(Constant.fotoProker);

        try {
            Picasso.with(this).load(urlFoto).into(img);
        }catch (Exception e){
            Log.e(Constant.log1, e.getMessage());
        }
    }

    private void onClick() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
