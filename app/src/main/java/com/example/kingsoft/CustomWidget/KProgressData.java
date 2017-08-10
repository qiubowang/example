package com.example.kingsoft.CustomWidget;

import android.os.Handler;
import android.os.Looper;

public class KProgressData {

	static final String TAG = KProgressData.class.getSimpleName();
	
	private static final float FLOAT_ACCURACY = 0.001f;
	
	public static final int MAX_PROGRESS = 100;
	
	public interface Listener
	{
		void updateProgress(int progress);
	}
	
	private float mProgress = MAX_PROGRESS;
	
	private Listener mListener;
	
	private Runnable mOnFinishRunnable;
	
	private Handler mHandler = new Handler(Looper.getMainLooper());
	
	/**
	 * progress listener canceled
	 */
	private volatile boolean mIsCancelled;
	
	public void dispose()
	{
		mListener = null;
		mOnFinishRunnable = null;
		mHandler = null;
	}
	
	public void setListener(Listener l) {
		mListener = l;
	}
	
	public void setOnFinishRunnable(Runnable r)
	{
		mOnFinishRunnable = r;
	}
	
	synchronized public void setCancelled(boolean isCancelled)
	{
		mIsCancelled = isCancelled;
	}

	synchronized public boolean isCancelled()
	{
		return mIsCancelled;
	}
	
	public boolean isFinished()
	{
//		return MAX_PROGRESS <= mProgress;
		return Math.abs(mProgress - MAX_PROGRESS) < FLOAT_ACCURACY;
	}

	public int getProgress()
	{
		return (int)mProgress;
	}
	
	protected float getAccurateProgress()
	{
		return mProgress;
	}
	
	protected Handler getHandler()
	{
		return mHandler;
	}
	
	public void resetProgress()
	{
		mProgress = -1;
	}
	
	public void updateProgress(float progress)
	{
//		KLog.debug(TAG, "updateProgress " + progress);
		
		if (progress < 0 || progress > MAX_PROGRESS)
		{
			progress = progress < 0 ? 0 : MAX_PROGRESS;
		}
		if (mProgress != progress)
		{
			mProgress = progress;
			
			if (null != mListener)
			{
				mListener.updateProgress((int)progress);
			}
		}
		
		if (isFinished() && null != mOnFinishRunnable)
		{
			mHandler.post(mOnFinishRunnable);
			mOnFinishRunnable = null;
		}
	}
}
