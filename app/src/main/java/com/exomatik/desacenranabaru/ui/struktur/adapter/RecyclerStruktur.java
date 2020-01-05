package com.exomatik.desacenranabaru.ui.struktur.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.exomatik.desacenranabaru.R;
import com.exomatik.desacenranabaru.model.ModelStruktur;
import com.exomatik.desacenranabaru.model.ModelWisata;
import com.exomatik.desacenranabaru.utils.Constant;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 17/09/2018.
 */

public class RecyclerStruktur extends RecyclerView.Adapter<RecyclerStruktur.bidangViewHolder> {
    private ArrayList<ModelStruktur> dataList;
    private Context context;

    public RecyclerStruktur(ArrayList<ModelStruktur> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public bidangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_struktur, parent, false);

        this.context = parent.getContext();
        return new bidangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(bidangViewHolder holder, final int position) {
        holder.textNama.setText(dataList.get(position).getNama());
        holder.textKontak.setText(dataList.get(position).getKontak());

        holder.textJabatan.setText(Constant.listJabatan[position]);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class bidangViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView textNama, textKontak, textJabatan;

        public bidangViewHolder(View itemView) {
            super(itemView);

            textNama = itemView.findViewById(R.id.textNama);
            textKontak = itemView.findViewById(R.id.textKontak);
            textJabatan = itemView.findViewById(R.id.textJabatan);
        }
    }
}
