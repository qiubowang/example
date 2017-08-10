package com.example.kingsoft.MyRemoteViews;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.kingsoft.CustomAdapter.R;


public class RemoteViewsActivity extends Activity {
    private final static String CLICK_ACTION = "com.example.kingsoft.MyRemoteViews.CLICK_ACTION_ONE";
    private final static String REMOTEVIES_ID = "remoteViews_id";

    private LinearLayout linearLayout = null;
    private MyBroadcastReceiver mBroadcastReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_views);

        linearLayout = (LinearLayout)findViewById(R.id.remote_views_content);
        initBoradReceiver();

        Intent intentService = new Intent(this, RemoteViewsService.class);
        startService(intentService);
    }


    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == CLICK_ACTION){
                RemoteViews remoteViews = intent.getParcelableExtra(REMOTEVIES_ID);
                if (null != remoteViews){
                    View view = remoteViews.apply(context, linearLayout);
                    linearLayout.addView(view);
                }
            }
        }
    }


    public void  initBoradReceiver(){
        mBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CLICK_ACTION);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }
}
