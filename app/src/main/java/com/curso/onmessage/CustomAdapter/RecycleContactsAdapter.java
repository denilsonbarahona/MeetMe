package com.curso.onmessage.CustomAdapter;

import android.content.Context;
import android.content.Intent;
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
import com.curso.onmessage.Models.Providers.ContactsProviders;
import com.curso.onmessage.MessageActitvity.MessageMain;
import com.curso.onmessage.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by PC-PRAF on 24/9/2016.
**/

public class RecycleContactsAdapter extends RecyclerView.Adapter<RecycleContactsAdapter.ContactsViewHolder> {

    private static ArrayList<String> ContactsName ;
    private static ArrayList<String> ContactsImage ;
    private static Context contexto;
    private static ArrayList<String> ContactsPhoneNumber;

    public static void InitArrayContact(Context context)
    {
        ContactsName = new ArrayList<>();
        ContactsImage = new ArrayList<>();
        ContactsPhoneNumber = new ArrayList<>();
        contexto = context;
        Cursor cursor;
        ContactsProviders contactsProviders = new ContactsProviders();
        cursor =  contactsProviders.GetContactsInformation(context);
        String[] strings = new String[2];
        while (cursor.moveToNext())
        {
            strings[0]= cursor.getString(1);
            strings[1] =  cursor.getString(4);
            ContactsName.add(strings[0]);
            if(strings[1] == null)
            {
                Uri uri = Uri.parse(String.valueOf("android.resource://"+contexto.getPackageName()+"/drawable/profile"));
                ContactsImage.add(String.valueOf(uri));
            }else
            {
                ContactsImage.add(strings[1]);
            }
            ContactsPhoneNumber.add(cursor.getString(2));
        }
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts , parent , false);
        ContactsViewHolder viewHolder = new ContactsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, final int position)
    {
        Bitmap bitmap;
        RoundedBitmapDrawable roundedBitmapDrawable = null;

        try
        {
             bitmap = MediaStore.Images.Media.getBitmap(contexto.getContentResolver() , Uri.parse(ContactsImage.get(position)));
             roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(contexto.getResources(),bitmap);
             roundedBitmapDrawable.setCornerRadius(bitmap.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.ImageViewContact.setImageDrawable(roundedBitmapDrawable);
        holder.TextViewContacName.setText(ContactsName.get(position));

        holder.LayoutContext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , MessageMain.class);
                intent.putExtra("PhoneNumber" , ContactsPhoneNumber.get(position) );
                intent.putExtra("ContactName" , ContactsName.get(position) );
                intent.putExtra("ContactProfileImage" , ContactsImage.get(position));
                intent.putExtra("behavior","1");
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ContactsName.size();
    }


    public class ContactsViewHolder extends RecyclerView.ViewHolder
    {

        public TextView TextViewContacName;
        public ImageView ImageViewContact;
        public LinearLayout LayoutContext;

        public ContactsViewHolder(View itemView)
        {
            super(itemView);
            this.TextViewContacName = (TextView)itemView.findViewById(R.id.ContactName);
            this.ImageViewContact =(ImageView)itemView.findViewById(R.id.ContactImage);
            this.LayoutContext = (LinearLayout)itemView.findViewById(R.id.ContactContent);
        }
    }
}
