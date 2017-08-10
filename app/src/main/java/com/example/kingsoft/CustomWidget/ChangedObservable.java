package com.example.kingsoft.CustomWidget;

/**
 * 文件名		：    ChangedObservable.java  
 * @author		：    	lijun
 * 维护人		：	lijun
 * 创建时间		： 	2011-3-9   下午04:42:07
 * 文件描述		: 	变化通知接口
 */
public interface ChangedObservable
{
	public void addOnChangedListener(OnChangedListener listener);
	public void removeOnChangedListener(OnChangedListener listener);
	public void removeAllListener();
	public void notifyChanged();
			
	public interface OnChangedListener
	{
		/**
		 * 通知更新
		 */
		public void update(ChangedObservable observable);
	}
}
