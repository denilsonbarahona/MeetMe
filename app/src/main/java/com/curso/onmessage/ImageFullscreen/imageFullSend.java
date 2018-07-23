package com.curso.onmessage.ImageFullscreen;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.curso.onmessage.PickActivity.imageview_in_folder_main;
import com.curso.onmessage.R;

import java.io.File;

/**
 * Created by PC-PRAF on 28/1/2017.
 */

public class imageFullSend extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.activity_image_pager , container , false);
        ImageView imageView = (ImageView)RootView.findViewById(R.id.image_in_slider);

        Glide.with(RootView.getContext())
                .load(Uri.fromFile( new File(imageview_in_folder_main.ImageToSend.get(getArguments().getInt("page"))[0])))
                .into(imageView);

        if(getArguments().getInt("page") > imageview_in_folder_main.TextToSend.size() -1 ) {
            imageview_in_folder_main.TextToSend.add(new String[]{String.valueOf(getArguments().getInt("page")),""});
        }

        return RootView;
    }
}
