package com.curso.onmessage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.curso.onmessage.ContactsPhone.ContactsPhone;
import com.curso.onmessage.CustomAdapter.RecycleContactinGroup;
import com.curso.onmessage.CustomAdapter.RecycleContactsAdapter;
import com.curso.onmessage.CustomAdapter.ViewPagerAdapter;
import com.curso.onmessage.GroupActivity.GroupActivity;
import com.curso.onmessage.GroupActivity.GroupNameActivity;
import com.curso.onmessage.ImageDecode.ImageFolderCaching;
import com.curso.onmessage.MapActivity.MapsActivity;
import com.curso.onmessage.SettingActivity.SettingActity;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 5 ;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 5 ;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout tab;
    private int[] TabIcon={ R.drawable.ic_message , R.drawable.ic_person , R.drawable.ic_map };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activicty_main_content);
        PermissionDebugMode();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        SetViewPager(mViewPager);

        tab = (TabLayout) findViewById(R.id.Tabs);
        tab.setupWithViewPager(mViewPager);
        setTabIcon();



     //   ImageFolderCaching imageFolderCaching = new ImageFolderCaching(this.getApplicationContext());
     //   imageFolderCaching.execute(Base64);

    }

    @Override
    protected void onResume() {
        super.onResume();
        RecycleContactinGroup.ContactsSelected.clear();
    }

    public void SetViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new MapsActivity(),null);
        viewPagerAdapter.AddFragment(new ContactsPhone(),null);
        viewPagerAdapter.AddFragment(new ContactsPhone(), null);
        viewPager.setAdapter(viewPagerAdapter);
    }


    private void setTabIcon()
    {
        tab.getTabAt(0).setIcon(TabIcon[2]);
        tab.getTabAt(1).setIcon(TabIcon[0]);
        tab.getTabAt(2).setIcon(TabIcon[1]);
    }

    public void PermissionDebugMode()
    {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        }


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        }


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;
        switch (id){
            case R.id.action_settings:
                            intent = new Intent(this.getApplicationContext(), SettingActity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            this.getApplicationContext().startActivity(intent);
                break;
            case R.id.action_new_group:
                            intent = new Intent(this.getApplicationContext(), GroupActivity.class);
                            intent.putExtra("behavior","1");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            this.getApplicationContext().startActivity(intent);
                break;
            case R.id.action_new_broadcast:
                           intent = new Intent(this.getApplicationContext(), GroupActivity.class);
                           intent.putExtra("behavior","0");
                           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           this.getApplicationContext().startActivity(intent);
                break;
        }

/*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this.getApplicationContext(), SettingActity.class);
            this.getApplicationContext().startActivity(intent);
        }
*/
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return new Fragment();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

    }
}
