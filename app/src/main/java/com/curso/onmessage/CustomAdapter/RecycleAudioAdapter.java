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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by PC-PRAF on 1/12/2016.
 */

public class RecycleAudioAdapter extends RecyclerView.Adapter<RecycleAudioAdapter.PickAudioAdapter> {

    private Context context;
    private  ArrayList<String[]> FilesAudio = new ArrayList<>();
    public  static ArrayList<String[]> AudioAttachedToSend = new ArrayList<>();

    public RecycleAudioAdapter(Context cnxt) {
        this.context = cnxt;
        FilesAudio = GetAudiosFiles();

        if(FilesAudio.size()==0) {
            FilePickActivity.TextViewAddFiles.setText(R.string.tale_empty);
        }else{
            FilePickActivity.TextViewAddFiles.setVisibility(View.GONE);
        }
    }


    private ArrayList<String[]> GetAudiosFiles() {

        ArrayList<String[]>  ArrayListAudios = new ArrayList<>();

        Cursor MediaStoreAudio = context.getContentResolver().query
                (
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        null ,
                        MediaStore.Audio.Media.IS_NOTIFICATION+" = 0 AND " + MediaStore.Audio.Media.IS_ALARM+" = 0"+
                                " AND "+ MediaStore.Audio.Media.IS_RINGTONE+" = 0",
                        null ,
                        null
                );

        if(MediaStoreAudio.moveToFirst()) {
            do{
                ArrayListAudios.add(new String[]{
                                MediaStoreAudio.getString(MediaStoreAudio.getColumnIndex(MediaStore.Audio.Media.TITLE)) ,
                                MediaStoreAudio.getString(MediaStoreAudio.getColumnIndex(MediaStore.Audio.Media.ALBUM)) ,
                                MediaStoreAudio.getString(MediaStoreAudio.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                                getTimeString(Long.parseLong(MediaStoreAudio.getString(MediaStoreAudio.getColumnIndex(MediaStore.Audio.Media.DURATION)))) ,
                                MediaStoreAudio.getString(MediaStoreAudio.getColumnIndex(MediaStore.Audio.Media.DATA))  ,
                                "0"

                });
            }while (MediaStoreAudio.moveToNext());
        }

        return ArrayListAudios;
    }


    private String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        int hours = (int) (millis / (1000 * 60 * 60));
        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf.append(String.format("%02d", hours))
                .append(":")
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }


    @Override
    public PickAudioAdapter onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pick_audio,parent,false);
        PickAudioAdapter pickAudioAdapter = new PickAudioAdapter(view);
        return pickAudioAdapter;
    }

    @Override
    public void onBindViewHolder(final PickAudioAdapter holder, final int position) {
        if(FilesAudio.get(position)[5]=="1"){
            holder.btn_toggle.setImageDrawable(context.getResources().getDrawable(R.drawable.toggle_on));
        }else{
            holder.btn_toggle.setImageDrawable(null);
        }
        holder.AudioName.setText(FilesAudio.get(position)[0]);
        holder.AudioAlbum.setText(FilesAudio.get(position)[1]);
        holder.AudioArtis.setText(FilesAudio.get(position)[2]);
        holder.AudioDuration.setText(FilesAudio.get(position)[3]);
        holder.AudioFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FilesAudio.get(position)[5]=="1"){
                    FilesAudio.get(position)[5]="0";
                    holder.btn_toggle.setImageDrawable(null);
                    for(Iterator<String[]> iterator = AudioAttachedToSend.iterator(); iterator.hasNext();) {
                        if(iterator.next()[1]==String.valueOf(position)) {
                            iterator.remove();
                        }
                    }

                }else{
                    holder.btn_toggle.setImageDrawable(context.getResources().getDrawable(R.drawable.toggle_on));
                    FilesAudio.get(position)[5]="1";
                    AudioAttachedToSend.add(new String[]{
                            FilesAudio.get(position)[4] ,
                            String.valueOf(position)
                    });
                }

                if(AudioAttachedToSend.size()> 0){
                    FilePickActivity.CountFileAttached.setText((AudioAttachedToSend.size()==1)?String.valueOf(AudioAttachedToSend.size())+" "+context.getString(R.string.single_select_item):String.valueOf(AudioAttachedToSend.size()) +
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
        return FilesAudio.size();
    }

    public class PickAudioAdapter extends RecyclerView.ViewHolder {

        TextView AudioName;
        TextView AudioDuration;
        TextView AudioAlbum;
        TextView AudioArtis;
        LinearLayout AudioFile;
        LinearLayout Toggle;
        ImageView btn_toggle;

        public PickAudioAdapter(View itemView) {
            super(itemView);

            this.AudioName = (TextView) itemView.findViewById(R.id.audio_name);
            this.AudioDuration = (TextView) itemView.findViewById(R.id.audio_duration);
            this.AudioAlbum = (TextView) itemView.findViewById(R.id.audio_Album);
            this.AudioArtis = (TextView) itemView.findViewById(R.id.artis_name_audio);
            this.AudioFile = (LinearLayout) itemView.findViewById(R.id.item_desciption);
            this.Toggle = (LinearLayout) itemView.findViewById(R.id.Toggle);
            this.btn_toggle = (ImageView) itemView.findViewById(R.id.btn_toggle);


        }
    }
}
