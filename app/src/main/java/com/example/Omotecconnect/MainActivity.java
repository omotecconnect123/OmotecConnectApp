package com.example.Omotecconnect;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {



    ///private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //toolbar = (Toolbar) findViewById(R.id.mytoolbar);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.myViewpager);


        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);




    }

    private void setupViewPager(ViewPager viewPager){

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),1);
        viewPagerAdapter.addFragment(new StudentLogin(),"Student");
        viewPagerAdapter.addFragment(new TrainerLogin(),"Trainer");
        viewPager.setAdapter(viewPagerAdapter);


    }



}
