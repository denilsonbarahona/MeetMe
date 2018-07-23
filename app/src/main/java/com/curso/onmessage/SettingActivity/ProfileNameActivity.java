package com.curso.onmessage.SettingActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.curso.onmessage.R;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

/**
 * Created by PC-PRAF on 6/2/2017.
 */

public class ProfileNameActivity extends AppCompatActivity {

    private EmojiconEditText emojiEditText;
    private ViewGroup RootView;
    private EmojIconActions emojIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);

        Toolbar toolbar = (Toolbar) findViewById(R.id.setting_Toolbar);
        SetToolBar(toolbar);

        emojiEditText = (EmojiconEditText)findViewById(R.id.text_name);
        ImageView BtnEmoji =  (ImageView)findViewById(R.id.Add_Emoji_Btn);
        RootView = (ViewGroup)findViewById(R.id.rootmain_view);
        emojIcon = new EmojIconActions(this,RootView,emojiEditText,BtnEmoji);
        emojIcon.ShowEmojIcon();

        emojiEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojIcon.closeEmojIcon();
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
