package com.exomatik.desacenranabaru.ui.proker;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.exomatik.desacenranabaru.R;
import com.exomatik.desacenranabaru.base.BaseFragment;
import com.exomatik.desacenranabaru.model.ModelProker;
import com.exomatik.desacenranabaru.ui.proker.adapter.RecyclerProker;
import com.exomatik.desacenranabaru.utils.Constant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class ProkerFragment extends BaseFragment {
    @Override protected Integer getLayoutResource() { return R.layout.fragment_proker; }
    private RelativeLayout rlAdd;
    private FloatingActionButton btnAdd;
    private RecyclerView recycler;
    private AppCompatTextView textNoData;
    private SwipeRefreshLayout refresh;
    private RecyclerProker adapter;
    private ArrayList<ModelProker> list = new ArrayList<>();

    @Override
    protected void myCodeHere() {
        init();
        setAdapter();
        setData();
        getData();
        onClick();
    }

    private void init() {
        btnAdd = view.findViewById(R.id.btnAdd);
        rlAdd = view.findViewById(R.id.rlAdd);
        recycler = view.findViewById(R.id.recycler);
        refresh = view.findViewById(R.id.refresh);
        textNoData = view.findViewById(R.id.textNoData);
    }

    private void setData() {
        if (userPreferences.getSaveString(Constant.savedUser) != null){
            if (userPreferences.getSaveString(Constant.savedUser).equals(Constant.userName)){
                rlAdd.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setAdapter() {
        adapter = new RecyclerProker(list, getActivity());
        LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(localLinearLayoutManager);
        recycler.setNestedScrollingEnabled(false);
        recycler.setAdapter(adapter);
    }

    private void onClick() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                        , new TambahProkerFragment()).commit();
            }
        });
    }

    private void getData(){
        showLoading(true);
        ValueEventListener valueEvent = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showLoading(false);
                list.removeAll(list);
                if (dataSnapshot.exists()) {
                    Iterator localIterator = dataSnapshot.getChildren().iterator();
                    while (localIterator.hasNext()) {
                        ModelProker localDataUser = ((DataSnapshot) localIterator.next()).getValue(ModelProker.class);

                        if (localDataUser.getFoto() != null){
                            list.add(localDataUser);
                            adapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    textNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showLoading(false);
                makeSnackbar(databaseError.getMessage(), R.drawable.radius_bottom_gradient);
            }
        };

        firebaseUtils.getAllValue(Constant.proker, valueEvent);
    }
}
