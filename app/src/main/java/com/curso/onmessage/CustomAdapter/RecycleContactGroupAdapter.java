package com.curso.onmessage.CustomAdapter;

import android.content.Context;
import android.database.Cursor;
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
import com.curso.onmessage.Models.Providers.ContactsProviders;
import com.curso.onmessage.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by PC-PRAF on 8/1/2017.
 */

public class RecycleContactGroupAdapter extends RecyclerView.Adapter<RecycleContactGroupAdapter.ContactHolder> {

    private static ArrayList<String> ContactsName ;
    private static ArrayList<String> ContactsImage ;
    private static Context context;
    private static ArrayList<String[]> ContactAdded;
    private static ArrayList<String[]> ContactsPhoneNumber;
    private RecycleContactinGroup ContactInGroup;


    public RecycleContactGroupAdapter(Context cntx){

        context = cntx;
        ContactsName = new ArrayList<>();
        ContactsImage = new ArrayList<>();
        ContactsPhoneNumber = new ArrayList<>();
        ContactAdded = new ArrayList<>();
        ContactInGroup = new RecycleContactinGroup(context);

        Cursor cursor;
        ContactsProviders contactsProviders = new ContactsProviders();
        cursor =  contactsProviders.GetContactsInformation(context);
        String[] strings = new String[2];
        while (cursor.moveToNext()) {
            strings[0]= cursor.getString(1);
            strings[1] =  cursor.getString(4);
            ContactsName.add(strings[0]);
            if(strings[1] == null) {
                Uri uri = Uri.parse(String.valueOf("android.resource://"+context.getPackageName()+"/drawable/profile"));
                ContactsImage.add(String.valueOf(uri));
            }else {
                ContactsImage.add(strings[1]);
            }
            ContactsPhoneNumber.add(new String[]{ cursor.getString(2) ,"0" });
        }

    }


    public void ItemRemovedFromGroup(String Position){
        ContactsPhoneNumber.get(Integer.parseInt(Position))[1]="0";
        notifyDataSetChanged();
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_contact_group,parent,false);

        ContactHolder holder = new ContactHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ContactHolder holder, final int position) {

        Bitmap bitmap;
        RoundedBitmapDrawable roundedBitmapDrawable = null;

        try
        {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver() , Uri.parse(ContactsImage.get(position)));
            roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(),bitmap);
            roundedBitmapDrawable.setCornerRadius(bitmap.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ContactsPhoneNumber.get(position)[1]=="1") {
            holder.ToggleButton.setImageDrawable(context.getResources().getDrawable(R.drawable.toggle_on));
        }else{
            holder.ToggleButton.setImageDrawable(null);
        }

        holder.imageProfile.setImageDrawable(roundedBitmapDrawable);
        holder.ContactName.setText(ContactsName.get(position));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     if(ContactsPhoneNumber.get(position)[1]=="1"){
                         ContactsPhoneNumber.get(position)[1]="0";
                         holder.ToggleButton.setImageDrawable(null);
                         for(Iterator<String[]> iterator = ContactAdded.iterator(); iterator.hasNext();) {
                             if(iterator.next()[0]==String.valueOf(position)) {
                                 iterator.remove();
                             }
                         }
                         GroupActivity.RemoveItem(String.valueOf(position));
                     }else{
                         ContactsPhoneNumber.get(position)[1]="1";
                         holder.ToggleButton.setImageDrawable(context.getResources().getDrawable(R.drawable.toggle_on));
                         ContactAdded.add(new String[]{String.valueOf(position) ,
                                                        ContactsPhoneNumber.get(position)[1]
                                                    });
                         GroupActivity.AddNewItem(ContactsImage.get(position),ContactsName.get(position) , String.valueOf(position));
                     }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ContactsName.size();
    }

    public class ContactHolder extends RecyclerView.ViewHolder {

        ImageView imageProfile;
        TextView ContactName;
        LinearLayout linearLayout;
        ImageView ToggleButton;

        public ContactHolder(View itemView) {
            super(itemView);

            imageProfile = (ImageView) itemView.findViewById(R.id.ContactImage);
            ContactName = (TextView) itemView.findViewById(R.id.ContactName);
            ToggleButton = (ImageView) itemView.findViewById(R.id.btn_toggle);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.ContactContent);
        }
    }
}
