package com.curso.onmessage.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso.onmessage.R;
import com.curso.onmessage.SettingActivity.ProfileNameActivity;
import com.curso.onmessage.SettingActivity.SettingChangeNumber;
import com.curso.onmessage.SettingActivity.SettingState;

import java.util.ArrayList;

/**
 * Created by PC-PRAF on 5/2/2017.
 */

public class RecycleProfileOption extends RecyclerView.Adapter<RecycleProfileOption.HolderProfile> {

    private static ArrayList<String[]> ProfileOptions;
    private Context context;

    public RecycleProfileOption(Context cntx){
        context = cntx;
        ProfileOptions = new ArrayList<>();
        ProfileOptions.add(new String[]{"1","Denilson Barahona" ,"0"});
        ProfileOptions.add(new String[]{"2","Escribiendo .... " ,"0"});
        ProfileOptions.add(new String[]{"3",cntx.getString(R.string.private_information) ,"1"});
        ProfileOptions.add(new String[]{"4","+504 96981692" ,"0"});
        ProfileOptions.add(new String[]{"5","denilsonbarahona@gmail.com" ,"0"});
    }

    @Override
    public HolderProfile onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile,parent, false);
        HolderProfile holderProfile = new HolderProfile(view);
        return holderProfile;
    }

    @Override
    public void onBindViewHolder(HolderProfile holder, final int position) {

        if(ProfileOptions.get(position)[2]=="1"){
            holder.LayoutHeader.setVisibility(View.VISIBLE);
            holder.headerText.setText(ProfileOptions.get(position)[1]);
            holder.layout_setting_name.setVisibility(View.GONE);
            holder.Item_separator.setVisibility(View.GONE);
        }else{
            holder.LayoutHeader.setVisibility(View.GONE);
            holder.layout_setting_name.setVisibility(View.VISIBLE);
            holder.SettingName.setText(ProfileOptions.get(position)[1]);
            holder.Item_separator.setVisibility(View.VISIBLE);
        }

        holder.edit_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (ProfileOptions.get(position)[0]){
                    case "1" :
                            Intent intent = new Intent(v.getContext(), ProfileNameActivity.class);
                            v.getContext().startActivity(intent);
                            break;
                    case "2" :
                        Intent intentChangeState = new Intent(v.getContext(), SettingState.class);
                        v.getContext().startActivity(intentChangeState);
                        break;
                    case "4" :
                        Intent intentChangeNumber = new Intent(v.getContext(), SettingChangeNumber.class);
                        v.getContext().startActivity(intentChangeNumber);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ProfileOptions.size();
    }

    public class HolderProfile extends RecyclerView.ViewHolder {

        public TextView SettingName;
        public LinearLayout LayoutHeader;
        public LinearLayout layout_setting_name;
        public TextView headerText;
        public ImageView Item_separator;
        public LinearLayout edit_mode;

        public HolderProfile(View itemView) {
            super(itemView);

            SettingName = (TextView) itemView.findViewById(R.id.SettingText);
            LayoutHeader = (LinearLayout) itemView.findViewById(R.id.header_setting);
            headerText = (TextView) itemView.findViewById(R.id.header_text_setting);
            layout_setting_name = (LinearLayout) itemView.findViewById(R.id.layout_setting_name);
            Item_separator = (ImageView) itemView.findViewById(R.id.Item_separator);
            edit_mode = (LinearLayout) itemView.findViewById(R.id.edit_mode);
        }
    }
}
