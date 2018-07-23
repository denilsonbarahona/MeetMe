package com.curso.onmessage.ImageFullscreen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.curso.onmessage.R;
import java.io.File;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

/**
 * Created by PC-PRAF on 25/12/2016.
 */

public class VideoFullSceenSend extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_full_screen);
        setToolbar();

        ImageView imageView = (ImageView) findViewById(R.id.video_full);

        Glide.with(this.getApplicationContext())
             .load(Uri.fromFile(new File(getIntent().getStringExtra("path_file"))))
             .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getIntent().getStringExtra("path_file")));
                intent.setDataAndType(Uri.parse(getIntent().getStringExtra("path_file")), "video/*");
                startActivity(intent);
            }
        });

        ImageView BtnImoji = (ImageView)findViewById(R.id.Add_Emoji_Btn);
        EmojiconEditText EditText = (EmojiconEditText)findViewById(R.id.txt_message_video);
        ViewGroup RootView = (ViewGroup)findViewById(R.id.Rootview_message_video);
        EmojIconActions emojIconActions = new EmojIconActions(this.getApplicationContext() ,
                                                                RootView , EditText,  BtnImoji);
        emojIconActions.ShowEmojIcon();
    }


    public void setToolbar() {
        ImageView imageBack = (ImageView) findViewById(R.id.arrow_back_full_screen);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarFullScreen);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
