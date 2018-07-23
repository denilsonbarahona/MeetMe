package com.curso.onmessage.Models.Providers;

import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC-PRAF on 4/12/2016.
 */

public class Multimedia_Provider {

    private Context context;

    public Multimedia_Provider(Context cntx) {
        this.context = cntx;
    }

    public ArrayList<String[]> VideoProvider(){

        int countItems=0;
        ArrayList<String> AddedFolders = new ArrayList<>();
        ArrayList<String[]> VideoFolders = new ArrayList<>();

        Cursor VideoProvider = context.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI ,
                new String[]{
                        MediaStore.Video.Media._ID ,
                        MediaStore.Video.Media.BUCKET_ID ,
                        MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                        MediaStore.Video.Media.DATA
                } ,
                null ,
                null ,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME+" DESC , "+MediaStore.Files.FileColumns.DATE_ADDED+" DESC"
        );

        if(VideoProvider.moveToFirst()){
            do {
                if(!AddedFolders.contains(VideoProvider.getString(VideoProvider.getColumnIndex(MediaStore.Video.Media.BUCKET_ID)))) {
                    if (countItems > 0) {
                        VideoFolders.get(VideoFolders.size() - 1)[2] = String.valueOf(countItems);
                    }

                    VideoFolders.add(new String[]{
                            VideoProvider.getString(VideoProvider.getColumnIndex(MediaStore.Video.Media.DATA)),
                            VideoProvider.getString(VideoProvider.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)),
                            "0"
                    });
                    AddedFolders.add(VideoProvider.getString(VideoProvider.getColumnIndex(MediaStore.Video.Media.BUCKET_ID)));
                    countItems = 1;
                } else {
                    countItems = countItems + 1;
                }
            } while (VideoProvider.moveToNext());
        }
        VideoFolders.get(VideoFolders.size() - 1)[2] = String.valueOf(countItems);
        return VideoFolders;
    }

    public ArrayList<String[]> ImageProvider(){
        int countItems=0;
        ArrayList<String> AddedFolders=new ArrayList<>();
        ArrayList<String[]> ImagesFolders = new ArrayList<>();

        Cursor ImageProvider = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI ,
                new String[]{MediaStore.Images.Media._ID ,
                        MediaStore.Images.Media.BUCKET_ID ,
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME ,
                        MediaStore.Images.Media.DATA
                },
                null    ,
                null    ,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME+" DESC , "+MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
        );

        if(ImageProvider.moveToFirst()){
            do{
                if(!AddedFolders.contains(ImageProvider.getString(ImageProvider.getColumnIndex(MediaStore.Images.Media.BUCKET_ID)))){

                    if(countItems > 0) {
                        ImagesFolders.get(ImagesFolders.size()-1)[2]=String.valueOf(countItems);
                    }
                    ImagesFolders.add( new String[]{
                            ImageProvider.getString(ImageProvider.getColumnIndex(MediaStore.Images.Media.DATA)),
                            ImageProvider.getString(ImageProvider.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))  ,
                            "0"
                    });
                    AddedFolders.add(ImageProvider.getString(ImageProvider.getColumnIndex(MediaStore.Images.Media.BUCKET_ID)));
                    countItems = 1;
                }else{
                    countItems=countItems+1;
                }

            }while(ImageProvider.moveToNext());
        }
        ImagesFolders.get(ImagesFolders.size()-1)[2]=String.valueOf(countItems);
        return  ImagesFolders;
    }

    public ArrayList<String[]> ImageInFolderProvider(String FolderParent) {
       ArrayList<String[]> imagesInfolder= new ArrayList<>();

        Cursor imageprovider = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI ,
                new String[]{
                        MediaStore.Images.Media._ID ,
                        MediaStore.Images.Media.BUCKET_ID ,
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME ,
                        MediaStore.Images.Media.DATA
                } ,
                MediaStore.Images.Media.DATA+" LIKE ?" ,
                new String[]{"%"+FolderParent+"%"},
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
        );

        if(imageprovider.moveToFirst()){
            do{
                imagesInfolder.add(new String[]{
                        imageprovider.getString(imageprovider.getColumnIndex(MediaStore.Images.Media.DATA)) ,
                        imageprovider.getString(imageprovider.getColumnIndex(MediaStore.Images.Media._ID)) ,
                        imageprovider.getString(imageprovider.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)),
                        "0"
                });
            }while(imageprovider.moveToNext());
        }

       return  imagesInfolder;
    }

    public ArrayList<String[]> VideoinFolderProvider(String FolderParent){
        ArrayList<String[]> videoInFolder = new ArrayList<>();

        Cursor videoProvider = context.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI ,
                new String[]{
                        MediaStore.Video.Media._ID ,
                        MediaStore.Video.Media.BUCKET_ID ,
                        MediaStore.Video.Media.BUCKET_DISPLAY_NAME ,
                        MediaStore.Video.Media.DATA
                } ,
                MediaStore.Video.Media.DATA+ " LIKE ? " ,
                new String[]{"%"+FolderParent+"%" },
                MediaStore.Files.FileColumns.DATE_ADDED+" DESC"
        );

        if(videoProvider.moveToFirst()){
            do{
                videoInFolder.add(new String[]{
                        videoProvider.getString(videoProvider.getColumnIndex( MediaStore.Video.Media.DATA)),
                        videoProvider.getString(videoProvider.getColumnIndex( MediaStore.Video.Media._ID)),
                        videoProvider.getString(videoProvider.getColumnIndex( MediaStore.Video.Media.BUCKET_DISPLAY_NAME)),
                        "0"
                });
            }while (videoProvider.moveToNext());
        }

        return videoInFolder;
    }

    public ArrayList<String[]> RingToneProvider(){

        ArrayList<String[]> Ringtones = new ArrayList<>();
        RingtoneManager manager = new RingtoneManager(this.context);
        manager.setType(RingtoneManager.TYPE_ALL);
        Cursor RingToneCursor = manager.getCursor();

        if(RingToneCursor.moveToFirst()){

            do{
                Ringtones.add(new String[]{ RingToneCursor.getString(RingtoneManager.TITLE_COLUMN_INDEX) ,
                        String.valueOf(manager.getRingtoneUri(RingToneCursor.getPosition()))});
            }while(RingToneCursor.moveToNext());
        }

        return Ringtones;
    }
}
