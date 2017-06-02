package com.example.kingsoft.animation;

import android.widget.Button;

/**
 * Created by qiubowang on 2017/6/2.
 */

public class ButtonWrapper {
    private Button mButton = null;

    public ButtonWrapper(Button button){
        mButton = button;
    }

    public void setWidth(int width){
        mButton.getLayoutParams().width = width;
        mButton.requestLayout();
    }

    public int getWidth(){
        return mButton.getLayoutParams().width;
    }

    public void setHeight(int height){
        mButton.getLayoutParams().height = height;
        mButton.requestLayout();
    }

    public int getHeight(){
        return mButton.getLayoutParams().height;
    }
}
