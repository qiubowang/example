package com.example.kingsoft.CustomWidget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Debug;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.Button;

import com.example.kingsoft.CustomAdapter.R;

/**
 * 自定义属性MaxLine:
 * <AutoAdjustButton
 *      MaxLine="1"
 *      android:layout_width = "wrap_content"
 *      android:layout_height = "wrap_content"/>
 *
 */
@SuppressLint("AppCompatCustomView")
public class AutoAdjustButton extends Button {

	private final float FONT_ACCURACY_SIZE = 2f;
	private float mDefaultTextSize;
	
	private int mMaxLine = 2;
	
	public AutoAdjustButton(Context context) {
		this(context, null);
	}
	
	public AutoAdjustButton(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.buttonStyle);
	}
	
	public AutoAdjustButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (attrs != null) {
			TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoAdjustButton);
			int mMaxLine = typedArray.getInt(R.styleable.AutoAdjustButton_maxLineNum, 10);
			mDefaultTextSize = getTextSize();
			mMaxLine =  attrs.getAttributeIntValue(null, "MaxLine", mMaxLine);
		}
	}
	
//	public void setMaxLine(int line) {
//		mMaxLine = line;
//	}
	
	@Override
	public void setTextSize(int unit, float size)
	{
		super.setTextSize(unit, size);
		mDefaultTextSize = getTextSize();
	}
	
	public void setTempTextSize(int unit, float size) {
		super.setTextSize(unit, size);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		Debug.waitForDebugger();
		if (mDefaultTextSize > 0)
			super.setTextSize(TypedValue.COMPLEX_UNIT_PX, mDefaultTextSize);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		if (mMaxLine <= 0 || getLineCount() <= mMaxLine || getMeasuredWidth() <= 0) {
			return;
		}
		
		//高度大于两行
		float maxFontSize = getTextSize();
		float minFontSize = 0;
		float oriFontSize = maxFontSize;
		float curFontSize = 0;
		
		while (maxFontSize - minFontSize > FONT_ACCURACY_SIZE) {
			curFontSize = (maxFontSize + minFontSize) / 2;
			
			super.setTextSize(TypedValue.COMPLEX_UNIT_PX, curFontSize );
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			
			if (getLineCount() > mMaxLine) {
				maxFontSize = curFontSize;
			} else {
				minFontSize = curFontSize;
//				break;
			}
		}
		
		if (minFontSize < FONT_ACCURACY_SIZE / 2) {
			minFontSize = oriFontSize;
		}
		
		super.setTextSize(TypedValue.COMPLEX_UNIT_PX, minFontSize );
		if (Math.abs(curFontSize - minFontSize) < 0.0000001f)
		{
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
	
}
