package com.exomatik.desacenranabaru.ui.struktur;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.exomatik.desacenranabaru.R;
import com.exomatik.desacenranabaru.base.BaseFragment;
import com.exomatik.desacenranabaru.model.ModelStruktur;
import com.exomatik.desacenranabaru.ui.struktur.adapter.RecyclerStruktur;
import com.exomatik.desacenranabaru.utils.Constant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class StrukturFragment extends BaseFragment {
    @Override protected Integer getLayoutResource() { return R.layout.fragment_struktur; }
    private RelativeLayout rlEdit;
    private FloatingActionButton btnEdit;
    private RecyclerView recycler;
    private AppCompatTextView textNoData;
    private SwipeRefreshLayout refresh;
    private RecyclerStruktur adapter;
    private ArrayList<ModelStruktur> list = new ArrayList<>();

    @Override
    protected void myCodeHere() {
        init();
        setAdapter();
        setData();
        getData();
        onClick();
    }

    private void init() {
        btnEdit = view.findViewById(R.id.btnEdit);
        rlEdit = view.findViewById(R.id.rlEdit);
        recycler = view.findViewById(R.id.recycler);
        refresh = view.findViewById(R.id.refresh);
        textNoData = view.findViewById(R.id.textNoData);
    }

    private void setData() {
        if (userPreferences.getSaveString(Constant.savedUser) != null){
            if (userPreferences.getSaveString(Constant.savedUser).equals(Constant.userName)){
                rlEdit.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setAdapter() {
        list.add(new ModelStruktur());
        list.add(new ModelStruktur());
        list.add(new ModelStruktur());
        list.add(new ModelStruktur());
        list.add(new ModelStruktur());
        list.add(new ModelStruktur());
        list.add(new ModelStruktur());
        list.add(new ModelStruktur());

        adapter = new RecyclerStruktur(list, getActivity());
        LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(localLinearLayoutManager);
        recycler.setNestedScrollingEnabled(false);
        recycler.setAdapter(adapter);
    }

    private void onClick() {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                        , new EditStrukturFragment(list)).commit();
            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                refresh.setRefreshing(false);
            }
        });
    }

    private void getData(){
        showLoading(true);
        ValueEventListener valueEvent = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showLoading(false);

                if (dataSnapshot.exists()) {
                    Iterator localIterator = dataSnapshot.getChildren().iterator();
                    while (localIterator.hasNext()) {
                        ModelStruktur localDataUser = ((DataSnapshot) localIterator.next()).getValue(ModelStruktur.class);

                        for (int a = 0; a <= 7; a++){
                            if (localDataUser.getIdJabatan().equals(Constant.listJbt[a])){
                                list.set(a, localDataUser);
                                adapter.notifyItemChanged(a);
                            }
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

        firebaseUtils.getAllValue(Constant.struktur, valueEvent);
    }
}
