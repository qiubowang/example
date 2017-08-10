package com.example.kingsoft.CustomAdapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingsoft on 2017/6/27.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private Context mContext = null;
    private LayoutInflater mInflater = null;
    private List<View> mViewList = null;
    View view1, view2, view3;

    public ViewPagerAdapter(Context context){
        mContext = context;
        mInflater= LayoutInflater.from(context);
        init();

    }

    public void init(){
        mViewList = new ArrayList<>();
        view1 = mInflater.inflate(R.layout.view_pager_layout_one, null);
        view2 = mInflater.inflate(R.layout.view_pager_layout_two, null);
        view3 = mInflater.inflate(R.layout.view_pager_layout_three, null);
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
    }
    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mViewList.size();
    }

    /**
     * @return 返回的是key，每个页面对应一个key，这个key会在isViewFromObject 用到
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return position;
    }

    /**
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
//        super.destroyItem(container, position, object);
    }

    /**
     *  Object object 是页面对应的key
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        int posion = ((Integer)object).intValue();
        return view == mViewList.get(posion);
    }

    /**
     */
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0)
            return "标题1";
        else if (position == 1){
            return "标题2";
        }else if (position == 2){
            return "标题3";
        }
        return "";
    }
}
