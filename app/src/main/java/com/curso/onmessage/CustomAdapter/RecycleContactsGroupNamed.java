package com.curso.onmessage.CustomAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.curso.onmessage.R;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by PC-PRAF on 15/1/2017.
 */

public class RecycleContactsGroupNamed extends RecyclerView.Adapter<RecycleContactsGroupNamed.Holder> {

    private Context context;

    public RecycleContactsGroupNamed(Context cntx){
        context = cntx;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_group , parent , false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Bitmap bitmap;
        RoundedBitmapDrawable roundedBitmapDrawable = null;

        try
        {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver() , Uri.parse(RecycleContactinGroup.ContactsSelected.get(position)[0]));
            roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(),bitmap);
            roundedBitmapDrawable.setCornerRadius(bitmap.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.ContactProfile.setImageDrawable(roundedBitmapDrawable);
        holder.ContactName.setText(RecycleContactinGroup.ContactsSelected.get(position)[1]);
    }

    @Override
    public int getItemCount() {
        return RecycleContactinGroup.ContactsSelected.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView ContactProfile;
        TextView ContactName;

        public Holder(View itemView) {
            super(itemView);

            ContactProfile = (ImageView) itemView.findViewById(R.id.ContactImage);
            ContactName = (TextView) itemView.findViewById(R.id.ContactName);
        }
    }
}
