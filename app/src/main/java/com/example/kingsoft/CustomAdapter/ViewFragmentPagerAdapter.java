package com.example.kingsoft.CustomAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;

import com.example.kingsoft.ViewPagerLibs.Fragment1;
import com.example.kingsoft.ViewPagerLibs.Fragment2;
import com.example.kingsoft.ViewPagerLibs.Fragment3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingsoft on 2017/6/27.
 */

public class ViewFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context mContext = null;
    private LayoutInflater mInflater = null;
    private List<Fragment> mFragments = null;
    public ViewFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        init();
    }

    public ViewFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        init();
    }

    private void  init(){
        Fragment fragment = new Fragment1();
        mFragments = new ArrayList<>();
        mFragments.add(fragment);
        fragment = new Fragment2();
        mFragments.add(fragment);
        fragment = new Fragment3();
        mFragments.add(fragment);
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mFragments.size();
    }
}
