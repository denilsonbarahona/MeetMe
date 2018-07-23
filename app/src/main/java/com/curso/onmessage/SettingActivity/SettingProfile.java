package com.curso.onmessage.SettingActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.curso.onmessage.CustomAdapter.RecycleProfileOption;
import com.curso.onmessage.R;

/**
 * Created by PC-PRAF on 5/2/2017.
 */

public class SettingProfile extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.setting_Toolbar);
        SetToolBar(toolbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecycleProfileOption profileoption = new RecycleProfileOption(this.getApplicationContext());
        RecyclerView recyclerView =  (RecyclerView)findViewById(R.id.profileoption);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(profileoption);

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
