package com.curso.onmessage.ImageDecode;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by PC-PRAF on 16/10/2016.
 */

public  class ImageFolderCaching extends AsyncTask<String ,Void ,Void > {

    Context context;

    public ImageFolderCaching(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params) {
     // CreateFolderImageCaching();
      CachingImageToFile(params[0]);
      return  null;
    }



    public void CachingImageToFile(String Image64string ) {

        CreateFolderImageCaching();
        FileOutputStream fileOutputStream;
        if(Image64string!= null){
            try {
                //fileOutputStream = context.openFileOutput(Environment.getExternalStorageDirectory()+
                  //                                          "/Message/Image/IMG0001_0001.png",context.MODE_PRIVATE);
                byte[] decodedString = Base64.decode(Image64string , Base64.DEFAULT);
                File filePath = new File(Environment.getExternalStorageDirectory()+"/Message/Image/IMG0001_0001.jpg");
                fileOutputStream = new FileOutputStream(filePath,true);
                fileOutputStream.write(decodedString);
                fileOutputStream.flush();
                fileOutputStream.close();
              /*  fileOutputStream.write(decodedString);
                fileOutputStream.flush();
                fileOutputStream.close();*/
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void CreateFolderImageCaching() {
        String FolderMain="Message";
        String FolderImage="Image";
        File foldeMain  = new File(Environment.getExternalStorageDirectory(), FolderMain);
        File foldeImage  = new File(Environment.getExternalStorageDirectory()+"/"+FolderMain,FolderImage );

        if(!foldeMain.exists())
        {
            foldeMain.mkdir();
        }

        if(!foldeImage.exists()){
            foldeImage.mkdir();
        }
    }



}
