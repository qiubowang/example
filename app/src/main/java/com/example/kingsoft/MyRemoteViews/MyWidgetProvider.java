package com.example.kingsoft.MyRemoteViews;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Debug;
import android.os.SystemClock;
import android.widget.RemoteViews;

import com.example.kingsoft.ActivityLibs.MyAppWidgetProvider;
import com.example.kingsoft.CustomAdapter.R;

public class MyWidgetProvider extends AppWidgetProvider {
    private static final String CLICK_ACITON = "com.example.kingsoft.MyRemoteViews.CLICK_ACTION";
    private static int sDegree = 0;
    AppWidgetManager widgetManager = null;

    public MyWidgetProvider(){
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Debug.waitForDebugger();
        if (intent.getAction() == CLICK_ACITON){
            widgetManager = AppWidgetManager.getInstance(context);
            new Thread(() -> {
                Bitmap srcBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
                Matrix matrix = new Matrix();
                matrix.reset();
                matrix.setRotate((sDegree + 90) % 360);
                Bitmap bitmap = Bitmap.createBitmap(srcBitmap,0,0,srcBitmap.getWidth(),srcBitmap.getHeight(), matrix, true);
                RemoteViews remoteViews = createRemoteViews(context, bitmap);
                widgetManager.updateAppWidget(new ComponentName(context, MyAppWidgetProvider.class), remoteViews);
                SystemClock.sleep(100);

            }).start();

        }
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int i = 0, length = appWidgetIds.length; i < length; i++){
            int appIds = appWidgetIds[i];
            Bitmap srcBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
            RemoteViews remoteViews = createRemoteViews2(context);
            appWidgetManager.updateAppWidget(appIds,remoteViews);
        }
//        Arrays.stream(appWidgetIds).forEach(appIds->{
//            Bitmap srcBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.image3);
//            RemoteViews remoteViews = createRemoteViews(context, srcBitmap);
//            appWidgetManager.updateAppWidget(appIds,remoteViews);
//        });
    }

    public RemoteViews createRemoteViews(Context context, Bitmap bitmap){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.content_my_widget_provider);
        remoteViews.setImageViewBitmap(R.id.widget_image_one, bitmap);
        Intent intentClick = new Intent(CLICK_ACITON);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_image_one, pendingIntent);

        return remoteViews;
    }

    public RemoteViews createRemoteViews2(Context context){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.content_my_widget_provider);
        remoteViews.setImageViewResource(R.id.widget_image_one, R.drawable.icon);
        Intent intentClick = new Intent(CLICK_ACITON);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_image_one, pendingIntent);

        return remoteViews;
    }
}
