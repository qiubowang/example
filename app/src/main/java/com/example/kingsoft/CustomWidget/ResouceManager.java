package com.example.kingsoft.CustomWidget;

import android.content.Context;
import android.content.res.Resources;

import com.example.kingsoft.CustomAdapter.R;


/**
 * Created by kingsoft on 2017/8/10.
 */

public class ResouceManager {
    private Context mContext = null;

    public ResouceManager(Context context){
        mContext = context;
    }
    private String getResourcePackageName(Context context) {
        return context.getPackageName();
    }

    public String getString(int id) {
        return id == 0 ? "" : mContext.getResources().getString(id);
    }

    public String getString(String name)
    {
        return getString(getStringId(name));
    }

    public int getStringId(String name)
    {
        Class c = R.string.class;
        int resID = 0;
        try
        {
            resID = (Integer) c.getDeclaredField(name).get(c);
        }
        catch (Exception e)
        {
        }
        return resID;
    }

    public String[] getStringArray(String name) {
        Class c = android.R.array.class;
        int resID = 0;
        try
        {
            resID = (Integer) c.getDeclaredField(name).get(c);
        }
        catch (Exception e)
        {
        }
        return resID == 0 ? new String[]{} : mContext.getResources().getStringArray(resID);
    }

    public int[] getIntArray(String name) {
        Class c = android.R.array.class;
        int resID = 0;
        try
        {
            resID = (Integer) c.getDeclaredField(name).get(c);
        }
        catch (Exception e)
        {
        }
        return resID == 0 ? new int[]{} : mContext.getResources().getIntArray(resID);
    }

    public int getLayoutId(String name)
    {
        Class c = R.layout.class;
        int resID = 0;
        try
        {
            resID = (Integer) c.getDeclaredField(name).get(c);
        }
        catch (Exception e)
        {
        }
        return resID;
    }

    public int getDrawableId(String name)
    {
        Class c = R.drawable.class;
        int resID = 0;
        try
        {
            resID = (Integer) c.getDeclaredField(name).get(c);
        }
        catch (Exception e)
        {
        }
        return resID;
    }

    public int getAnimId(String name)
    {
        Class c = R.anim.class;
        int resID = 0;
        try
        {
            resID = (Integer) c.getDeclaredField(name).get(c);
        }
        catch (Exception e)
        {
        }
        return resID;
    }

    public int getArrayId(String name)
    {
        Class c = android.R.array.class;
        int resID = 0;
        try
        {
            resID = (Integer) c.getDeclaredField(name).get(c);
        }
        catch (Exception e)
        {
        }
        return resID;
    }

    public int getAttrId(String name)
    {
        Class c = R.attr.class;
        int resID = 0;
        try
        {
            resID = (Integer) c.getDeclaredField(name).get(c);
        }
        catch (Exception e)
        {
        }
        return resID;
    }

    public int getId(String name)
    {
        Class c = R.id.class;
        int resID = 0;
        try
        {
            resID = (Integer) c.getDeclaredField(name).get(c);
        }
        catch (Exception e)
        {
        }
        return resID;
    }

    public int getStyleId(String name)
    {
        Class c = R.style.class;
        int resID = 0;
        try
        {
            resID = (Integer) c.getDeclaredField(name).get(c);
        }
        catch (Exception e)
        {
        }
        return resID;
    }

    public int getDimenId(String name)
    {
        Class c = R.dimen.class;
        int resID = 0;
        try
        {
            resID = (Integer) c.getDeclaredField(name).get(c);
        }
        catch (Exception e)
        {
        }
        return resID;
    }

    public int getXmlId(String name)
    {
        Class c = R.xml.class;
        int resID = 0;
        try
        {
            resID = (Integer) c.getDeclaredField(name).get(c);
        }
        catch (Exception e)
        {
        }
        return resID;
    }

    public int getColorId(String name)
    {
        Class c = R.color.class;
        int resID = 0;
        try
        {
            resID = (Integer) c.getDeclaredField(name).get(c);
        }
        catch (Exception e)
        {
        }
        return resID;
    }

    public int getColor(int id) {
        return id == 0 ? 0 : mContext.getResources().getColor(id);
    }

    public int getDimen(int id) {
        return id == 0 ? 0 : mContext.getResources().getDimensionPixelSize(id);
    }

    public int[] getStyleableArr(String name)
    {
        Class c = R.styleable.class;
        int[] resID = null;
        try
        {
            Object o = c.getDeclaredField(name).get(c);
            if(o instanceof int[]) {
                resID = (int[]) o;
            }
        }
        catch (Exception e)
        {
        }
        return resID;
    }

    public int getStyleableId(String name)
    {
        Class c = R.styleable.class;
        Integer resID = 0;
        try
        {
            resID = (Integer) c.getDeclaredField(name).get(c);
        }
        catch (Exception e)
        {
        }
        return resID;
    }
}
