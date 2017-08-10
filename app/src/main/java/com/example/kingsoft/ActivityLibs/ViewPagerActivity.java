package com.example.kingsoft.ActivityLibs;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.kingsoft.CustomAdapter.R;
import com.example.kingsoft.CustomAdapter.ViewPagerAdapter;


public class ViewPagerActivity extends FragmentActivity {
    private ViewPager mViewPager = null;
    private PagerAdapter mPagerAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        mViewPager = (ViewPager)findViewById(R.id.my_view_pager);
        mPagerAdapter = new ViewPagerAdapter(this);
        mViewPager.setAdapter(mPagerAdapter);
    }
}
