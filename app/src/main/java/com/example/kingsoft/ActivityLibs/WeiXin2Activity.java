package com.example.kingsoft.ActivityLibs;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kingsoft.CustomAdapter.R;
import com.example.kingsoft.CustomAdapter.ViewFragmentPagerAdapter;

public class WeiXin2Activity extends FragmentActivity {
    private int curPage = 0;
    private float baseLineLength = 0;// 1/3屏幕宽度
    private ViewPager mViewPager = null;
    FragmentPagerAdapter pagerAdapter = null;
    TextView chatTextView = null;
    TextView findTextView = null;
    TextView catTextView = null;
    ImageView baseLine = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initData();
    }

    public void initData(){
//        Debug.waitForDebugger();
        baseLine = (ImageView)findViewById(R.id.baseLine);
        chatTextView = (TextView)findViewById(R.id.chat_view);
        findTextView = (TextView)findViewById(R.id.find_view);
        catTextView = (TextView)findViewById(R.id.cat_view);

        mViewPager = (ViewPager)findViewById(R.id.weixin_main_layout);
        pagerAdapter = new ViewFragmentPagerAdapter(this.getSupportFragmentManager());

        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) ->{

        });

        //获取屏幕的尺寸
        Display display = this.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        baseLineLength = metrics.widthPixels /3;

        ViewGroup.LayoutParams params = baseLine.getLayoutParams();
        params.width = (int) baseLineLength;
        baseLine.setLayoutParams(params);
        baseLine.requestLayout();

        ViewGroup.LayoutParams params2 = chatTextView.getLayoutParams();
        params2.width = (int) baseLineLength;
        chatTextView.setLayoutParams(params2);
        chatTextView.requestLayout();

        ViewGroup.LayoutParams params3= findTextView.getLayoutParams();
        params3.width = (int) baseLineLength;
        findTextView.setLayoutParams(params3);
        findTextView.requestLayout();

        ViewGroup.LayoutParams params4= catTextView.getLayoutParams();
        params4.width = (int) baseLineLength;
        catTextView.setLayoutParams(params4);
        catTextView.requestLayout();

       this.getWindow().getDecorView().getViewTreeObserver().addOnPreDrawListener(() ->{
//           //获取屏幕的尺寸
//           Display display = this.getWindowManager().getDefaultDisplay();
//           DisplayMetrics metrics = new DisplayMetrics();
//           display.getMetrics(metrics);
//           baseLineLength = metrics.widthPixels /3;
//
//           ViewGroup.LayoutParams params = baseLine.getLayoutParams();
//           params.width = (int) baseLineLength;
//           baseLine.setLayoutParams(params);
//
//           ViewGroup.LayoutParams params2 = chatTextView.getLayoutParams();
//           params2.width = (int) baseLineLength;
//           baseLine.setLayoutParams(params2);
//
//           ViewGroup.LayoutParams params3= findTextView.getLayoutParams();
//           params3.width = (int) baseLineLength;
//           baseLine.setLayoutParams(params3);
//
//           ViewGroup.LayoutParams params4= catTextView.getLayoutParams();
//           params4.width = (int) baseLineLength;
//           baseLine.setLayoutParams(params4);

           return false;
       });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) baseLine.getLayoutParams();
                if (curPage == 0 && position == 1){
                    params.leftMargin = (int) (curPage * baseLineLength + positionOffset * baseLineLength);

                }else if (curPage == 1 && position == 2){
                    params.leftMargin = (int) (curPage * baseLineLength + positionOffset * baseLineLength);
                }else if (curPage == 1 && position == 0){
                    params.leftMargin = (int) (curPage * baseLineLength + (1-positionOffset) * baseLineLength);
                }else if (curPage == 2 && position == 1){
                    params.leftMargin = (int) (curPage * baseLineLength + (1-positionOffset) * baseLineLength);
                }
            }

            @Override
            public void onPageSelected(int position) {
                curPage = position;
                if (position == 0){
                    chatTextView.setTextColor(0xff0000);
                }else if (position == 1){
                    findTextView.setTextColor(0xff0000);
                }else if (position == 2){
                    catTextView.setTextColor(0xff0000);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


}
