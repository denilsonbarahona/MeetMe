package com.curso.onmessage.PickActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.curso.onmessage.CustomAdapter.RecycleAudioAdapter;
import com.curso.onmessage.CustomAdapter.RecycleFileAttached;
import com.curso.onmessage.R;

/**
 * Created by PC-PRAF on 27/11/2016.
 */

public class FilePickActivity extends AppCompatActivity {

    private RecycleFileAttached recycleFileAttached;
    public static TextView CountFileAttached;
    public static TextView SendFilesView;
    public static TextView TextViewAddFiles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.file_pick_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.file_attached_toolbar);
        SetToolBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_file_pick);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        this.CountFileAttached = (TextView) findViewById(R.id.CountFileAttached);
        this.SendFilesView = (TextView) findViewById(R.id.SendFilesPick);
        this.TextViewAddFiles = (TextView) findViewById(R.id.text_view_add);

        if (getIntent().getStringExtra("type_attached").equals("files")) {
            recycleFileAttached = new RecycleFileAttached(getApplicationContext());
            recyclerView.setAdapter(recycleFileAttached);
        }else if(getIntent().getStringExtra("type_attached").equals( "audio" )){
            RecycleAudioAdapter recycleAudioAdapter = new RecycleAudioAdapter(this.getApplicationContext());
            recyclerView.setAdapter(recycleAudioAdapter);
        }

    }


    private void SetToolBar(Toolbar toolbar) {
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
