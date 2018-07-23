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

import com.curso.onmessage.PickActivity.*;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by PC-PRAF on 4/12/2016.
 */

public class Recycle_image_pick extends RecyclerView.Adapter<Recycle_image_pick.HolderPickImage> {

    private Context context;
    private Multimedia_Provider multimedia_provider;
    private ArrayList<String[]> FolderImages = new ArrayList<>();
    private int behavior;


    public Recycle_image_pick(Context cntx , int bhvior){
        this.context = cntx;
        this.behavior = bhvior;
        this.multimedia_provider = new Multimedia_Provider(this.context);
        FolderImages = multimedia_provider.ImageProvider();
    }


    @Override
    public HolderPickImage onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_pick,parent,false);
        HolderPickImage holderPickImage = new HolderPickImage(view);
        return holderPickImage;
    }

    @Override
    public void onBindViewHolder(final HolderPickImage holder, final int position) {
        holder.FolderName.setText(FolderImages.get(position)[1]);
        holder.ItemCount.setText(FolderImages.get(position)[2]);
        Glide.with(context)
                .load(Uri.fromFile(new File(FolderImages.get(position)[0])))
                .into(holder.imageView);
        holder.imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),imageview_in_folder_main.class);
                intent.putExtra("parent_file",FolderImages.get(position)[0]);
                intent.putExtra("folder_name",FolderImages.get(position)[1]);
                intent.putExtra("behavior",behavior);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return FolderImages.size();
    }

    public class HolderPickImage extends RecyclerView.ViewHolder {

        LinearLayout imageLayout;
        ImageView imageView;
        TextView FolderName;
        TextView ItemCount;

        public HolderPickImage(View itemView) {
            super(itemView);

            this.imageLayout = (LinearLayout)itemView.findViewById(R.id.layout_folder_image);
            this.imageView = (ImageView) itemView.findViewById(R.id.image_folder);
            this.FolderName = (TextView) itemView.findViewById(R.id.folder_name);
            this.ItemCount = (TextView) itemView.findViewById(R.id.count_image_folder);
        }
    }
}
