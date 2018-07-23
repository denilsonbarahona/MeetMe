package com.curso.onmessage.PickActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.curso.onmessage.CustomAdapter.ViewPagerAdapter;
import com.curso.onmessage.R;

/**
 * Created by PC-PRAF on 3/12/2016.
 */

public class MultimediaPickActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapterPick viewPagerAdapterPick;
    private TabLayout tabs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multimedia_pick);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_pick);
        SetToolBar(toolbar);
        viewPager = (ViewPager)findViewById(R.id.container_pick);
        viewPager.setAdapter(viewPagerAdapterPick);

        ViewPagerAdapter(viewPager);
        tabs = (TabLayout)findViewById(R.id.Tabs_pick);
        tabs.setupWithViewPager(viewPager);

    }


    private void SetToolBar(Toolbar toolbar) {
        ImageView imageBack = (ImageView)findViewById(R.id.arrow_back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);
    }

    public void ViewPagerAdapter(ViewPager viewPager){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new Pick_Image_Fragment(),getString(R.string.Image_Title));
        viewPagerAdapter.AddFragment(new Pick_Video_Fragment(),getString(R.string.Videos_Title));

        viewPager.setAdapter(viewPagerAdapter);
    }


    public class ViewPagerAdapterPick extends FragmentPagerAdapter {

        public ViewPagerAdapterPick(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new Fragment();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
