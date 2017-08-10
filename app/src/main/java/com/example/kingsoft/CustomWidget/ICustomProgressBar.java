package com.example.kingsoft.CustomWidget;


/**
 * 文件名 ： CustomProgressBar.java
 * 
 * @author ： lijun 维护人 ： lijun 创建时间 ： 2011-3-9 下午03:08:48 文件描述 : 自定义进度条
 */
public interface ICustomProgressBar {
	public void setAppId(int appId);

	public void show() ;

	public void dismiss() ;

	public void setMax(int max) ;

	public int getMax();

	public void setProgress(int process) ;

	public int getProgress() ;

	public void setProgressPercentEnable(boolean textable);
	public void setProgerssInfoText(int resid) ;

	public void setProgerssInfoText(String info);
	
	public void setSubTitleInfoText(int resid);
	
	public void setSubTitleInfoText(String info);

	public boolean isFinish();

	public void update(ChangedObservable observable) ;

	public void updateProgress(int progress) ;
	
	public void setIndeterminate(boolean indeterminate) ;
}
