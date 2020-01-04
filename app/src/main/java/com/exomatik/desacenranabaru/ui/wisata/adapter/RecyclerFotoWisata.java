package com.exomatik.desacenranabaru.ui.wisata.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.exomatik.desacenranabaru.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by IrfanRZ on 03/08/2019.
 */

public class RecyclerFotoWisata extends RecyclerView.Adapter<RecyclerFotoWisata.bidangViewHolder> {
    private ArrayList<String> dataList;
    private Context context;
    private ProgressDialog progressDialog = null;

    public RecyclerFotoWisata(ArrayList<String> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public bidangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_foto_wisata, parent, false);

        this.context = parent.getContext();
        return new bidangViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(bidangViewHolder holder, final int position) {
        Uri localUri = Uri.parse(dataList.get(position));
        Picasso.with(context).load(localUri).into(holder.btnAdd);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class bidangViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView btnAdd;

        public bidangViewHolder(View itemView) {
            super(itemView);

            btnAdd = itemView.findViewById(R.id.btn_add_foto);
        }
    }
}