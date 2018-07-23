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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso.onmessage.GroupActivity.GroupActivity;
import com.curso.onmessage.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by PC-PRAF on 10/1/2017.
 */

public class RecycleContactinGroup extends RecyclerView.Adapter<RecycleContactinGroup.HolderInGroup> {

    public static ArrayList<String[]> ContactsSelected = new ArrayList<>();
    private Context context;

    public RecycleContactinGroup(Context cntx){
        context = cntx;
    }

    public  void AddNewItem(String uri , String Name , String position){
        ContactsSelected.add(new String[]{uri , Name , position});
        notifyDataSetChanged();
    }

    public void RemoveItem(String Position){

        for(Iterator<String[]> iterator = ContactsSelected.iterator(); iterator.hasNext();) {
            if(iterator.next()[2]==String.valueOf(Position)) {
                iterator.remove();
            }
        }
        notifyDataSetChanged();

    }


    @Override
    public HolderInGroup onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_in_group,parent,false);
        HolderInGroup holderInGroup = new HolderInGroup(view);
        return holderInGroup;
    }

    @Override
    public void onBindViewHolder(HolderInGroup holder, final int position) {

        Bitmap bitmap;
        RoundedBitmapDrawable roundedBitmapDrawable = null;

        try
        {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver() , Uri.parse(ContactsSelected.get(position)[0]));
            roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(),bitmap);
            roundedBitmapDrawable.setCornerRadius(bitmap.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.ImageProfile.setImageDrawable(roundedBitmapDrawable);
        holder.ContactsName.setText(ContactsSelected.get(position)[1]);
        holder.LayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupActivity.RemoveItem(ContactsSelected.get(position)[2]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ContactsSelected.size();
    }

    public class HolderInGroup extends RecyclerView.ViewHolder {

        LinearLayout LayoutItem;
        TextView ContactsName;
        ImageView ImageProfile;

        public HolderInGroup(View itemView) {
            super(itemView);

            this.LayoutItem = (LinearLayout) itemView.findViewById(R.id.main_layout);
            this.ImageProfile = (ImageView) itemView.findViewById(R.id.image_profile);
            this.ContactsName =(TextView) itemView.findViewById(R.id.contact_name);
        }
    }
}
