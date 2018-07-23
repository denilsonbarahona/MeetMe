package com.curso.onmessage.CustomAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC-PRAF on 24/9/2016.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> FragmentPageList = new ArrayList<>();
    private final List<String> FragmentPageListName = new ArrayList<>();
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentPageList.get(position);
    }

    @Override
    public int getCount() {
        return FragmentPageList.size();
    }

    public void AddFragment(Fragment fragment , String title) {
        FragmentPageList.add(fragment);
        FragmentPageListName.add(title);
    }

    @Override
    public  CharSequence getPageTitle(int Position)
    {
        return FragmentPageListName.get(Position);
    }

}
