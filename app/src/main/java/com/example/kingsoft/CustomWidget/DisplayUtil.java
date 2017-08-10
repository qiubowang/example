package com.example.kingsoft.CustomWidget;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

/*
*	文件名		：	Tools.java
*	创建者		：	朱健
*	维护人		：	朱健
*	创建时间		：	2011-2-18 14:08:48
*	功能描述		：	处理单位换算和排序
*					
*/
public class DisplayUtil 
{
	// for bug 149020, 改成dp mx3 72.8
	// for bug:115198 Kindle Fire 底部间距是60 ; MX2 96 MX3 72.8dp ; Amazon 60dp
	public static final int DETECT_SKB_DELTA = 75; // dp
	
	private static float mDip = -1;
	private static boolean ENFORCE_PHONE_SCREEN = false;
	private static DisplayMetrics metrics = new DisplayMetrics();
	private static float sw = -1;
	
	public static int getDeviceHeight(Context context) {
	    return getRealMetrics(context).heightPixels;
	}
	
	public static int getDeviceWidth(Context context) {
	    return getRealMetrics(context).widthPixels;
	}
	
	public static float getDeviceDensity(Context context)
    {
	    return getRealMetrics(context).density;
    }
	
	public static float getSW(Context context) {
		if (sw == -1) {
			sw = Math.min(getDeviceWidth(context), getDeviceHeight(context));
		}
		return sw;
	}
	
	//判断设备当前在重力下的横竖屏状态，与WPS本身的“横竖屏”布局无关，因为被华为强迫适配Android N的多窗口时，WPS的横竖屏概念已经乱掉了，临时方案。 ——黄伯荣
	public static boolean isDeviceLand(Context context) {
		return getDeviceWidth(context) > getDeviceHeight(context);
	}
	
    public static float getWindowHeight(Activity context)
    {
    	if (null != context.getWindow().getDecorView()) {
        	Rect rect = new Rect();
        	context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
    	    return rect.height();
		}
    	return 0;
    }
    
    public static float getWindowWidth(Activity context)
    {
    	if (null != context.getWindow().getDecorView()) {
        	Rect rect = new Rect();
        	context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
    	    return rect.width();
		}
    	return 0;
    }
    
    public static Rect getWindowRect(Activity context)
    {
    	Rect rect = new Rect();
    	if (null != context.getWindow().getDecorView()) {
        	context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
    	}
    	
    	return rect;
    }

