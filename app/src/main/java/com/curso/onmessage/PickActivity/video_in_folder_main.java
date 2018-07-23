package com.curso.onmessage.PickActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.curso.onmessage.CustomAdapter.CustomGridLayoutManager;
import com.curso.onmessage.CustomAdapter.RecycleVideoInFolder;
import com.curso.onmessage.R;

import java.io.File;

/**
 * Created by PC-PRAF on 24/12/2016.
 */

public class video_in_folder_main extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_in_folder_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.video_folder_toolbar);
        SetToolBar(toolbar);

        CustomGridLayoutManager gridLayoutManager = new CustomGridLayoutManager(this.getApplicationContext() , 300);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.Recycle_video);
        File file = new File(getIntent().getStringExtra("parent_file")).getParentFile();
        RecycleVideoInFolder recycleVideoInFolder = new RecycleVideoInFolder(
                    this.getApplicationContext() ,file.getAbsolutePath() , getIntent().getStringExtra("folder_name"));
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(recycleVideoInFolder);
    }


    private void SetToolBar(Toolbar toolbar) {
        ImageView imageBack = (ImageView)findViewById(R.id.arrow_back);
        TextView textViewTitle = (TextView)findViewById(R.id.folder_name);
        textViewTitle.setText(getIntent().getStringExtra("folder_name"));

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);
    }

}
