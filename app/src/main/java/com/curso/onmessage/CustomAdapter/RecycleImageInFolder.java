package com.curso.onmessage.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ToggleButton;
import com.bumptech.glide.Glide;
import com.curso.onmessage.GroupActivity.GroupNameActivity;
import com.curso.onmessage.GroupActivity.SearchImageGroupProfile;
import com.curso.onmessage.Models.Providers.Multimedia_Provider;
import com.curso.onmessage.R;
import com.curso.onmessage.PickActivity.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by PC-PRAF on 10/12/2016.
 **/

public class RecycleImageInFolder extends
        RecyclerView.Adapter<RecycleImageInFolder.HolderImageInFolder>
{
    /// this array contains the images in the folder previosly selected by the user
    /// the information of the images are the path , id , bucket_name , bucket_id , and custom value ,
    /// that determine if the file has been selected to send.
    private static ArrayList<String[]> ImagesinFolder;
    private Context context;
    private int behavior;
    private String NameFolder;

    public RecycleImageInFolder(Context cntx , String ParentFolder , String folderName , int bhvior) {
        behavior = bhvior;
        ImagesinFolder = new ArrayList<>();
        context = cntx;
        NameFolder = folderName;
        Multimedia_Provider multimedia_provider = new Multimedia_Provider(cntx);
        ImagesinFolder = multimedia_provider.ImageInFolderProvider(ParentFolder);
    }

    @Override
    public HolderImageInFolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_in_folder , parent , false);
        HolderImageInFolder holderImageInFolder = new HolderImageInFolder(view);
        return holderImageInFolder;
    }

    @Override
    public void onBindViewHolder(final HolderImageInFolder holder, final int position) {
        /// check if the value in the position has been cheked by the user.
        // therefore we show the drawable to tell the user "file has been assigned"
        if(ImagesinFolder.get(position)[3]=="1"){
            holder.toggleButton.setChecked(true);
        }else{
            holder.toggleButton.setChecked(false);
        }

        Glide.with(this.context).
                load(Uri.fromFile(new File(ImagesinFolder.get(position)[0]))).
                into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(behavior == 1)
                {
                    if (holder.toggleButton.isChecked()) {
                        holder.toggleButton.setChecked(false);
                        ImagesinFolder.get(position)[3] = "0";
                        for (Iterator<String[]> iterator = imageview_in_folder_main.ImagesAdded.iterator(); iterator.hasNext(); ) {
                            if (iterator.next()[1] == String.valueOf(position)) {
                                iterator.remove();
                            }
                        }

                        for (Iterator<String[]> iterator = imageview_in_folder_main.ImageToSend.iterator(); iterator.hasNext(); ) {
                            if (iterator.next()[1] == String.valueOf(position)) {
                                iterator.remove();
                            }
                        }
                    } else {
                        holder.toggleButton.setChecked(true);
                        ImagesinFolder.get(position)[3] = "1";
                        imageview_in_folder_main.ImagesAdded.add(new String[]{ImagesinFolder.get(position)[1], String.valueOf(position)});
                        imageview_in_folder_main.ImageToSend.add(new String[]{ImagesinFolder.get(position)[0], String.valueOf(position), ImagesinFolder.get(position)[1], ""});
                    }
                    if (imageview_in_folder_main.ImageToSend.size() > 0) {
                        imageview_in_folder_main.CountSelectedItem.setText((imageview_in_folder_main.ImageToSend.size() == 1) ? String.valueOf(imageview_in_folder_main.ImageToSend.size()) + " " + context.getString(R.string.single_select_item) : String.valueOf(imageview_in_folder_main.ImageToSend.size()) +
                                context.getString(R.string.count_select_pick));
                        imageview_in_folder_main.SendSelectedItem.setVisibility(View.VISIBLE);
                    } else {
                        imageview_in_folder_main.CountSelectedItem.setText(NameFolder);
                        imageview_in_folder_main.SendSelectedItem.setVisibility(View.GONE);
                    }
                }else{
                    SearchImageGroupProfile.ImageSelectedToProfile = ImagesinFolder.get(position)[0];
                    Intent intent = new Intent(v.getContext() , GroupNameActivity.class);
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ImagesinFolder.size();
    }

    public class HolderImageInFolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public ToggleButton toggleButton;

        public HolderImageInFolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.image_in_folder);
            this.toggleButton = (ToggleButton) itemView.findViewById(R.id.item_image_selected);
        }
    }
}
