package com.curso.onmessage.CustomAdapter;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.ImageView;

import com.curso.onmessage.PickActivity.FilePickActivity;
import com.curso.onmessage.PickActivity.MultimediaPickActivity;
import com.curso.onmessage.R;

/**
 * Created by PC-PRAF on 26/11/2016.
 */

public class BottomSheetFragment_ extends BottomSheetDialogFragment
{
    ImageView FileAttached;
    ImageView AudioAttached;
    ImageView GalleryAttached;

    private BottomSheetBehavior.BottomSheetCallback BottomSheetBehaviorCallback =
            new BottomSheetBehavior.BottomSheetCallback()
    {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if(newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {}
    };

    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = View.inflate(getContext(), R.layout.bottom_sheet_pick, null);
        dialog.setContentView(view);

        CoordinatorLayout.LayoutParams layoutParams=(CoordinatorLayout.LayoutParams)((View)view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();

        if(behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior)behavior).setBottomSheetCallback(BottomSheetBehaviorCallback);

            FileAttached = (ImageView)view.findViewById(R.id.file_attached);
            AudioAttached = (ImageView)view.findViewById(R.id.audio_attached);
            GalleryAttached = (ImageView)view.findViewById(R.id.gallery_attached);

            FileAttached.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), FilePickActivity.class);
                    intent.putExtra("type_attached","files");
                    dialog.dismiss();
                    startActivity(intent);
                }
            });

            AudioAttached.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), FilePickActivity.class);
                    intent.putExtra("type_attached","audio");
                    dialog.dismiss();
                    startActivity(intent);

                }
            });

            GalleryAttached.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MultimediaPickActivity.class);
                    dialog.dismiss();
                    startActivity(intent);
                }
            });

        }
    }

}

