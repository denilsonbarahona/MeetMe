package com.curso.onmessage.ImageFullscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.curso.onmessage.CustomAdapter.ViewPagerAdapter;
import com.curso.onmessage.PickActivity.imageview_in_folder_main;
import com.curso.onmessage.R;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

/**
 * Created by PC-PRAF on 28/1/2017.
 */

public class ImageSendFull extends AppCompatActivity
{

    private TextView countImagePage;
    private ViewPager mViewPager;
    private ViewPagerAdapter viewPagerAdapter;
    public static int CurrentPosition;
    private ViewGroup root;
    private EmojIconActions emojIcon;
    private ImageView BtnEmoji;
    EmojiconEditText EmojiEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_full_send_2);
        setToolbar();

        EmojiEditText = (EmojiconEditText)findViewById(R.id.EmojiEditText_2);
        BtnEmoji =  (ImageView)findViewById(R.id.Add_Emoji_Btn);
        root = (ViewGroup) findViewById(R.id.Rootview_Message_pager);
        emojIcon = new EmojIconActions( getApplicationContext() , root , EmojiEditText , BtnEmoji );
        emojIcon.ShowEmojIcon();

        mViewPager = (ViewPager) findViewById(R.id.container);
        SetViewPager( );
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position)
            {
                InputMethodManager ImM = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                ImM.hideSoftInputFromWindow( EmojiEditText.getWindowToken(), 0);
                countImagePage.setText(String.valueOf(position+1)+" / "+String.valueOf(imageview_in_folder_main.ImageToSend.size()));
                EmojiEditText.setText(imageview_in_folder_main.TextToSend.get(position)[1]);
                EmojiEditText.setSelection(EmojiEditText.getText().length());
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        EmojiEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                imageview_in_folder_main.TextToSend.get(mViewPager.getCurrentItem())[1]=""+s;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void SetViewPager()
    {
       viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        for(int i = 0 ; i < imageview_in_folder_main.ImageToSend.size() ; i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("page" , i);
            imageFullSend im = new imageFullSend();
            im.setArguments(bundle);
            viewPagerAdapter.AddFragment( im , null);
        }
        mViewPager.setAdapter(viewPagerAdapter);
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

        ImageView imageDelete = (ImageView) findViewById(R.id.remove_image);
        imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    imageview_in_folder_main.ImageDeleted.add(imageview_in_folder_main.ImageToSend.get(mViewPager.getCurrentItem()));
                    imageview_in_folder_main.ImageToSend.remove(mViewPager.getCurrentItem());
                    imageview_in_folder_main.TextToSend.remove(mViewPager.getCurrentItem());

                    if (imageview_in_folder_main.ImageToSend.size() == 0) {
                        onBackPressed();
                    } else {
                        viewPagerAdapter.notifyDataSetChanged();
                        SetViewPager();
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem());
                        countImagePage.setText(String.valueOf(mViewPager.getCurrentItem() + 1) + " / " + String.valueOf(imageview_in_folder_main.ImageToSend.size()));
                        EmojiEditText.setText(imageview_in_folder_main.TextToSend.get(mViewPager.getCurrentItem())[1]);
                        EmojiEditText.setSelection(EmojiEditText.getText().length());
                    }
                }catch (Exception e){
                    Toast.makeText(v.getContext() , e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
