package com.curso.onmessage.SettingActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.curso.onmessage.CustomAdapter.RecyclerSettingsAdapter;
import com.curso.onmessage.R;

import java.io.IOException;

/**
 * Created by PC-PRAF on 7/1/2017.
 **/

public class SettingActity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.setting_Toolbar);
        SetToolBar(toolbar);


        RecyclerSettingsAdapter recyclerSettingsAdapter = new RecyclerSettingsAdapter(this.getApplicationContext() , this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_setting_menu);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerSettingsAdapter);
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
