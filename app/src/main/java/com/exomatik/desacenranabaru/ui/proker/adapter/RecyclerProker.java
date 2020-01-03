package com.exomatik.desacenranabaru.ui.proker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.exomatik.desacenranabaru.R;
import com.exomatik.desacenranabaru.model.ModelProker;
import com.exomatik.desacenranabaru.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by IrfanRZ on 17/09/2018.
 */

public class RecyclerProker extends RecyclerView.Adapter<RecyclerProker.bidangViewHolder> {
    private ArrayList<ModelProker> dataList;
    private Context context;

    public RecyclerProker(ArrayList<ModelProker> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public bidangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_proker, parent, false);

        this.context = parent.getContext();
        return new bidangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(bidangViewHolder holder, final int position) {
        holder.textNama.setText(dataList.get(position).getNama());
        holder.textTanggal.setText(Constant.tanggal + Constant.titikDua + dataList.get(position).getTanggal());
        holder.textDesc.setText(Constant.desc + Constant.titikDua + dataList.get(position).getDesc());
        Picasso.with(context).load(dataList.get(position).getFoto()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class bidangViewHolder extends RecyclerView.ViewHolder {
        private TextView textNama, textStatus, textTanggal, textDesc;
        private CircleImageView img;

        public bidangViewHolder(View itemView) {
            super(itemView);

            textNama = itemView.findViewById(R.id.textNama);
            textStatus = itemView.findViewById(R.id.textStatus);
            textTanggal = itemView.findViewById(R.id.textJK);
            textDesc = itemView.findViewById(R.id.textNIK);
            img = itemView.findViewById(R.id.img);
        }
    }
}
