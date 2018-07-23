package com.curso.onmessage.ImageFullscreen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.curso.onmessage.R;

import java.io.File;
import java.io.IOException;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by PC-PRAF on 30/10/2016.
 */

public class ImageFullScreen_Main extends AppCompatActivity implements PhotoViewAttacher.OnViewTapListener {

    PhotoViewAttacher photoView=null;
    AppBarLayout barLayout;
    LinearLayout linearLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.image_full_screen_activity);
         setToolbar();

         barLayout = (AppBarLayout) findViewById(R.id.bar_layout_full_screen);
         linearLayout = (LinearLayout) findViewById(R.id.linear_layout_full_screen);
         ImageView imagePlayIcon = (ImageView) findViewById(R.id.ic_play_icon);

         ImageView imageViewFullScreen = (ImageView) findViewById(R.id.ImageFullScreen);
         if (getIntent().getStringExtra("type_media").equals( "i" ) )
         {
             imagePlayIcon.setVisibility(View.GONE);
             try {
                 Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(new File(getIntent().getStringExtra("image_path"))));
                 imageViewFullScreen.setImageBitmap(bitmap);

             } catch (IOException e) {
                 e.printStackTrace();
             }

             photoView = new PhotoViewAttacher(imageViewFullScreen);
             photoView.setOnViewTapListener(this);
         }
        else
         {
             ImmersiveModeOn();
             imagePlayIcon.setVisibility(View.VISIBLE);
             Glide.with(this.getApplicationContext()).
                     load(Uri.fromFile(new File(getIntent().getStringExtra("image_path")))).
                     into(imageViewFullScreen);

             imageViewFullScreen.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getIntent().getStringExtra("image_path")));
                     intent.setDataAndType(Uri.parse(getIntent().getStringExtra("image_path")), "video/*");
                     startActivity(intent);
                 }
             });

             /*Init Intent to show video selected*/
             Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getIntent().getStringExtra("image_path")));
             intent.setDataAndType(Uri.parse(getIntent().getStringExtra("image_path")), "video/*");
             startActivity(intent);

         }
    }

    public void setToolbar() {

        ImageView profileFull = (ImageView)findViewById(R.id.profile_full_screen);
        ImageView imageBack = (ImageView)findViewById(R.id.arrow_back_full_screen);
        TextView ContactName =(TextView)findViewById(R.id.TextNameSend_Image);
        ContactName.setText(getIntent().getStringExtra("Name_send_image"));
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(getIntent().getStringExtra("behavior").equals("1")){

            Bitmap bitmap;
            RoundedBitmapDrawable roundedBitmapDrawable = null;
            try
            {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver() , Uri.parse(getIntent().getStringExtra("profile_send_full")));
                roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(this.getResources(),bitmap);
                roundedBitmapDrawable.setCornerRadius(10);
            } catch (IOException e) {
                e.printStackTrace();
            }


            profileFull.setImageDrawable(roundedBitmapDrawable);

        }else{
            profileFull.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.colored_circle_draw));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarFullScreen);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_full_screen , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewTap(View view, float x, float y) {
        int OptionUi = getWindow().getDecorView().getSystemUiVisibility();
        boolean ImmersiveModeStatus = ((OptionUi |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY )== OptionUi);

        if(!ImmersiveModeStatus)
        {
            ImmersiveModeOn();
        }
        else
        {
            ImmersiveModeOff();
        }
    }

    private void ImmersiveModeOn()
    {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        barLayout.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
    }


    private void ImmersiveModeOff()
    {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        barLayout.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
    }

}
