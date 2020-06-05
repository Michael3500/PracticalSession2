package com.example.practicals2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.activeandroid.ActiveAndroid;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    GridView myGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //checking and requesting read and write access permissions
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        //setting up the toolbar
        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        //declaring and initialising the tabs on the tablayout
        TabLayout myTabLayout = (TabLayout)findViewById(R.id.tablayout);
        myTabLayout.addTab(myTabLayout.newTab().setText("World"));
        myTabLayout.addTab(myTabLayout.newTab().setText("Business"));
        myTabLayout.addTab(myTabLayout.newTab().setText("Technology"));
        myTabLayout.addTab(myTabLayout.newTab().setText("Science"));
        myTabLayout.addTab(myTabLayout.newTab().setText("Sports"));
        myTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //setting up the adapter to joining the tabs with the rest of the layout
        final ViewPager myViewpager = (ViewPager)findViewById(R.id.viewpager);
        PageAdapter myPageAdapter = new PageAdapter(getSupportFragmentManager(), myTabLayout.getTabCount());
        myViewpager.setAdapter(myPageAdapter);
        myViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(myTabLayout));
        myTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                myViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ActiveAndroid.initialize(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //redirect to activity according to which menu item was selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.addPost:
                Intent intentAddPost = new Intent(getApplicationContext(), PostActivity.class);
                startActivity(intentAddPost);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
