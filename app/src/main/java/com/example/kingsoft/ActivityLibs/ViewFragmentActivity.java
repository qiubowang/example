package com.example.kingsoft.ActivityLibs;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.kingsoft.CustomAdapter.R;
import com.example.kingsoft.InterfaceLibs.IFragmentCallback;
import com.example.kingsoft.ViewPagerLibs.Fragment2;
import com.example.kingsoft.ViewPagerLibs.Fragment3;

public class ViewFragmentActivity extends FragmentActivity implements IFragmentCallback{
    private FragmentPagerAdapter pagerAdapter = null;
    private ViewPager mViewPager = null;
    private Button mFramgentOneButton = null;
    private Button mFramgentTwoButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_layout);

//        Debug.waitForDebugger();
        printChannelInf();

//        mFramgentOneButton = (Button)findViewById(R.id.frament1_button);
//        mFramgentTwoButton = (Button)findViewById(R.id.frament2_button);
//
//        mFramgentOneButton.setOnClickListener((v) -> {
//            Fragment fragment = new Fragment2();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.frament2, fragment);
//            transaction.commit();
//
//        });
//
//        mFramgentTwoButton.setOnClickListener((v) ->{
//            Fragment fragment = new Fragment3();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.frament2, fragment);
//            //将之前的R.id.frament2增加到回退栈则按返回键的时候则会回到这个被代替的fragement状态，否则不会
//            transaction.addToBackStack(null);
//            transaction.commit();
//        });
//        pagerAdapter = new ViewFragmentPagerAdapter(this.getSupportFragmentManager(), this);
//        mViewPager = (ViewPager)findViewById(R.id.my_view_pager);
//        mViewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void interactiveFragment(int type) {
        if (type == RESET_FRAGMENT_2_TEXT_VIEW){


        }else if (type == RESET_FRAGMENT_3_TEXT_VIEW){
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frament2);
            ((TextView)findViewById(R.id.frament3_text_view1)).setText("Fragment交互来之页面1");
        }
    }

    public void  printChannelInf(){
        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            String key = applicationInfo.metaData.getString("UMENG_CHANNEL");
            Log.d("channelInfo: ", key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
}
