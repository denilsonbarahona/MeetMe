package com.curso.onmessage.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.curso.onmessage.ImageFullscreen.VideoFullSceenSend;
import com.curso.onmessage.Models.Providers.Multimedia_Provider;
import com.curso.onmessage.R;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by PC-PRAF on 24/12/2016.
 */

public class RecycleVideoInFolder extends RecyclerView.Adapter<RecycleVideoInFolder.VideoFolderHolder> {

    private ArrayList<String[]> VideoInFolder = new ArrayList<>();
    private Context context;
    private String NameFolder;

   public  RecycleVideoInFolder(Context contxt,  String ParentFolder , String FolderName){
        this.context = contxt;
        Multimedia_Provider multimedia_provider = new Multimedia_Provider(this.context);
        this.NameFolder = FolderName;
        VideoInFolder = multimedia_provider.VideoinFolderProvider(ParentFolder);
    }

    @Override
    public VideoFolderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_in_folder , parent , false);
        VideoFolderHolder holder = new VideoFolderHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(VideoFolderHolder holder, final int position) {
        Glide.with(this.context)
                .load(Uri.fromFile(new File(VideoInFolder.get(position)[0])))
                .into(holder.VideoImage);

        holder.VideoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , VideoFullSceenSend.class);
                intent.putExtra("path_file" , VideoInFolder.get(position)[0]);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return VideoInFolder.size();
    }

    public class VideoFolderHolder extends RecyclerView.ViewHolder {
        ImageView VideoImage;
        public VideoFolderHolder(View itemView) {
            super(itemView);
            this.VideoImage = (ImageView) itemView.findViewById(R.id.video_in_folder);
        }
    }
}
