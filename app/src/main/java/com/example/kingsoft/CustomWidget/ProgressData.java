package com.example.kingsoft.CustomWidget;

/**
 * 文件名		：    ProgressData.java  
 * @author		：    lijun
 * 维护人		：	lijun
 * 创建时间		： 	2011-3-10   上午09:09:27
 * 文件描述		: 	进度数据接口
 */
public interface ProgressData
{
	public int getProgressCount();
	public int getCurrentProgress();
	public boolean isFinish();
	
	public interface PercentData
	{
		public int getCurrentPercent();
		public boolean isFinish();
	}
}