package com.exomatik.desacenranabaru.ui.wisata.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.exomatik.desacenranabaru.R;
import com.exomatik.desacenranabaru.model.ModelProker;
import com.exomatik.desacenranabaru.model.ModelWisata;
import com.exomatik.desacenranabaru.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by IrfanRZ on 17/09/2018.
 */

public class RecyclerWisata extends RecyclerView.Adapter<RecyclerWisata.bidangViewHolder> {
    private ArrayList<ModelWisata> dataList;
    private Context context;

    public RecyclerWisata(ArrayList<ModelWisata> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public bidangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_wisata, parent, false);

        this.context = parent.getContext();
        return new bidangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(bidangViewHolder holder, final int position) {
        String alphabet = dataList.get(position).getNamaWisata().substring(0, 2);
        holder.imgName.setText(alphabet);
        holder.textNama.setText(dataList.get(position).getNamaWisata());
        holder.textDesc.setText(Constant.desc + Constant.titikDua + dataList.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class bidangViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView textNama, textDesc, imgName;

        public bidangViewHolder(View itemView) {
            super(itemView);

            textNama = itemView.findViewById(R.id.textNama);
            textDesc = itemView.findViewById(R.id.textDesc);
            imgName = itemView.findViewById(R.id.imgName);
        }
    }
}
