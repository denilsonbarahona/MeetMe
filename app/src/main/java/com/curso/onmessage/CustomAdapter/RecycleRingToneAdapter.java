package com.curso.onmessage.CustomAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.curso.onmessage.Models.Providers.Multimedia_Provider;
import com.curso.onmessage.R;

import java.util.ArrayList;

/**
 * Created by PC-PRAF on 12/2/2017.
 */

public class RecycleRingToneAdapter extends RecyclerView.Adapter<RecycleRingToneAdapter.RingToneHolder>
{
    ArrayList<String[]> arrayList;

    public RecycleRingToneAdapter(Context cntx){

        arrayList = new ArrayList<>();
        Multimedia_Provider multimedia_provider = new Multimedia_Provider(cntx);
        arrayList = multimedia_provider.RingToneProvider();
    }

    @Override
    public RingToneHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sound , parent , false);
        RingToneHolder holder = new RingToneHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RingToneHolder holder, int position) {
        holder.sound.setText(arrayList.get(position)[0]);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RingToneHolder extends RecyclerView.ViewHolder {

        private RadioButton sound;
        public RingToneHolder(View itemView) {
            super(itemView);

            sound = (RadioButton) itemView.findViewById(R.id.sound);
        }
    }
}
