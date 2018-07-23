package com.curso.onmessage.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.curso.onmessage.Models.Providers.Multimedia_Provider;
import com.curso.onmessage.R;

import java.io.File;
import java.util.ArrayList;
import com.curso.onmessage.PickActivity.video_in_folder_main;
/**
 * Created by PC-PRAF on 20/12/2016.
 */

public class RecycleVideoPickAdapter extends RecyclerView.Adapter<RecycleVideoPickAdapter.HolderAdapter> {

    private static ArrayList<String[]> VideoFolder;
    private Context context;
    private Multimedia_Provider multimedia_provider;

    public RecycleVideoPickAdapter(Context context){
        this.context = context;
        this.multimedia_provider = new Multimedia_Provider(this.context);
        VideoFolder = multimedia_provider.VideoProvider();
    }

    @Override
    public HolderAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_pick , parent, false);
        HolderAdapter holderAdapter = new HolderAdapter(view);
        return holderAdapter;
    }

    @Override
    public void onBindViewHolder(HolderAdapter holder, final int position) {
        holder.FolderName.setText(VideoFolder.get(position)[1]);
        holder.ItemCount.setText(VideoFolder.get(position)[2]);
        Glide.with(this.context)
                .load(Uri.fromFile(new File(VideoFolder.get(position)[0])))
                .into(holder.imageView);
        holder.videoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), video_in_folder_main.class);
                intent.putExtra("parent_file",VideoFolder.get(position)[0]);
                intent.putExtra("folder_name",VideoFolder.get(position)[1]);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return VideoFolder.size();
    }

    public class HolderAdapter extends RecyclerView.ViewHolder {

        LinearLayout videoLayout;
        ImageView imageView;
        TextView FolderName;
        TextView ItemCount;

        public HolderAdapter(View itemView) {
            super(itemView);

            this.videoLayout = (LinearLayout)itemView.findViewById(R.id.layout_folder_image);
            this.imageView = (ImageView) itemView.findViewById(R.id.image_folder);
            this.FolderName = (TextView) itemView.findViewById(R.id.folder_name);
            this.ItemCount = (TextView) itemView.findViewById(R.id.count_image_folder);
        }
    }
}
