package com.example.kingsoft.CustomWidget;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

public class DeviceUtil
{
	private static final String[] GALAXY_S8_MODEL = {
			"SM-G9500", "SM-G9550", "SM-G9508"
	};
	private static Boolean sIsGalaxyS8 = null;

	public static boolean isGalaxyS8() {
		if (sIsGalaxyS8 != null) {
			return sIsGalaxyS8;
		}
		sIsGalaxyS8 = false;
		for (String model : GALAXY_S8_MODEL) {
			if (model.equalsIgnoreCase(Build.MODEL)) {
				sIsGalaxyS8 = true;
			}
		}
		return sIsGalaxyS8;
	}

	public static boolean isN5100()
	{
		return Build.MODEL.equals("GT-N5100");
	}
	
	public static boolean isN7()
	{
		return "Nexus 7".equals(Build.MODEL);
	}
	
	public static boolean isN7_2()
	{
		return "Nexus 7".equals(Build.MODEL) && "flo".equals(Build.DEVICE);//PRODUCT razor DEVICE flo
	}
	
	public static boolean isHtcFlyer()
	{
		return Build.MODEL.equals("HTC Flyer P510e");
	}
	
	/**
	 * SurfaceView刚开始会有残影的设备
	 * @return
	 */
	public static boolean isFixSurfaceMess()
	{
		return isN7_2() || Build.MODEL.equals("Acer Iconia A500");
	}
	
	public static boolean isStatusBarBottom()
	{
		return (Build.VERSION.SDK_INT < 14 && Build.MODEL.startsWith("ASUS Transformer Pad TF")) ||
			Build.MODEL.equals("EBEN T7");
	}
	
	//for bug:176848 Fire Phone 处理
	public static boolean isStatusBarInvible(Activity context)
	{
		return Build.BRAND.equalsIgnoreCase("amazon") && DisplayUtil.isPhoneScreen(context);
	}
	
	public static boolean isStatusBarInvisbleOrBottom(Activity context)
	{
		return isStatusBarBottom() || isStatusBarInvible(context);
	}
	
	private static final String HP_MANUFACTURER = "Hewlett-Packard";
	private static final String HP_MODEL = "HP Pro Slate";
	public static boolean isHpDevice()
	{
		String model = android.os.Build.MODEL;
		String manu = android.os.Build.MANUFACTURER;
		return (model.contains(HP_MODEL) && manu.equals(HP_MANUFACTURER));
	}

	public static boolean isSamSung601()
	{
		return "SM-P601".equals(android.os.Build.MODEL);
	}

	private static final String[] AMAZON_MODEL = {"KFAPWI", "KFTHWI", "KFSAWI", "KFARWI"};
	public static boolean isAmazonPad()
	{
		String model = android.os.Build.MODEL;
		for (int i = 0; i < AMAZON_MODEL.length; ++i)
		{
			if (model.equals(AMAZON_MODEL[i]))
			{
				return true;
			}
		}
		return false;
	}

	public static boolean isAmazon()
	{
	    String manufacturer = android.os.Build.MANUFACTURER;
	    return "Amazon".equalsIgnoreCase(manufacturer);
	}

	private static final String[] HP_TRUCO_MODEL = {"HP SlateBook 14 PC", "Slate 21 Pro", "Slate 21"};
	public static boolean isHpTrucoModel ()
	{
		if(isAndroidN())
			return true;//暂时放置在此处
		String model = android.os.Build.MODEL;
		for (int i = 0; i < HP_TRUCO_MODEL.length; i++)
		{
			if (model.equals(HP_TRUCO_MODEL[i]))
			{
				return true;
			}
		}
		return false;
	}

	private static final String[] LENOVO_MULWIN_MODEL = {"YOGA Tablet"};//"YOGA Tablet 2 Pro-1380F"
	public static boolean isLenovoMulWindow ()
	{
		String model = android.os.Build.MODEL;
		for (int i = 0; i < LENOVO_MULWIN_MODEL.length; i++)
		{
			if (model.startsWith(LENOVO_MULWIN_MODEL[i]))
			{
				return true;
			}
		}
		return false;
	}

	private static final String[] MZ_MODEL = {"M351","M045", "MX4"};
	public static boolean isMz()
	{
		String model = android.os.Build.MODEL;
		for (int i = 0; i < MZ_MODEL.length; ++i)
		{
			if (model.equals(MZ_MODEL[i]))
			{
				return true;
			}
		}
		return false;
	}

	public static boolean isMzMX3()
	{
		String model = android.os.Build.MODEL;
		return "M351".equals(model);
	}

	public static boolean isHuaweiX1()
	{
		return "Huawei".equalsIgnoreCase(Build.BRAND) && Build.MODEL.startsWith("X1");
	}

	public static boolean isTeclastP89Mini()
	{
		return "Teclast".equalsIgnoreCase(Build.BRAND) && Build.MODEL.equals("P89mini(E2W6)");
	}

	public static boolean isHPProSlate8()
	{
		return Build.MODEL.equals("HP Pro Slate 8");
	}

	public static boolean isSamsung()
	{
		return "samsung".equals(android.os.Build.BRAND);
	}

	public static boolean isGTI9100()
	{
		return "GT-I9100".equals(Build.MODEL);
	}

	public static boolean isXiaomi2s()
	{
	    return "Xiaomi".equalsIgnoreCase(android.os.Build.MANUFACTURER) &&
	            "MI 2S".equalsIgnoreCase(android.os.Build.MODEL);
	}

	public static boolean isHaiweiH60()
	{
		return "Huawei".equals(android.os.Build.BRAND) && "hwH60".equalsIgnoreCase(Build.DEVICE);
	}
	
    public static boolean isAndroidN()
    {
    	return Build.VERSION.SDK_INT >= 24 || "N".equals(Build.VERSION.CODENAME);
    }
    
    public static boolean isVivoDevice() {
    	return Build.MODEL.startsWith("vivo");
    }
    
    static final String SPLITMODE = "splitmode";
    static final String AUTHORITY_APPSCONFIGS = "com.vivo.smartmultiwindow";
    static final String TABLE_PUBLICMODE = "publicmode";
    static Uri CONTENT_PUBLICMODE_URI;
    
    public static boolean isVivoSplitMode(Context context){
        if(context == null){
            return false;
        }
        ContentResolver cr = context.getContentResolver();
        if(cr == null){
            return false;
        }
        if (null == CONTENT_PUBLICMODE_URI)
        	CONTENT_PUBLICMODE_URI = Uri.parse("content://" + AUTHORITY_APPSCONFIGS + "/" + TABLE_PUBLICMODE);
        
        boolean mode = false;
        Cursor cursor = null;
        try {
        	cursor = cr.query(CONTENT_PUBLICMODE_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                String value = cursor.getString(cursor.getColumnIndex(SPLITMODE));
                if(value != null){
                    value.trim();
                }
                if(value != null && value.equals("1")){
                    mode = true;
                }
            }
        } catch (Throwable e) {
        	e.printStackTrace();
        }
        finally {
	        if(cursor != null){
	            cursor.close();
	        }
        }
        return mode;
    }
    
    public static boolean isLeshiDevice() {
    	return Build.MODEL.startsWith("Letv");
    }
}
