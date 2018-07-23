package com.curso.onmessage.GroupActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.curso.onmessage.CustomAdapter.CustomGridLayoutManager;
import com.curso.onmessage.CustomAdapter.RecycleContactinGroup;
import com.curso.onmessage.CustomAdapter.Recycle_image_pick;
import com.curso.onmessage.R;

/**
 * Created by PC-PRAF on 31/1/2017.
 */

public class SearchImageGroupProfile extends AppCompatActivity {

    private CustomGridLayoutManager gridLayoutManager;
    public static String ImageSelectedToProfile="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_profileimage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_pick);
        SetToolBar(toolbar);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycle_image_pick);
        gridLayoutManager = new CustomGridLayoutManager(getApplicationContext(),500);
        Recycle_image_pick recycle_image_pick = new Recycle_image_pick(getApplicationContext() , 0);
        recyclerView.setLayoutManager( gridLayoutManager);
        recyclerView.setAdapter(recycle_image_pick);

    }


    private void SetToolBar(Toolbar toolbar)
    {
        ImageView imageBack = (ImageView)findViewById(R.id.arrow_back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);
    }


}
