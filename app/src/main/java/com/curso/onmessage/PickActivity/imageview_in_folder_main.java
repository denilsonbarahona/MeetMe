package com.curso.onmessage.PickActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.curso.onmessage.CustomAdapter.CustomGridLayoutManager;
import com.curso.onmessage.CustomAdapter.RecycleImageInFolder;
import com.curso.onmessage.ImageFullscreen.ImageSendFull;
import com.curso.onmessage.R;
import java.io.File;
import java.util.ArrayList;

import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

/**
 * Created by PC-PRAF on 4/12/2016.
 */

public class imageview_in_folder_main extends AppCompatActivity {

    public static ArrayList<String[]> ImageToSend=new ArrayList<>();
    public static ArrayList<String[]> ImagesAdded = new ArrayList<>();
    public static ArrayList<String[]> ImageDeleted = new ArrayList<>();
    public static ArrayList<EmojiconEditText> TextViewArray = new ArrayList<>();
    public static ArrayList<String[]> TextArrayCreated = new ArrayList<>();
    public static TextView CountSelectedItem;
    public static TextView SendSelectedItem;
    public static ArrayList<String[]> TextToSend = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_in_folder_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.imageView_Toolbar);
        SetToolBar(toolbar);

        CustomGridLayoutManager gridLayoutManager = new CustomGridLayoutManager(this.getApplicationContext(),300);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.image_in_folder);
        File file = new File(getIntent().getStringExtra("parent_file")).getParentFile();
        RecycleImageInFolder recycleImageInFolder = new RecycleImageInFolder(
                this.getApplicationContext() ,file.getAbsolutePath(),getIntent().getStringExtra("folder_name") , getIntent().getIntExtra("behavior" , -1));
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(recycleImageInFolder);

        CountSelectedItem = (TextView)findViewById(R.id.folder_imageview_name);
        SendSelectedItem = (TextView)findViewById(R.id.SendImagePick);

        SendSelectedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageview_in_folder_main.ImageDeleted.size()!=0) {
                    try {
                        imageview_in_folder_main.ImageDeleted.clear();
                    } catch (Exception e){
                        Toast.makeText(v.getContext() , e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
             /*   Intent intent = new Intent(v.getContext() , ImageFullScreenSend.class);
                v.getContext().startActivity(intent);*/
                Intent intent = new Intent(v.getContext() , ImageSendFull.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    private void SetToolBar(Toolbar toolbar) {
        ImageView imageBack = (ImageView)findViewById(R.id.arrow_back);
        TextView textViewTitle = (TextView)findViewById(R.id.folder_imageview_name);
        textViewTitle.setText(getIntent().getStringExtra("folder_name"));

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        imageview_in_folder_main.ImageToSend.addAll(imageview_in_folder_main.ImageDeleted);
    }
}
