package com.curso.onmessage.CustomAdapter;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.curso.onmessage.PickActivity.FilePickActivity;
import com.curso.onmessage.R;
import org.apache.commons.io.FilenameUtils;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by PC-PRAF on 27/11/2016.
 **/

public class RecycleFileAttached extends RecyclerView.Adapter<RecycleFileAttached.AttachedFileHolder> {

    private ArrayList<String[]> FileList;
    private Context context;
    private static ArrayList<String[]>  FilesAttachedToSend;

    public RecycleFileAttached(Context cntx) {
        this.context = cntx;
        FileList = ArrayFileList();
        FilesAttachedToSend = new ArrayList<>();

        if(FileList.size()==0) {
            FilePickActivity.TextViewAddFiles.setText(R.string.tale_empty);
        }else{
            FilePickActivity.TextViewAddFiles.setVisibility(View.GONE);
        }
    }


    @Override
    public AttachedFileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file_pick,parent,false);
        AttachedFileHolder viewHolder = new AttachedFileHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AttachedFileHolder holder, final int position) {

        if(FileList.get(position)[4]=="1") {
            holder.btn_toggle.setImageDrawable(context.getResources().getDrawable(R.drawable.toggle_on));
        }else{
            holder.btn_toggle.setImageDrawable(null);
        }

         holder.FileName.setText(FileList.get(position)[1]+"."+FileList.get(position)[3]);
         holder.FileSize.setText(String.valueOf((Long.parseLong( FileList.get(position)[2] )/1024))+" KB");
         holder.File.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (FileList.get(position)[4] == "1") {
                     FileList.get(position)[4] = "0";
                     holder.btn_toggle.setImageDrawable(null);
                     for(Iterator<String[]> iterator = FilesAttachedToSend.iterator();iterator.hasNext();) {
                           if(iterator.next()[1]==String.valueOf(position)) {
                               iterator.remove();
                           }
                     }
                 }else {
                     FileList.get(position)[4] = "1";
                     holder.btn_toggle.setImageDrawable(context.getResources().getDrawable(R.drawable.toggle_on));
                     FilesAttachedToSend.add(new String[]{
                                                         FileList.get(position)[0]    ,
                                                         String.valueOf(position)
                     });
                 }

                 if(FilesAttachedToSend.size()> 0) {
                     FilePickActivity.CountFileAttached.setText((FilesAttachedToSend.size()==1)?String.valueOf(FilesAttachedToSend.size())+" "+context.getString(R.string.single_select_item):String.valueOf(FilesAttachedToSend.size()) +
                             context.getString(R.string.count_select_pick));
                     FilePickActivity.SendFilesView.setVisibility(View.VISIBLE);
                 }else{
                     FilePickActivity.CountFileAttached.setText(context.getString(R.string.file_pick));
                    FilePickActivity.SendFilesView.setVisibility(View.GONE);
                 }

             }
         });
    }

    @Override
    public int getItemCount() {
        return FileList.size();
    }

    public class AttachedFileHolder extends RecyclerView.ViewHolder {

       TextView FileName;
       TextView FileSize;
       LinearLayout File;
       LinearLayout Toggle;
       ImageView btn_toggle;

        public AttachedFileHolder(View itemView) {
            super(itemView);

            this.File = (LinearLayout)itemView.findViewById(R.id.file_description);
            this.FileName = (TextView)itemView.findViewById(R.id.file_name);
            this.FileSize = (TextView)itemView.findViewById(R.id.file_size);
            this.btn_toggle = (ImageView) itemView.findViewById(R.id.btn_toggle);
            this.Toggle =(LinearLayout)itemView.findViewById(R.id.Toggle);
        }
    }

    private ArrayList<String[]> ArrayFileList() {

        ArrayList<String[]> FilesList = new ArrayList<>();

        Cursor MediaFileProvider = context.getContentResolver().query(
                MediaStore.Files.getContentUri("external"),
                new String[]{MediaStore.Files.FileColumns.DATA , MediaStore.Files.FileColumns.TITLE ,MediaStore.Files.FileColumns.SIZE },
                null,
                null,
                null
        );


        if(MediaFileProvider.moveToFirst())
        {
            do{
                String ext = FilenameUtils.getExtension(
                                        MediaFileProvider.getString(
                                                                MediaFileProvider.getColumnIndex(
                                                                        MediaStore.Files.FileColumns.DATA)
                                                    )
                                        );
                if(ext.equals("pdf") || ext.equals("txt")|| ext.equals("docx") ||
                        ext.equals("xlsx") ||
                        ext.equals("csv")||
                        ext.equals("ods")||
                        ext.equals("odt")
                        ) {
                         FilesList.add( new String[]
                               {
                                 MediaFileProvider.getString(MediaFileProvider.getColumnIndex(MediaStore.Files.FileColumns.DATA))  ,
                                 MediaFileProvider.getString(MediaFileProvider.getColumnIndex(MediaStore.Files.FileColumns.TITLE)) ,
                                 MediaFileProvider.getString(MediaFileProvider.getColumnIndex(MediaStore.Files.FileColumns.SIZE))  ,
                                 ext ,
                                 "0"
                               }
                    );
                }
            }while (MediaFileProvider.moveToNext());
        }
        return FilesList;
    }

}
