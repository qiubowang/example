package com.example.kingsoft.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by kingsoft on 2017/4/14.
 */

public class CustomTextEditor extends android.support.v7.widget.AppCompatTextView{
    private final float FONT_ACCURACY_SIZE = 2f;
    private int mMaxLine = 2;
    private float mDefaultSize = 0;
    public CustomTextEditor(Context context) {
        super(context);
    }

    public CustomTextEditor(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData(attrs);
    }

    public CustomTextEditor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        if (mDefaultSize > 0)
            super.setTextSize(TypedValue.COMPLEX_UNIT_PX,mDefaultSize);
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

        if (getLineCount() < mMaxLine || getMeasuredWidth() < 0 || mMaxLine < 0)
            return;
        float sourceFontSize = getTextSize();
        float miniFontSize = 0;
        float maxFontSize = sourceFontSize;
        float currentFontsie = 0;

        while ((maxFontSize - miniFontSize) > FONT_ACCURACY_SIZE){
            currentFontsie = (maxFontSize + miniFontSize)/2;
            super.setTextSize(TypedValue.COMPLEX_UNIT_PX, currentFontsie);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            if (getLineCount() > mMaxLine)
                maxFontSize = currentFontsie;
            else
                miniFontSize = currentFontsie;

        }

        if (miniFontSize < FONT_ACCURACY_SIZE/2)
            miniFontSize = sourceFontSize;

        super.setTextSize(TypedValue.COMPLEX_UNIT_PX, miniFontSize);
        if (miniFontSize != currentFontsie)
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    public void  setTextSize(int unit, float size){
        super.setTextSize(unit,size);
        mDefaultSize = getTextSize();
    }

    public void initData(AttributeSet attributeSet){
        if (null != attributeSet) {
            mMaxLine = attributeSet.getAttributeIntValue(null, "MaxLine", mMaxLine);
            mDefaultSize = getTextSize();
        }
    }
}
