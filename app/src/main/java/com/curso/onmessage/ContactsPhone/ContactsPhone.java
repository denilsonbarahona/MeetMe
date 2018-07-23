package com.curso.onmessage.ContactsPhone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.curso.onmessage.CustomAdapter.RecycleContactsAdapter;
import com.curso.onmessage.R;


/**
 * Created by PC-PRAF on 24/9/2016.
 */

public class ContactsPhone extends Fragment
{
    private LinearLayoutManager linearLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        RecycleContactsAdapter.InitArrayContact(this.getContext());
        View Rootview = inflater.inflate(R.layout.contacts_activity_main , container ,false);
        RecyclerView recyclerView = (RecyclerView) Rootview.findViewById(R.id.recycle_contact);

        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        RecycleContactsAdapter adapter = new RecycleContactsAdapter();
        recyclerView.setAdapter(adapter);
        return Rootview;
    }






}
