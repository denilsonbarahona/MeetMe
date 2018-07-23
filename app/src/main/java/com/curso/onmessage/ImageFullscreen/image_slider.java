package com.curso.onmessage.ImageFullscreen;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.curso.onmessage.R;
import com.curso.onmessage.PickActivity.*;
import com.google.android.gms.vision.text.Text;

import java.io.File;
import java.util.Iterator;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import hani.momanii.supernova_emoji_library.Helper.EmojiconsPopup;


/**
 * Created by PC-PRAF on 10/12/2016.
 */

public class image_slider extends Fragment {

    private int mpageCount;
    private ViewGroup RootView;
    private EmojIconActions emojIcon;
    private ImageView BtnEmoji;

    public static image_slider create(int pagenumber){
        image_slider fragment = new image_slider();
        Bundle bundle = new Bundle();
        bundle.putInt("page" , pagenumber);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mpageCount = getArguments().getInt("page");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.item_image_pager, container,false);
        int index = 0;
        boolean UseIndex = false;

        ImageView imageView = (ImageView)rootview.findViewById(R.id.image_in_slider);
        Glide.with(rootview.getContext())
                .load(Uri.fromFile( new File(imageview_in_folder_main.ImageToSend.get(mpageCount)[0])))
                .into(imageView);

        LinearLayout linearLayout = (LinearLayout) rootview.findViewById(R.id.text_layout);

        boolean created= false ;

        for(Iterator<String[]> iterator = imageview_in_folder_main.TextArrayCreated.iterator(); iterator.hasNext();) {
            if(iterator.next()[0] == String.valueOf(mpageCount)){
                created = true;
            }
        }

        if(!created)
        {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.MATCH_PARENT);

            EmojiconEditText TextBoxEmoji = new EmojiconEditText(rootview.getContext());
            TextBoxEmoji.setLayoutParams(layoutParams);
            TextBoxEmoji.setPadding((int) getResources().getDimension(R.dimen.paddingEmojinLeftRight),
                    (int) getResources().getDimension(R.dimen.paddingEmojinTopBottom),
                    (int) getResources().getDimension(R.dimen.paddingEmojinLeftRight),
                    (int) getResources().getDimension(R.dimen.paddingEmojinTopBottom));
            TextBoxEmoji.setMaxLines(5);
            TextBoxEmoji.setId(mpageCount);
            TextBoxEmoji.setHint("Type Something ...");
            TextBoxEmoji.setEmojiconSize((int)getResources().getDimension(R.dimen.emojiSize));
            TextBoxEmoji.setBackground(null);
            TextBoxEmoji.setTextColor(Color.parseColor("#8c8c8c"));
            TextBoxEmoji.setTextSize(16);
            TextBoxEmoji.setHintTextColor(Color.parseColor("#8c8c8c"));
            TextBoxEmoji.setMinHeight(50);
            TextBoxEmoji.setFocusable(true);
            TextBoxEmoji.setText("Denilson + "+String.valueOf(mpageCount));

            imageview_in_folder_main.TextViewArray.add(TextBoxEmoji);
            imageview_in_folder_main.TextArrayCreated.add( new String[] {String.valueOf(mpageCount) ,"a" ,
                                    imageview_in_folder_main.ImageToSend.get(mpageCount)[1]  ,
                                    String.valueOf(mpageCount)
            });
        }

        UseIndex = true;
        index = Integer.parseInt(imageview_in_folder_main.TextArrayCreated.get(mpageCount)[3]);

        if(imageview_in_folder_main.TextViewArray.get(mpageCount).getParent() != null ) {
            ((ViewGroup)imageview_in_folder_main.TextViewArray.get(mpageCount)
                    .getParent()).removeView(imageview_in_folder_main.TextViewArray.get(mpageCount));

            if(imageview_in_folder_main.TextViewArray.get(index).getParent() != null )
            {
                ((ViewGroup)imageview_in_folder_main.TextViewArray.get(index)
                        .getParent()).removeView(imageview_in_folder_main.TextViewArray.get(index));
                linearLayout.addView(imageview_in_folder_main.TextViewArray.get(index));
            }else{
                linearLayout.addView(imageview_in_folder_main.TextViewArray.get(mpageCount));
            }
            /*
            for(String[] item : imageview_in_folder_main.TextArrayCreated){
                if(item[1]!="d" && item[0] == String.valueOf(mpageCount)){
                    UseIndex = true;
                    index = Integer.parseInt(item[3]);
                    break;
                }
                //index = index + 1;
            }*/

        }else {
            linearLayout.addView(imageview_in_folder_main.TextViewArray.get(mpageCount));
        }

        BtnEmoji =  (ImageView)rootview.findViewById(R.id.Add_Emoji_Btn);
        RootView = (ViewGroup) getActivity().findViewById(R.id.Rootview_Message_pager);
        emojIcon = new EmojIconActions( rootview.getContext() , rootview ,
                                        imageview_in_folder_main.TextViewArray.get(mpageCount) ,
                                        BtnEmoji );
        emojIcon.ShowEmojIcon();
        imageview_in_folder_main.TextViewArray.get(mpageCount).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return rootview;
    }


}
