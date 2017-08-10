package com.example.kingsoft.CustomWidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class MaterialProgressBarHorizontal extends View {

	private int max = 100;
	private int min = 0;
	
	private Paint mPaint = new Paint();
	private int progress = 0;

	private int minHeight = 3;
	private int backgroundColor = 0xffb3e1fa;
	private boolean indeterminate = true;
	private int duration = 1333;

	MaterialProgressBarHorizontalAnimation mAnimation = new MaterialProgressBarHorizontalAnimation();
	
	public MaterialProgressBarHorizontal(Context context) {
		this(context, null);
	}

	public MaterialProgressBarHorizontal(Context context, AttributeSet attrs) {
		this(context, attrs,  0);
	}
	
	public MaterialProgressBarHorizontal(Context context, AttributeSet attrs, int defStyleRes) {
		super(context, attrs, defStyleRes);
		setAttributes(context, attrs, defStyleRes);
	}

	// Set atributtes of XML to View
	protected void setAttributes(Context context, AttributeSet attrs, int defStyleRes) {
		ResouceManager rm = new ResouceManager(context);
		
		TypedArray a = context.obtainStyledAttributes(attrs, rm.getStyleableArr("MaterialProgressBarHorizontal"), 0, defStyleRes);
		setMinimumHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, minHeight, getResources().getDisplayMetrics()));
		
		backgroundColor = a.getColor(rm.getStyleableId("MaterialProgressBarHorizontal_bgColor"), backgroundColor);
		indeterminate = a.getBoolean(rm.getStyleableId("MaterialProgressBarHorizontal_indeterminate"), indeterminate);
		duration = a.getInt(rm.getStyleableId("MaterialProgressBarHorizontal_duration"), duration);
	
		int progressColor = a.getColor(rm.getStyleableId("MaterialProgressBarHorizontal_barColor"), 0xff0ea7fa);
		backgroundColor = 0xffb3e1fa;
		indeterminate = true;
		duration = 1200;
		
		mPaint.setColor(progressColor);
		setBackgroundColor(backgroundColor);
		a.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.drawColor(backgroundColor);
		
		if (indeterminate)
		{
			if (progress == 0)
			{
				mAnimation.countProgress();
				mAnimation.draw(canvas);
				invalidate();
				return;
			}
			else
			{
				mAnimation.reset();
			}
		}
		
		drawProgress(canvas, progress);
	}
	
	private void drawProgress(Canvas canvas, int currentProgress)
	{
		if (currentProgress > max)
			progress = max;
		if (currentProgress < min)
			progress = min;
		
		int totalWidth = max - min;
		double progressPercent = (double) progress / (double) totalWidth;
		int progressWidth = (int) (getWidth() * progressPercent);

		if(isRTL()) {
			canvas.drawRect((getWidth()-progressWidth), 0, getWidth(), getHeight(), mPaint);
		} else {
			canvas.drawRect(0, 0, progressWidth, getHeight(), mPaint);
		}


	}

	public void setMax(int max) {
		this.max = max;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public void setProgress(int progress) {
		this.progress = progress;
		invalidate();
	}

	public int getProgress() {
		return progress;
	}

	// Set color of background
	public void setBackgroundColor(int color) {
		this.backgroundColor = color;
	}
	
	public void setProgressColor(int color) {
		mPaint.setColor(color);
	}
	
	public boolean isIndeterminate() {
		return indeterminate;
	}

	public void setIndeterminate(boolean indeterminate) {
		if(this.indeterminate && indeterminate) 
			return;
		this.indeterminate = indeterminate;
		
		invalidate();
	}
	
	@Override
	public void setVisibility(int visibility) {
		if(getVisibility() != visibility) {
			super.setVisibility(visibility);
			checkAnimation(visibility);
		}
	}
	
	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		checkAnimation(visibility);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		checkAnimation(INVISIBLE);
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		checkAnimation(getVisibility());
	}
	
	private void checkAnimation(int visibility)
	{
		if (indeterminate && visibility == View.VISIBLE)
		{
//			invalidate();
			//其实什么都不用做
		}
		else if (mAnimation != null)
		{
			mAnimation.reset();
		}
	}
	
	public int getMax()
	{
		return max;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		
		 //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            setMeasuredDimension(getMeasuredWidth(), heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
    		setMeasuredDimension(getMeasuredWidth(), getSuggestedMinimumHeight());
        }
    }
	
	class MaterialProgressBarHorizontalAnimation
	{
		long startTime = 0;
		float width = 0.4f;
		float currentProgress;
		boolean isInAnimation = false;
		
		private void reset()
		{
			isInAnimation = false;
		}
		
		private boolean isInAnimation()
		{
			return isInAnimation;
		}
		
		private void countProgress()
		{
			if (isInAnimation)
			{
				long currentTime = System.currentTimeMillis();
				currentProgress = (float)(currentTime - startTime) / duration;
				
				//加速效果 参考AccelerateInterpolator
				currentProgress = currentProgress * currentProgress;
				
				if (currentProgress >= 1.0f)
				{
					isInAnimation = false;
				}
			}
			else
			{
				currentProgress = 0;
				startTime = System.currentTimeMillis();
				isInAnimation = true;
			}
		}
		
		private void draw(Canvas canvas)
		{
			if (isRTL()) {
				int progressEnd = (int) (getWidth() * (1 + width) * (1.0f - currentProgress) - getWidth() * width);
				int progressBegin = (int) (progressEnd - getWidth() * width);
				canvas.drawRect(progressBegin, 0, progressEnd, getHeight(), mPaint);
			} else {
				int progressBegin = (int) (getWidth() * (1 + width) * currentProgress - getWidth() * width);
				int progressEnd = (int) (progressBegin + getWidth() * width);
				canvas.drawRect(progressBegin, 0, progressEnd, getHeight(), mPaint);
			}
		}
	}

	private boolean isRTL(){
		if(Build.VERSION.SDK_INT>=17) {
			return getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
		}
		return false;
	}

}
