package com.curso.onmessage.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso.onmessage.R;
import com.curso.onmessage.SettingActivity.SettingChangeNumber;
import com.curso.onmessage.SettingActivity.SettingProfile;

import java.util.ArrayList;

/**
 * Created by PC-PRAF on 7/1/2017.
 */

public class RecyclerSettingsAdapter extends RecyclerView.Adapter<RecyclerSettingsAdapter.SettingHolder> {

    private static ArrayList<String[]> SettingsNameArray;
    private Context context;
    private Activity activity;

    public RecyclerSettingsAdapter(Context cntx , Activity act){
        context = cntx;
        activity = act;
        SettingsNameArray = new ArrayList<>();
        SettingsNameArray.add(new String[]{"1",cntx.getString(R.string.Account_Setting),"1"});
        SettingsNameArray.add(new String[]{"2",context.getString(R.string.perfil),"0"});
        SettingsNameArray.add(new String[]{"3",context.getString(R.string.change_number),"0"});
        //SettingsNameArray.add(new String[]{"4",context.getString(R.string.privacity),"0"});
        SettingsNameArray.add(new String[]{"4",context.getString(R.string.delete_account),"0"});
        SettingsNameArray.add(new String[]{"5",cntx.getString(R.string.notify_setting),"1"});
        SettingsNameArray.add(new String[]{"6",context.getString(R.string.preview),"0"});
        SettingsNameArray.add(new String[]{"7",context.getString(R.string.sound),"0"});
        SettingsNameArray.add(new String[]{"8",context.getString(R.string.vibrations),"0"});
        SettingsNameArray.add(new String[]{"9",cntx.getString(R.string.privacity_setting),"1"});
        SettingsNameArray.add(new String[]{"10",context.getString(R.string.bloq_users),"0"});
        SettingsNameArray.add(new String[]{"12",context.getString(R.string.visible_map),"0"});
        SettingsNameArray.add(new String[]{"12",context.getString(R.string.perfil_photo),"0"});
        SettingsNameArray.add(new String[]{"11",context.getString(R.string.group),"0"});
        SettingsNameArray.add(new String[]{"12",context.getString(R.string.states),"0"});
        SettingsNameArray.add(new String[]{"13",cntx.getString(R.string.questions_setting),"1"});
        SettingsNameArray.add(new String[]{"14",context.getString(R.string.frequent_questions),"0"});
        SettingsNameArray.add(new String[]{"15",context.getString(R.string.question),"0"});
        SettingsNameArray.add(new String[]{"16",context.getString(R.string.term_priv),"0"});
        SettingsNameArray.add(new String[]{"16",context.getString(R.string.open_source_projects),"0"});
    }

    @Override
    public RecyclerSettingsAdapter.SettingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_setting,parent,false);
        SettingHolder holder = new SettingHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerSettingsAdapter.SettingHolder holder, final int position) {
        holder.AppVersionLayout.setVisibility(View.GONE);

        if(SettingsNameArray.get(position)[2]=="1"){
            holder.LayoutHeader.setVisibility(View.VISIBLE);
            holder.headerText.setText(SettingsNameArray.get(position)[1]);
            holder.layout_setting_name.setVisibility(View.GONE);
            holder.Item_separator.setVisibility(View.GONE);
        }else{
            holder.LayoutHeader.setVisibility(View.GONE);
            holder.layout_setting_name.setVisibility(View.VISIBLE);
            holder.SettingName.setText(SettingsNameArray.get(position)[1]);
            holder.Item_separator.setVisibility(View.VISIBLE);
        }
        if(SettingsNameArray.size()-1 == position){
            holder.AppVersionLayout.setVisibility(View.VISIBLE);
        }

        holder.layout_setting_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (SettingsNameArray.get(position)[0]){
                    case "2":
                        Intent intent = new Intent(v.getContext() , SettingProfile.class);
                        v.getContext().startActivity(intent);
                        break;
                    case "3":
                        Intent intentChangeNumber = new Intent(v.getContext(), SettingChangeNumber.class);
                        v.getContext().startActivity(intentChangeNumber);
                        break;
                    case "7":

                        Display display = activity.getWindowManager().getDefaultDisplay();
                        int mwidth = display.getWidth();
                        int mheight = display.getHeight();

                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        View view = LayoutInflater.from(v.getContext()).inflate(R.layout.popup_sounds ,null);

                        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycle_sounds);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
                        RecycleRingToneAdapter adapter = new RecycleRingToneAdapter(v.getContext());

                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);
                        builder.setView(view);
                        AlertDialog dialog = builder.create();
                        dialog.show();

                        WindowManager.LayoutParams LP = new WindowManager.LayoutParams();
                        LP.copyFrom(dialog.getWindow().getAttributes());
                        LP.width = (int) (( mwidth / 2 ) * 1.4);
                        LP.height = (int)((mheight /2 ) * 1.8 );

                        dialog.getWindow().setAttributes(LP);

                    //    dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND | WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return SettingsNameArray.size();
    }

    public class SettingHolder extends RecyclerView.ViewHolder {

        public TextView SettingName;
        public LinearLayout LayoutHeader;
        public LinearLayout layout_setting_name;
        public TextView headerText;
        public ImageView Item_separator;
        public LinearLayout AppVersionLayout;

        public SettingHolder(View itemView) {
            super(itemView);
            SettingName = (TextView) itemView.findViewById(R.id.SettingName);
            LayoutHeader = (LinearLayout) itemView.findViewById(R.id.header_setting);
            headerText = (TextView) itemView.findViewById(R.id.header_text_setting);
            layout_setting_name = (LinearLayout) itemView.findViewById(R.id.layout_setting_name);
            Item_separator = (ImageView) itemView.findViewById(R.id.Item_separator);
            AppVersionLayout = (LinearLayout) itemView.findViewById(R.id.layout_app_version);
        }
    }
}
