package com.example.kingsoft.MyRemoteViews;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.kingsoft.CustomAdapter.R;


public class RemoteViews2Activity extends Activity implements View.OnClickListener {
    private final static String INTENT_BUTTONID_TAG = "ButtonId";
    private final static String REMOTEVIES_ID = "remoteViews_id";
    private final static String CLICK_ACTION = "com.example.kingsoft.MyRemoteViews.CLICK_ACTION_ONE";

    private final static int PRE_CLICK_ACTION = 0;
    private final static int PLAY_CLICK_ACTION = 1;
    private final static int NEXT_CLICK_ACTION = 2;

    private Button button_button = null;
    private Button sendRemoteViews = null;
    private Button startActivity = null;
    private MyBroadcastReceiver receiver = null;

    /** Notification 的ID */
    int notifyId = 101;

    private boolean isPlay = false;
    private NotificationManager manager = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_views2);

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        receiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CLICK_ACTION);
        registerReceiver(receiver, intentFilter);

        button_button = (Button)findViewById(R.id.button_button);
        button_button.setOnClickListener(this);

        sendRemoteViews = (Button) findViewById(R.id.start_intent_button);
        sendRemoteViews.setOnClickListener(this);

        startActivity = (Button) findViewById(R.id.start_intent);
        startActivity.setOnClickListener(this);

        Intent intentService = new Intent(this, RemoteViewsService.class);
        startService(intentService);
    }


    private void  createCustomRemoteViews(){
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.my_remoteviews_button_layout);
        if(isPlay){
            remoteViews.setImageViewResource(R.id.play_button, R.drawable.btn_pause);
        }else{
            remoteViews.setImageViewResource(R.id.play_button, R.drawable.btn_play);
        }

        remoteViews.setTextViewText(R.id.song_singer, "周杰伦");
        remoteViews.setTextViewText(R.id.tv_custom_song_name, "双节棍");

        Intent intent1 = new Intent(CLICK_ACTION);
        intent1.putExtra(INTENT_BUTTONID_TAG,PRE_CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.pre_button, pendingIntent);

        Intent intent2 = new Intent(CLICK_ACTION);
        intent2.putExtra(INTENT_BUTTONID_TAG, PLAY_CLICK_ACTION);
        PendingIntent playIntent = PendingIntent.getBroadcast(this, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.play_button, playIntent);

        Intent intent3 = new Intent();
        intent3.putExtra(INTENT_BUTTONID_TAG, NEXT_CLICK_ACTION);
        PendingIntent nextIntent = PendingIntent.getBroadcast(this, 1, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.next_button, nextIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContent(remoteViews)
                .setContentIntent(getDefalutIntent(Notification.FLAG_ONGOING_EVENT))
                .setTicker("正在播放")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(true)
                .setSmallIcon(R.drawable.icon);

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        manager.notify(notifyId,notification);
    }


    private RemoteViews  createCustomRemoteViews2(){
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.my_remoteviews_button_layout);
        if(isPlay){
            remoteViews.setImageViewResource(R.id.play_button, R.drawable.btn_pause);
        }else{
            remoteViews.setImageViewResource(R.id.play_button, R.drawable.btn_play);
        }

        remoteViews.setTextViewText(R.id.song_singer, "周杰伦");
        remoteViews.setTextViewText(R.id.tv_custom_song_name, "双节棍");

        Intent intent1 = new Intent(CLICK_ACTION);
        intent1.putExtra(INTENT_BUTTONID_TAG,PRE_CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.pre_button, pendingIntent);

        Intent intent2 = new Intent(CLICK_ACTION);
        intent2.putExtra(INTENT_BUTTONID_TAG, PLAY_CLICK_ACTION);
        PendingIntent playIntent = PendingIntent.getBroadcast(this, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.play_button, playIntent);

        Intent intent3 = new Intent();
        intent3.putExtra(INTENT_BUTTONID_TAG, NEXT_CLICK_ACTION);
        PendingIntent nextIntent = PendingIntent.getBroadcast(this, 1, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.next_button, nextIntent);

        return remoteViews;
    }

    private void createNotification(RemoteViews remoteViews){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContent(remoteViews)
                .setContentIntent(getDefalutIntent(Notification.FLAG_ONGOING_EVENT))
                .setTicker("正在播放")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(true)
                .setSmallIcon(R.drawable.icon);

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        manager.notify(notifyId,notification);
    }


    /**
     * @获取默认的pendingIntent,为了防止2.3及以下版本报错
     * @flags属性:
     * 在顶部常驻:Notification.FLAG_ONGOING_EVENT
     * 点击去除： Notification.FLAG_AUTO_CANCEL
     */
    public PendingIntent getDefalutIntent(int flags){
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_button){
            RemoteViews remoteViews = createCustomRemoteViews2();
            createNotification(remoteViews);
        }else if (v.getId() == R.id.start_intent_button){
            Intent intent = new Intent();
            intent.setAction(CLICK_ACTION);
            intent.putExtra(REMOTEVIES_ID, createCustomRemoteViews2());
            sendBroadcast(intent);
        }else if (v.getId() == R.id.start_intent){
            Intent intent = new Intent(this, RemoteViewsActivity.class);
            startActivity(intent);
        }
    }

    public class MyBroadcastReceiver extends  BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
//            Debug.waitForDebugger();
            if (intent.getAction() == ""){
                Bundle bundle = intent.getExtras();
                RemoteViews remoteViews = bundle.getParcelable(REMOTEVIES_ID);
                if (null != remoteViews){
                    createNotification(remoteViews);
                }
            }else if (intent.getAction() == CLICK_ACTION){
                Bundle bundle = intent.getExtras();
                int id = bundle.getInt(INTENT_BUTTONID_TAG);
                switch (id){
                    case PRE_CLICK_ACTION:
                        Toast.makeText(context, "上一首歌曲", Toast.LENGTH_LONG);
                        break;
                    case PLAY_CLICK_ACTION:
                        isPlay = !isPlay;
                        createCustomRemoteViews();
                        break;
                    case NEXT_CLICK_ACTION:
                        Toast.makeText(context, "下一首歌曲", Toast.LENGTH_LONG);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
