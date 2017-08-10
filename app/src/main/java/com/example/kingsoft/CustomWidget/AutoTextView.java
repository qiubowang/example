package com.example.kingsoft.CustomWidget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by kingsoft on 2017/8/9.
 */

@SuppressLint("AppCompatCustomView")
public class AutoTextView extends TextView {
    private final float FONT_ACCURACY_SIZE = 2f;
    private int MaxLine = 2;
    public AutoTextView(Context context) {
        this(context, null);
    }

    public AutoTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.R.attr.textStyle);
    }

    public AutoTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AutoTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (null != attrs)
            MaxLine = attrs.getAttributeIntValue(null, "MaxLine", MaxLine);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Debug.waitForDebugger();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (MaxLine <= 0 || MaxLine >= getLineCount())
            return;
        float maxFontSize = getTextSize();
        float minFontSize = 0;
        float curFontSize = maxFontSize;

        while ((maxFontSize - minFontSize) > FONT_ACCURACY_SIZE){
            curFontSize = (maxFontSize + minFontSize)/2;
            super.setTextSize(TypedValue.COMPLEX_UNIT_PX, curFontSize);
            super.measure(widthMeasureSpec, heightMeasureSpec);
            if (getLineCount() > MaxLine){
                maxFontSize = curFontSize;
            }else {
                minFontSize =curFontSize;
            }
        }

        super.measure(widthMeasureSpec,heightMeasureSpec);
    }
}
