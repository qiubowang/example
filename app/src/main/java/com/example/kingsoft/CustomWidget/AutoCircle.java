package com.example.kingsoft.CustomWidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kingsoft on 2017/8/10.
 */

public class AutoCircle extends View{
    Paint paint = null;

    public AutoCircle(Context context) {
        this(context, null);
    }

    public AutoCircle(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.R.attr.textStyle);
    }

    public AutoCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AutoCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        paint = new Paint();
    }

    /**
     * Implement this to do your drawing.
     *
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        paint.setColor(Color.RED);
        int width = getWidth();
        int height = getHeight();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(width/2,height/2,150,paint);
    }
}
