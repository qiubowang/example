package com.example.kingsoft.CustomWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


/**
 * 文件名 ： CustomProgressBar.java
 * 
 * @author ： lijun 维护人 ： lijun 创建时间 ： 2011-3-9 下午03:08:48 文件描述 : 自定义进度条
 */
public class CustomProgressBar extends FrameLayout implements ChangedObservable.OnChangedListener, KProgressData.Listener {

	public static final int DEFAULT_MAX = 100;
	protected int style = STYLE_NORMAL;
	public final static int STYLE_NORMAL = 0;
	private ICustomProgressBar mProgressBar;

	public CustomProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		setInterruptTouchEvent(true);
		this.setVisibility(View.INVISIBLE);
		mProgressBar = new PublicCustomProgressBar(context, this);
	}

	public void setAppId(int appId) {
		if (mProgressBar != null)
			mProgressBar.setAppId(appId);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);

	}

	public void show() {
		this.setVisibility(View.VISIBLE);
		mProgressBar.show();
	}

	public void dismiss() {
		mProgressBar.dismiss();
	}

	public void setMax(int max) {
		mProgressBar.setMax(max);
	}

	public int getMax() {
		return mProgressBar.getMax();
	}

	public void setProgress(int process) {
		mProgressBar.setProgress(process);
	}

	public int getProgress() {
		return mProgressBar.getProgress();
	}

	public void setInterruptTouchEvent(final boolean interrupt) {
		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return interrupt;
			}
		});
	}

	public void setProgressPercentEnable(boolean textable) {
		mProgressBar.setProgressPercentEnable(textable);
	}

	public void setProgerssInfoText(int resid) {
		mProgressBar.setProgerssInfoText(resid);
	}

	public void setProgerssInfoText(String info) {
		mProgressBar.setProgerssInfoText(info);
	}
	
	public void setSubTitleInfoText(int resid) {
		mProgressBar.setSubTitleInfoText(resid);
	}

	public void setSubTitleInfoText(String info) {
		mProgressBar.setSubTitleInfoText(info);
	}

	public boolean isFinish() {
		return mProgressBar.isFinish();
	}
	
	public void setIndeterminate(boolean indeterminate)
	{
		mProgressBar.setIndeterminate(indeterminate);
	}

	@Override
	public void update(ChangedObservable observable) {
		mProgressBar.update(observable);
	}

	@Override
	public void updateProgress(int progress) {
		mProgressBar.updateProgress(progress);
	}
}
