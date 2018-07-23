package com.curso.onmessage.GroupActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso.onmessage.CustomAdapter.RecycleContactGroupAdapter;
import com.curso.onmessage.CustomAdapter.RecycleContactinGroup;
import com.curso.onmessage.MessageActitvity.MessageMain;
import com.curso.onmessage.R;

/**
 * Created by PC-PRAF on 8/1/2017.
 */

public class GroupActivity extends AppCompatActivity
{
    private static RecycleContactinGroup recycleContactinGroup;
    private static RecyclerView  ContactsInGroup;
    private static RecycleContactGroupAdapter Adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        Toolbar toolbar = (Toolbar) findViewById(R.id.group_Toolbar);
        SetToolBar(toolbar);

        Adapter = new RecycleContactGroupAdapter(this.getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycle_contacts);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);

        recycleContactinGroup = new RecycleContactinGroup(this.getApplicationContext());
        LinearLayoutManager LayoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false);
        ContactsInGroup = (RecyclerView) findViewById(R.id.recycler_in_group);

        ContactsInGroup.setLayoutManager(LayoutManager);
        ContactsInGroup.setAdapter(recycleContactinGroup);

    }



    public static void AddNewItem(String uri , String Name , String Position){
        String []Names = Name.split(" ");
        recycleContactinGroup.AddNewItem(uri , Names[0] , Position);
        ContactsInGroup.scrollToPosition(RecycleContactinGroup.ContactsSelected.size()-1);
    }

    public static void RemoveItem(String Position){
        recycleContactinGroup.RemoveItem(Position);
        Adapter.ItemRemovedFromGroup(Position);
    }

    private void SetToolBar(Toolbar toolbar)
    {
        ImageView imageBack = (ImageView)findViewById(R.id.arrow_back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TextView Title = (TextView) findViewById(R.id.activity_title);
        if( getIntent().getStringExtra("behavior").equals("1")) {
            Title.setText(R.string.new_group);
        }else{
            Title.setText(R.string.new_broadcast);
        }

        setSupportActionBar(toolbar);
        LinearLayout NextStep = (LinearLayout) findViewById(R.id.NameStep);
        NextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         if(   getIntent().getStringExtra("behavior").equals("1")) {
             if (RecycleContactinGroup.ContactsSelected.size() > 0) {
                 Intent intent = new Intent(v.getContext(), GroupNameActivity.class);
                 v.getContext().startActivity(intent);
             }
         }else{
             if (RecycleContactinGroup.ContactsSelected.size() > 0) {
                 Intent intent = new Intent(v.getContext(), MessageMain.class);
                 intent.putExtra("behavior","0");
                 intent.putExtra("ContactName",String.valueOf( RecycleContactinGroup.ContactsSelected.size() )+" "+
                                                            getString(R.string.str_members));
                 v.getContext().startActivity(intent);
             }
         }
            }
        });

    }

}