	public static boolean isRTL() {
		if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
			return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL;
		}
		return false;
	}

	/**
     * PadPhone 切换需要调用
     */
    public static void dispose()
    {
    	sStatusBarHeight = 0;
    	mDip = -1;
    }
    private static int sStatusBarHeight = 0;
    /**
     * 获取屏幕上方的状态栏高度（全屏状态不要调用）
     * @param context
     * @return
     */
    public static float getStatusBarHeight(Activity context)
    {
    	return getStatusBarHeight(context, null);
    }
    /**
     * 获取屏幕上方的状态栏高度（全屏状态不要调用）
     * @param context
     * @param isSurePhoneScreen 优化参数，为true确认是手机版少调用一次isPhoneScreen
     * @return
     */
    public static float getStatusBarHeight(Activity context, Boolean isSurePhoneScreen)
    {
   		if (sStatusBarHeight > 0)
		{
			return sStatusBarHeight;
		}
    	int statusBarHeight = -1;
    	View decorView = context.getWindow().getDecorView();
   		float screenHeight = getDisplayHeight(context);
    	Rect rect = new Rect();
    	decorView.getWindowVisibleDisplayFrame(rect);
		if (rect.height() <= 0 || rect.height() > screenHeight)//getWindowVisibleDisplayFrame失效
		{
			decorView.getGlobalVisibleRect(rect);
		}
	    statusBarHeight	= rect.top;
	    if (statusBarHeight <= 0)
	    {
			boolean isPhone = isSurePhoneScreen != null ? isSurePhoneScreen : isPhoneScreen(context);
			if (isPhone || !hasOnScreenSystemBar(context))// 手机上使用reflectStatusBarHeight
			{
				statusBarHeight = getInternalStatusBarHeight(context);
			}
	    }
    	
    	if (statusBarHeight < 0) 
    	{
    		statusBarHeight = 0;
    	}
    	sStatusBarHeight = statusBarHeight;
    	return statusBarHeight;
    }
    
	private static boolean hasOnScreenSystemBar(Activity context)
	{
		Display display = context.getWindowManager().getDefaultDisplay();
		int rawDisplayHeight = 0;
		try
		{
			// Moto 平板可以取到这个方法，有状态条的平板没有这个方法
			Method getRawHeight = Display.class.getMethod("getRawHeight");
			rawDisplayHeight = (Integer) getRawHeight.invoke(display);
		}
		catch (Exception ex)
		{
		}
		int UIRequestedHeight = display.getHeight();
		return rawDisplayHeight - UIRequestedHeight > 0;
	}
    
    /**
     * 系统状态栏是否可见
     */
    public static boolean isStatusBarVisible(Activity context)
    {
    	 WindowManager.LayoutParams params = context.getWindow().getAttributes();
    	 int paramsFlag =params.flags & (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	 if (paramsFlag == params.flags)
    	 {
    		 return true;
    	 }
    	 return false;
    }
    /**
     * 隐藏系统状态栏
     */
    public static void hideStatusBar(Activity context)
    {
    	//该属性保证状态栏的隐藏和显示不会挤压Activity向下，状态栏覆盖在Activity之上
		Window window = context.getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
				WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR);

		WindowManager.LayoutParams params = window.getAttributes();
		params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
		window.setAttributes(params);
    }
    
    /**
     * 显示系统状态栏
     */
    public static void showStatusBar(Activity context)
    {
		Window window = context.getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
				WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR);

		WindowManager.LayoutParams params = window.getAttributes();
		params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
		window.setAttributes(params);
    }
    
    /**
     * 切换系统状态栏状态
     */
    public static void toggleStatusBar(Activity context)
    {
		if (isStatusBarVisible(context)) 
		{
			hideStatusBar(context);
		} else 
		{
			showStatusBar(context);
		}
	}
    
    public static int getInternalStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            try {
                result = context.getResources().getDimensionPixelSize(resourceId);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    public static float getTitleBarHeight(Activity context)
    {
    	if (null != context) {
        	Window window = context.getWindow();
        	int contentViewTop= window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
    	    int titleBarHeight= contentViewTop - (int)getStatusBarHeight(context);
        	return titleBarHeight;
		}
    	return 0;
    }
    
    public static int getDisplayWidth(Context context)
	{
    	//context.getResources().getDisplayMetrics()里的那个是缓存下来，在updateConfiguration()里面再更新的,这里用实时的方法取，性能问题折中一下。for bug：210490
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }
    		
    public static int getDisplayHeight(Context context)
    {
    	//context.getResources().getDisplayMetrics()里的那个是缓存下来，在updateConfiguration()里面再更新的,这里用实时的方法取，性能问题折中一下。for bug：210490
    	((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}
    
    public static DisplayMetrics getRealMetrics(Context context) {
        Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        if (DeviceUtil.isAndroidN()) {
            // android-n以后支持分屏，改用realmetric方法获取实际屏幕大小
            display.getRealMetrics(dm);
        } else {
			display.getMetrics(dm);
        }
        return dm;
    }

    
    public static boolean isInMultiWindow(Activity activity) {
    	return false;
    }
    
    public static int getScreenSize(Context context)
    {
    	Configuration config = context.getResources().getConfiguration();
    	return config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
    }
    
    public static boolean isSmallScreenSize(Context context)
    {
		return Configuration.SCREENLAYOUT_SIZE_SMALL == getScreenSize(context);
    }
    
    public static boolean isNormalScreenSize(Context context)
    {
    	return Configuration.SCREENLAYOUT_SIZE_NORMAL == getScreenSize(context);
    }

    public static boolean isLargeScreenSize(Context context)
    {
		return Configuration.SCREENLAYOUT_SIZE_LARGE == getScreenSize(context);
    }
    
    public static boolean isXLargeScreenSize(Context context)
    {
		return 0x04 /*Configuration.SCREENLAYOUT_SIZE_XLARGE*/ == getScreenSize(context);//API 9
    }
    
    /**
     * 得到密度系数如：1.5
     * @param context
     * @return
     */
    public static float getDensity(Context context)
    {
    	WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metrics);
		return metrics.density;	
    }
    
    /**
     * 得到密度值例如DENSITY_MEDIUM：160
     * @param context
     * @return
     */
    public static int getDensityDpi(Context context)
    {
    	WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metrics);
		return metrics.densityDpi;
    }
    
    public static boolean screenCompareWith(int dpi, Context context)
    {
    	return Math.min(getDisplayWidth(context), getDisplayHeight(context)) >= dpi;
    }
    
    public static boolean isXHighDensity(Context context)
    {
    	return getDensityDpi(context) == DisplayMetrics.DENSITY_XHIGH;
    }
    
    public static boolean isHighDensity(Context context)
    {
    	return getDensityDpi(context) == DisplayMetrics.DENSITY_HIGH;
    }
    
    public static boolean isMediumDensity(Context context)
    {
    	return getDensityDpi(context) == DisplayMetrics.DENSITY_MEDIUM;
    }

    public static boolean isLowDensity(Context context)
    {
    	return getDensityDpi(context) == DisplayMetrics.DENSITY_LOW;
    }

    public static boolean isDefaultDensity(Context context)
    {
    	return getDensityDpi(context) == DisplayMetrics.DENSITY_DEFAULT;
    }
    
    public static boolean isPhoneScreen(Context context)
    {
    	return !isPadScreen(context);
    }
    
    public static boolean isPadScreen(Context context)
    {
    	if (ENFORCE_PHONE_SCREEN)
    	{
    		return false;
    	}

    	int wDp = (int)(getDeviceWidth(context) / getDensity(context));
    	int hDp = (int)(getDeviceHeight(context) / getDensity(context));
    	int longSizeDp = Math.max(wDp, hDp);
    	int shortSizeDp = Math.min(wDp, hDp);
        // What size is this screen screen?
        if (longSizeDp >= 960 && shortSizeDp >= 720) {
            // 1.5xVGA or larger screens at medium density are the point
            // at which we consider it to be an extra large screen.
            return true;
        } else if (longSizeDp >= 640 && shortSizeDp >= 480) {
            // VGA or larger screens at medium density are the point
            // at which we consider it to be a large screen.
            return true;
        }
    	return false;
    }

    public static float getContextDecorViewTop(Activity context)
    {
    	if (null != context.getWindow().getDecorView()) {
        	Rect rect = new Rect();
        	context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
    	    int contextDecorViewTop= rect.top;
    	    return contextDecorViewTop;
		}
    	return 0;
    }

    public static float getContextDecorViewWidth(Activity context)
    {
    	if (null != context.getWindow().getDecorView()) {
        	Rect rect = new Rect();
        	context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
    	    int contextDecorViewWidth= rect.right-rect.left;
    	    return contextDecorViewWidth;
		}
    	return 0;
    }

    public static float getContextDecorViewHeight(Activity context)
    {
    	if (null != context.getWindow().getDecorView()) {
        	Rect rect = new Rect();
        	context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
    	    int contextDecorViewHeight= rect.bottom-rect.top;
    	    return contextDecorViewHeight;
		}
    	return 0;
    }

    //dialog算高度时，把顶部的状态栏也算上。
    public static float getContextDecorViewHeightInDialog(Activity context)
    {
    	if (null != context.getWindow().getDecorView()) {
        	Rect rect = new Rect();
        	context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
    	    int contextDecorViewHeight= rect.bottom- 0;
    	    return contextDecorViewHeight;
		}
    	return 0;
    }

	public static void toggleSoftInput(final View targetView)
	{
		if (null != targetView)
		{
			final InputMethodManager imm = (InputMethodManager) targetView.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	public static void showSoftKeyBoard(View targetView)
	{
		if (null != targetView)
		{
			InputMethodManager imm = (InputMethodManager) targetView.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(targetView, 0);
		}
	}

	public static void hideSoftKeyBoard(View targetView)
	{
		hideSoftKeyBoard(targetView, null);
	}

	public static boolean hideSoftKeyBoard(View targetView, ResultReceiver resultReceiver)
	{
		if (null != targetView)
		{
			InputMethodManager imm = (InputMethodManager) targetView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			if (null != imm)
			{
				boolean result = imm.hideSoftInputFromWindow(targetView.getWindowToken(), 0, resultReceiver);
				return result;
			}
		}
		return false;
	}

	/**
	 * 4.0 关闭硬件加速None
	 */
	public static void setLayerTypeNone(View view, Paint paint)
	{
		setLayerType(view, paint, 0);
	}

	/**
	 * 4.0 关闭硬件加速Soft
	 */
	public static void setLayerTypeSoft(View view, Paint paint)
	{
		setLayerType(view, paint, 1);
	}

	/**
	 * 4.0 硬件加速
	 */
	public static void setLayerTypeHard(View view, Paint paint)
	{
		setLayerType(view, paint, 2);
	}

	private static void setLayerType(View view, Paint paint, int type)
	{
		if (Build.VERSION.SDK_INT < 14)
		{
			return;
		}
		int softwareType = type;
		Class<View> c = View.class;
		Method setLayerType = null;
		@SuppressWarnings("rawtypes")
		Class[] cargs = new Class[2];
		Object[] inArgs = new Object[2];
		try
		{
			// 获取所有public/private/protected/默认
			// 方法的函数，如果只需要获取public方法，则可以调用getMethod.
			cargs[0] = int.class;
			cargs[1] = Paint.class;
			inArgs[0] = softwareType;
			inArgs[1] = paint;
			setLayerType = c.getMethod("setLayerType", cargs);
			// 将要执行的方法对象设置是否进行访问检查，也就是说对于public/private/protected/默认
			// 我们是否能够访问。值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false
			// 则指示反射的对象应该实施 Java 语言访问检查。
			setLayerType.setAccessible(true);
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		if (setLayerType != null)
		{
			try
			{
				setLayerType.invoke(view, inArgs);
			}
			catch (IllegalArgumentException e)
			{
			}
			catch (IllegalAccessException e)
			{
			}
			catch (InvocationTargetException e)
			{
			}
		}
	}

	/**
	 * 判断是否开启了强制硬件加速 ，view的接口不可用，需要用canvas
	 */
	public static boolean isHardwareAccelerated (Canvas canvas)
	{
		if (Build.VERSION.SDK_INT < 11)
		{ // Canvas.isHardwareAccelerated()的api level为11
			return false;
		}
		Class<Canvas> c = Canvas.class;
		Method isHardwareAcceleratedMethod = null;
		try
		{
			// 获取所有public/private/protected/默认
			// 方法的函数，如果只需要获取public方法，则可以调用getMethod.
			isHardwareAcceleratedMethod = c.getMethod("isHardwareAccelerated", (Class[]) null);
			// 将要执行的方法对象设置是否进行访问检查，也就是说对于public/private/protected/默认
			// 我们是否能够访问。值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false
			// 则指示反射的对象应该实施 Java 语言访问检查。
			isHardwareAcceleratedMethod.setAccessible(true);
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		if (isHardwareAcceleratedMethod != null)
		{
			try
			{
				return (Boolean)isHardwareAcceleratedMethod.invoke(canvas, (Object[]) null);
			}
			catch (IllegalArgumentException e)
			{
			}
			catch (IllegalAccessException e)
			{
			}
			catch (InvocationTargetException e)
			{
			}
		}
		return false;
	}

	public static boolean isLand(Context context)
	{
		return Configuration.ORIENTATION_LANDSCAPE == context.getResources().getConfiguration().orientation;
	}

	public static void setEnforcePhoneScreen(boolean isPhoneScreen) {
		ENFORCE_PHONE_SCREEN = isPhoneScreen;
	}

	public static void setFullScreenFlags(Activity activity)
	{
		activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		if (isPadScreen(activity))
//		{
//			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//		}
	}

	public static void clearFullScreenFlags(Activity activity)
	{
		activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		if (isPadScreen(activity))
//		{
//			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//		}
	}

	/**
	 * 魅族有悬浮底部工具栏
	 * @return
	 */
	public static boolean isMeiZu()
	{
		return "Meizu".equals(android.os.Build.BRAND);
	}

	public static boolean isNotFrameHeightEqualScreenDevice()
	{
		if ("Meizu".equals(android.os.Build.BRAND)
				|| "Kindle Fire".equals(android.os.Build.BRAND)
				|| "Amazon".equals(android.os.Build.BRAND))
		{
			return true;
		}
		return false;
	}

	public static boolean isFullScreenVersion(Context context)
	{
		if ("Amazon".equals(android.os.Build.BRAND))
		{
			return false;
		}

		if ("KFSAWA".equals(android.os.Build.MODEL) || "KFSAWI".equals(android.os.Build.MODEL))
		{
			return false;
		}

		if (android.os.Build.MODEL.contains("MI PAD"))
		{
			return false;
		}

		//只有大于等于4.4的系统需要自动全屏
		return (Build.VERSION.SDK_INT >= 19) && isPadScreen(context);
	}

	public static void setWindowLayoutAllFlags(Activity activity)
	{
		if (DeviceUtil.isSamsung() && android.os.Build.VERSION.SDK_INT >= 16 && android.os.Build.VERSION.SDK_INT < 21)
		{
			activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
		}
		activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}

	public static void clearWindowLayoutAllFlags(Activity activity)
	{
		if (DeviceUtil.isSamsung() && android.os.Build.VERSION.SDK_INT >= 16 && android.os.Build.VERSION.SDK_INT < 21)
		{
			activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
		}
		activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}
	
	//全屏状态改为内容位置不被挤压出屏幕范围
	public static void setLayoutInsetDecorFlagsInFullScreen(Activity activity)
	{
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR,
			WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
	}
	
	public static void clearLayoutInsetDecorFlagsInFullScreen(Activity activity)
	{
		activity.getWindow().setFlags(~WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR,
			WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
	}
	
	/**
	 * 得到 1dip的长度
	 */
	public static float getDip(Context context)
	{
		if (mDip <= 0)
		{
			mDip = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, // 1dip;
				context.getResources().getDisplayMetrics());
		}
		return mDip;
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 判断键盘是否显示，原理：取当前被键盘挤压的View的底部和整个窗口的底部对比阀值（DETECT_SKB_DELTA）
	 * @param context
	 * @return
	 */
	public static boolean isSoftKeyboardShowing(View sizeChangedFullScreenView, Context context)
	{
	    return isSoftKeyboardShowing(sizeChangedFullScreenView, context, false);
	}
	
	/**
     * 判断键盘是否显示，原理：取当前被键盘挤压的View的底部和整个窗口的底部对比阀值（DETECT_SKB_DELTA）
     * @param context
     * @param excludeBottomPadding 是否排除掉View的bottom padding
     * @return
     */
	public static boolean isSoftKeyboardShowing(View sizeChangedFullScreenView, Context context, boolean excludeBottomPadding)
    {
		// vivo 分屏模式下都判断为false
		if (DeviceUtil.isVivoDevice() && DeviceUtil.isVivoSplitMode(context)) {
			return false;
		}
		
        int[] location = new int[2];
        sizeChangedFullScreenView.getLocationInWindow(location);
        int bottom = sizeChangedFullScreenView.getBottom() + location[1];
        if (excludeBottomPadding) {
            bottom -= sizeChangedFullScreenView.getPaddingBottom();
        }
        int screen = getDisplayHeight(context);//不是分屏下是含状态栏及底部条的高度，如MX3,Pad底部ActionBar一般不包含
        return screen - bottom > DETECT_SKB_DELTA * getDip(context);
    }
	
	/**
	 * 判断键盘是否显示，原理：取当前windowFrameRect和整个窗口的底部对比阀值（DETECT_SKB_DELTA）
	 * @param view
	 * @param context
	 * @param windowFrameRect
	 * @return
	 */
	public static boolean isSoftKeyboardShowing(View view, Context context, Rect windowFrameRect)
	{
		int[] location = new int[2];
		view.getLocationInWindow(location);
		int bottom = windowFrameRect.height() + location[1];
		int screen = getDisplayHeight(context);//不是分屏下是含状态栏及底部条的高度，如MX3,Pad底部ActionBar一般不包含
		return screen - bottom > DETECT_SKB_DELTA * getDip(context);
	}
	
	/**
	 * 是否有魅族的SmartBar
	 */
	static Boolean mHasSmartBar;
	public static boolean hasSmartBar()
    {
		if (mHasSmartBar == null)
		{
			try
	    	{
	    		Method method =  Class.forName("android.os.Build").getMethod("hasSmartBar");
	    		mHasSmartBar = ((Boolean)method.invoke(null)).booleanValue();
	    	}
	    	catch (Exception e)
	    	{
	    		mHasSmartBar = (Build.DEVICE.equals("mx2"));
			}
		}
		
    	return mHasSmartBar;
    }
	
	public static void hideMzNb(Window window, ActionBar actionBar) {
        if (window != null) {
            try {
            	window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
				if (actionBar != null)
				{
					actionBar.hide();
				}
            	
                WindowManager.LayoutParams lp = window.getAttributes();
                Field mzNbFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_NAVIGATIONBAR");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                mzNbFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = mzNbFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                value &= ~bit;
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
