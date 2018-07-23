package com.curso.onmessage.PickActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.curso.onmessage.CustomAdapter.CustomGridLayoutManager;
import com.curso.onmessage.CustomAdapter.Recycle_image_pick;
import com.curso.onmessage.R;


/**
 * Created by PC-PRAF on 4/12/2016.
 */

public class Pick_Image_Fragment extends Fragment {

    private CustomGridLayoutManager gridLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.image_pick, container , false);
        RecyclerView recyclerView = (RecyclerView) RootView.findViewById(R.id.recycle_image_pick);
        gridLayoutManager = new CustomGridLayoutManager(RootView.getContext(),500);
        Recycle_image_pick recycle_image_pick = new Recycle_image_pick(RootView.getContext(), 1);
        recyclerView.setLayoutManager( gridLayoutManager);
        recyclerView.setAdapter(recycle_image_pick);

        return RootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        imageview_in_folder_main.ImageToSend.clear();
        imageview_in_folder_main.ImagesAdded.clear();
        imageview_in_folder_main.ImageDeleted.clear();
        imageview_in_folder_main.TextArrayCreated.clear();
        imageview_in_folder_main.TextViewArray.clear();
    }
}
