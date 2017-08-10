package com.example.kingsoft.CustomWidget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import java.text.NumberFormat;


public class PublicCustomProgressBar implements ICustomProgressBar {

	public static final int DEFAULT_MAX = 100;
	private int mMax = DEFAULT_MAX;
	private int mProcess = 0;
	private boolean mIsDataFinish;
	private boolean mIsPercentEnable = true;

	protected MaterialProgressBarHorizontal mPercent;
	protected TextView mInfoText;
	protected TextView mSubTitleText;
	protected TextView mPercentText;
	private LayoutInflater mInflater;
	private NumberFormat mProgressPercentFormat;

	private boolean initFlag = false;

	private ResouceManager rm;
	private View mRootView;
	private ViewGroup mParent;
	private Context mContext;
	
	private int mAppId = 0;
	private boolean mIsPhone;

	public PublicCustomProgressBar(Context context, ViewGroup parent) {
		mInflater = LayoutInflater.from(context);
		mContext = context;
		mParent = parent;
		mIsPhone = DisplayUtil.isPhoneScreen(mContext);
		rm = new ResouceManager(mContext);
		mProgressPercentFormat = NumberFormat.getPercentInstance();
		mProgressPercentFormat.setMaximumFractionDigits(0);
		getRootView();
	}

	public View getRootView() {
		if (mRootView == null) {
			mRootView = mInflater.inflate(mIsPhone ? rm.getLayoutId("phone_public_custom_progress")
					: rm.getLayoutId("public_custom_progressbar_pad"), mParent, true);
			if(mIsPhone) {
				//针对小屏手机缩小屏幕
				int dialogWidth = rm.getDimen(rm.getDimenId("phone_public_dialog_width"));
				float windowWidth = Math.min(DisplayUtil.getWindowWidth((Activity)mContext), DisplayUtil.getWindowHeight((Activity)mContext));
				if(dialogWidth > windowWidth) {
					dialogWidth = (int) windowWidth;
				} 
				mRootView.setLayoutParams(new LayoutParams(dialogWidth, LayoutParams.WRAP_CONTENT));
			}
		}
		return mRootView; 
	}

	private void init() {
		if (!initFlag) {
			initWithNormal();
			initFlag = true;
		}
	}

	@Override
	public void setAppId(int appId) {
		mAppId = appId;
	}

	@Override
	public void setProgressPercentEnable(boolean textable) {
		mIsPercentEnable = textable;
	}

	private void initWithNormal() {
		mPercent = (MaterialProgressBarHorizontal) getRootView().findViewById(rm.getId("progress"));
		mInfoText = (TextView) getRootView().findViewById(rm.getId("progress_message"));
		if (mIsPhone)
		{
			mSubTitleText = (TextView) getRootView().findViewById(rm.getId("progress_sub_message"));
		}
		mPercentText = (TextView) getRootView().findViewById(rm.getId("progress_percent"));
	}

	public void show() {
		init();
		getRootView().setVisibility(View.VISIBLE);
		mProcess = 0;
		mPercentText.setText(null);
		setProgress(mProcess);
	}

	public void dismiss() {
		getRootView().setVisibility(View.GONE);
	}

	public void setMax(int max) {
		mMax = max;
	}

	public int getMax() {
		return mMax;
	}

	public void setProgress(final int value) {
		mPercent.post(new Runnable() {
			@Override
			public void run() {
				mProcess = value;
				
				mPercent.setProgress(value);
				onProgressChanged();
			}
		});
	}

	private void onProgressChanged() {
		/* Update the number and percent */
		int progress = mPercent.getProgress();
		int max = mPercent.getMax();
		double percent = (double) progress / (double) max;
		SpannableString tmp = new SpannableString(mProgressPercentFormat.format(percent));
		tmp.setSpan(new StyleSpan(mIsPhone ? Typeface.BOLD : Typeface.NORMAL), 0, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		if (mIsPercentEnable && progress > 0) {
			mPercentText.setText(tmp);
		}
	}

	public int getProgress() {
		return mProcess;
	}

	public void setProgerssInfoText(int resid) {
		init();
		mInfoText.setText(mContext.getResources().getString(resid));
	}

	public void setProgerssInfoText(String info) {
		init();
		mInfoText.setText(info);
	}
	
	public void setSubTitleInfoText(int resid) {
		if (mIsPhone)
		{
			try {
				mSubTitleText.setText(resid);
				mSubTitleText.setVisibility(View.VISIBLE);
			} catch (NotFoundException e) {
				e.printStackTrace();
				mSubTitleText.setVisibility(View.GONE);
			}
		}
	}

	public void setSubTitleInfoText(String info) {
		if (mIsPhone)
		{
			if (TextUtils.isEmpty(info)) {
				mSubTitleText.setVisibility(View.GONE);
				return;
			}
			mSubTitleText.setVisibility(View.VISIBLE);
			mSubTitleText.setText(info);
		}
	}

	public boolean isFinish() {
		return (mProcess >= mMax || mIsDataFinish);
	}

	public void update(ChangedObservable observable) {
		if (observable instanceof ProgressData) {
			ProgressData data = (ProgressData) observable;
			mIsDataFinish = data.isFinish();
			if (data.getProgressCount() > 0 && DEFAULT_MAX == mMax) {
				setMax(data.getProgressCount());
			}

			setProgress(data.getCurrentProgress());
		} else if (observable instanceof ProgressData.PercentData) {
			ProgressData.PercentData data = (ProgressData.PercentData) observable;
			mIsDataFinish = data.isFinish();
			setProgress(data.getCurrentPercent());
		}
	}

	public void updateProgress(int progress) {
		setProgress(progress);
	}
	
	@Override
	public void setIndeterminate(boolean indeterminate)
	{
		if (mPercent == null)
		{
			init();
		}
		mPercent.setIndeterminate(indeterminate);
	}
}