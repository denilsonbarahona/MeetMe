package com.curso.onmessage.PickActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.curso.onmessage.CustomAdapter.CustomGridLayoutManager;
import com.curso.onmessage.CustomAdapter.RecycleVideoPickAdapter;
import com.curso.onmessage.R;

/**
 * Created by PC-PRAF on 19/12/2016.
 */

public class Pick_Video_Fragment extends Fragment {

    private CustomGridLayoutManager gridLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.image_pick , container , false );
        RecyclerView recyclerView = (RecyclerView) RootView.findViewById(R.id.recycle_image_pick);
        gridLayoutManager = new CustomGridLayoutManager(RootView.getContext(),500);
        RecycleVideoPickAdapter recycleVideoPickAdapter = new RecycleVideoPickAdapter(RootView.getContext());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(recycleVideoPickAdapter);

        return RootView;
    }
}
