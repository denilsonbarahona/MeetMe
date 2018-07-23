package com.curso.onmessage.SettingActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.curso.onmessage.R;

/**
 * Created by PC-PRAF on 12/2/2017.
 */

public class SettingChangeNumber extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_number);

        Toolbar toolbar = (Toolbar) findViewById(R.id.setting_Toolbar);
        SetToolBar(toolbar);


        RelativeLayout layoutNextStep = (RelativeLayout)findViewById(R.id.NextStep);
        layoutNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( v.getContext() , ProfileNewNumber.class);
                v.getContext().startActivity(intent);
            }
        });

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
