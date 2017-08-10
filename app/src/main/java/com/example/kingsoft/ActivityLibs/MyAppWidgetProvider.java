package com.example.kingsoft.ActivityLibs;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.kingsoft.CustomAdapter.R;

/**
 * Created by kingsoft on 2017/7/18.
 */

public class MyAppWidgetProvider extends AppWidgetProvider{
    public static final String CLICK_ACTION = "com.example.kingsoft.ActivityLibs.CLICK_ACTION";

    public MyAppWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() == CLICK_ACTION){
            Toast.makeText(context, "点击了小部件", Toast.LENGTH_LONG);
            new Thread(() -> {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.image1);
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                for (int i = 0; i < 37; i++){
                    int degree = (10 * i) % 360;
                    RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
                    remoteViews.setImageViewBitmap(R.id.widget_image1, rotateBitmap(context, bitmap, degree));
                    Intent intentClick = new Intent();
                    intentClick.setAction(CLICK_ACTION);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0);
                    remoteViews.setOnClickPendingIntent(R.id.widget_image1, pendingIntent);

                    manager.updateAppWidget(new ComponentName(context, MyAppWidgetProvider.class), remoteViews);
                    SystemClock.sleep(300);
                }
            }).start();
        }
    }

    /**
     * @see AppWidgetManager#ACTION_APPWIDGET_UPDATE
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int i = 0, length = appWidgetIds.length; i < length; i++){
            int appWidgetId = appWidgetIds[i];
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        Intent intentClick = new Intent();
        intentClick.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget_image1, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    private Bitmap rotateBitmap(Context context, Bitmap srcBitmap, int degree){
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap bitmap = Bitmap.createBitmap(srcBitmap,0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
        return bitmap;
    }

}
