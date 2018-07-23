package com.curso.onmessage.ImageFullscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.curso.onmessage.R;
import com.curso.onmessage.PickActivity.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

/**
 * Created by PC-PRAF on 10/12/2016.
 **/

public class ImageFullScreenSend extends AppCompatActivity
{

    public static ViewPager pager;
    private PagerAdapter adapter;
    private TextView countImagePage;
    private static int Position = 0;
    private int index = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_full_send);
        setToolbar();

        pager = (ViewPager) findViewById(R.id.image_container);
        adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position)
            {
                InputMethodManager ImM = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                ImM.hideSoftInputFromWindow( imageview_in_folder_main.TextViewArray.get(Position).getWindowToken(), 0);
                countImagePage.setText(String.valueOf(position+1)+" / "+String.valueOf(imageview_in_folder_main.ImageToSend.size()));
                Position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    public void setToolbar()
    {

        ImageView imageBack = (ImageView)findViewById(R.id.arrow_back_full_screen);
        countImagePage =(TextView)findViewById(R.id.TextNameSend_Image);
        countImagePage.setText("1 / "+String.valueOf(imageview_in_folder_main.ImageToSend.size()));
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarFullScreen);
        setSupportActionBar(toolbar);

        final ImageView imageDelete = (ImageView) findViewById(R.id.remove_image);
        imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                   index = 0;
                   imageview_in_folder_main.ImageDeleted.add(imageview_in_folder_main.ImageToSend.get(pager.getCurrentItem()));
                   imageview_in_folder_main.ImageToSend.remove(pager.getCurrentItem());
                   imageview_in_folder_main.TextArrayCreated.get(pager.getCurrentItem())[1]="d";
                    boolean next = false;
                    for(int i = 0 ; i < imageview_in_folder_main.TextArrayCreated.size(); i++){
                        if(imageview_in_folder_main.TextArrayCreated.get(i)[1]=="d"){
                            next = true;
                        }
                        if(i != (imageview_in_folder_main.ImageToSend.size() -1)){
                            if(next){
                                imageview_in_folder_main.TextArrayCreated.get(i)[3] = String.valueOf( i + 1 );
                            }
                        }
                    }

                    if(imageview_in_folder_main.ImageToSend.size()== 0)
                    {
                        onBackPressed();
                    }else
                    {
                        adapter.notifyDataSetChanged();
                        pager.setAdapter(adapter);
                        pager.setCurrentItem(pager.getCurrentItem());
                        countImagePage.setText(String.valueOf(pager.getCurrentItem()+1)+" / "+String.valueOf(imageview_in_folder_main.ImageToSend.size()));
                    }
                } catch (Exception e)
                {
                    Toast.makeText(v.getContext() , e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private  class ScreenSlidePagerAdapter  extends FragmentStatePagerAdapter{

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return  image_slider.create(position);
        }

        @Override
        public int getCount() {
            return imageview_in_folder_main.ImageToSend.size() ;
        }

    }
}
