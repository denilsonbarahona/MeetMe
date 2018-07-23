package com.curso.onmessage.MessageActitvity;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.curso.onmessage.CustomAdapter.BottomSheetFragment_;
import com.curso.onmessage.CustomAdapter.RecycleChatAdapter;
import com.curso.onmessage.CustomAdapter.RecycleContactinGroup;
import com.curso.onmessage.R;
import java.io.IOException;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;

/**
 * Created by PC-PRAF on 5/10/2016.
 */

public class MessageMain extends AppCompatActivity
{
    hani.momanii.supernova_emoji_library.Helper.EmojiconEditText emojiEditText;
    ViewGroup RootView;
    String pathImageProfile = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.message_Toolbar);
        SetToolBar(toolbar);

        RecyclerView recyclerView =  (RecyclerView) findViewById(R.id.Recycle_message);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        if(getIntent().getStringExtra("behavior").equals("1")) {
            pathImageProfile=getIntent().getStringExtra("ContactProfileImage");
        }


        RecycleChatAdapter MessageAdapter = new RecycleChatAdapter(
                                                    this.getApplicationContext() ,
                                                    getIntent().getStringExtra("ContactName"),
                                                    pathImageProfile ,
                                                    getIntent().getStringExtra("behavior")
        );
        recyclerView.setAdapter(MessageAdapter);

        emojiEditText = (hani.momanii.supernova_emoji_library.Helper.EmojiconEditText)findViewById(R.id.EmojiEditText);
        ImageView BtnEmoji =  (ImageView)findViewById(R.id.Add_Emoji_Btn);
        RootView = (ViewGroup)findViewById(R.id.Message_RootView);
        EmojIconActions emojIcon=new EmojIconActions(this,RootView,emojiEditText,BtnEmoji);
        emojIcon.ShowEmojIcon();

        ImageView AttachedFileSend = (ImageView)findViewById(R.id.Attached_element);
        AttachedFileSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                BottomSheetDialogFragment bottomSheetDialogFragment = new BottomSheetFragment_();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });

    }

    private void SetToolBar(Toolbar toolbar)
    {
        ImageView imageBack = (ImageView)findViewById(R.id.arrow_back);
        TextView ContactName =(TextView)findViewById(R.id.ContactProfileName);
        ImageView imageProfile =(ImageView)findViewById(R.id.PhotoUserFront);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ContactName.setText(getIntent().getStringExtra("ContactName"));

        if(getIntent().getStringExtra("behavior").equals("1")) {

            Bitmap bitmap;
            RoundedBitmapDrawable roundedBitmapDrawable = null;

            try
            {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver() , Uri.parse(getIntent().getStringExtra("ContactProfileImage")));
                roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(this.getResources(),bitmap);
                roundedBitmapDrawable.setCornerRadius(bitmap.getHeight());
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageProfile.setImageDrawable(roundedBitmapDrawable);

        }else{
            imageProfile.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.colored_circle_draw));
        }

        setSupportActionBar(toolbar);
    }

    @Override
    protected void onPause() {
        super.onPause();
        RecycleChatAdapter.setCanceled(true);
        if(RecycleChatAdapter.getCanceled() && RecycleChatAdapter.getMediaPlayer()!=null) {
            RecycleChatAdapter.getMediaPlayer().release();
            RecycleChatAdapter.setMediaPlayer(null);
            RecycleChatAdapter.setIsPlaying(-1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }





}

