package com.example.kingsoft.MyRemoteViews;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.widget.RemoteViews;

import com.example.kingsoft.CustomAdapter.R;

public class RemoteViewsService extends Service {
    private final static String INTENT_BUTTONID_TAG = "ButtonId";
    private final static String REMOTEVIES_ID = "remoteViews_id";
    private final static String CLICK_ACTION = "com.example.kingsoft.MyRemoteViews.CLICK_ACTION_ONE";

    boolean isPlay = false;

    private final static int PRE_CLICK_ACTION = 0;
    private final static int PLAY_CLICK_ACTION = 1;
    private final static int NEXT_CLICK_ACTION = 2;

    public RemoteViewsService() {
    }


    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        new Thread(() -> {
            RemoteViews remoteViews = createCustomRemoteViews();
            Intent remoteIntent = new Intent(CLICK_ACTION);
            remoteIntent.putExtra(REMOTEVIES_ID, remoteViews);
            sendBroadcast(remoteIntent);
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    private RemoteViews createCustomRemoteViews(){
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

    public class RemoteViewsBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
