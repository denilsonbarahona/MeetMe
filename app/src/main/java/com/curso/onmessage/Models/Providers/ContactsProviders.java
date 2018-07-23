package com.curso.onmessage.Models.Providers;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

/**
 * Created by PC-PRAF on 24/9/2016.
 */

public class ContactsProviders
{
    public Cursor GetContactsInformation(Context context)
    {
        String[] projection = new String[] {
                ContactsContract.Data._ID ,
                ContactsContract.Data.DISPLAY_NAME ,
                ContactsContract.CommonDataKinds.Phone.NUMBER ,
                ContactsContract.CommonDataKinds.Phone.TYPE ,
                ContactsContract.Data.PHOTO_THUMBNAIL_URI };

        String WhereClause = ContactsContract.Data.MIMETYPE  + " = '" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE+ "' AND " +
                ContactsContract.CommonDataKinds.Phone.NUMBER+" IS NOT NULL " ;
        String Order = ContactsContract.Data.DISPLAY_NAME+" ASC";

        Cursor cursor = context.getContentResolver().query(
            ContactsContract.Data.CONTENT_URI ,
                projection ,
                WhereClause ,
                null,
                Order
        );

        return  cursor;
    }
}
