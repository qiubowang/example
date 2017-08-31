package com.example.kingsoft.CustomWidget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.kingsoft.CustomAdapter.R;

/**
 * Created by kingsoft on 2017/8/26.
 */

public class WaterRippleFrameLayout extends FrameLayout{
    private ImageView mImageView = null;
    private boolean mIsInit = false;
    public WaterRippleFrameLayout(@NonNull Context context) {
        super(context);
    }

    public WaterRippleFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WaterRippleFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WaterRippleFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP){
            onClick(mImageView,ev);
        }
        return true;//super.dispatchTouchEvent(ev);
    }

    public void  init(){
        if (mIsInit)
            return;
        getViewTreeObserver().addOnPreDrawListener(() -> {
            if (!mIsInit){
                mImageView = (ImageView)findViewById(R.id.ppt_remote_img_clicker);
                mImageView.setVisibility(GONE);
                mIsInit =  true;
            }
            return true;
        });
    }

    public void onClick(View v, MotionEvent ev) {
        if (null != mImageView) {
            float curX = ev.getX();
            float curY = ev.getY();
            mImageView.setVisibility(VISIBLE);
            ViewLayoutUnitl.setViewMargin(mImageView, (int)(curX - mImageView.getWidth()/2), (int)(curY - mImageView.getHeight()/2), (int)curX, (int)curY);
            doWaterRippleAnim(mImageView);
        }
    }

    public void doWaterRippleAnim(View view){
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1,2,1,2);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(1000);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animationSet);
    }

    public static class  ViewLayoutUnitl{
        public static void setViewMargin(View view, int x, int y, int m, int n){
            ViewGroup.LayoutParams params = view.getLayoutParams();
            if (params instanceof FrameLayout.LayoutParams){
                ((MarginLayoutParams)params).leftMargin = x;
                ((MarginLayoutParams)params).rightMargin = -x;
                ((MarginLayoutParams)params).topMargin = y;
                ((MarginLayoutParams)params).bottomMargin = -y;
                view.setLayoutParams(params);

            }else if (params instanceof LinearLayout.LayoutParams){
                ((LinearLayout.LayoutParams)params).leftMargin = x;
                ((LinearLayout.LayoutParams)params).rightMargin = -x;
                ((LinearLayout.LayoutParams)params).topMargin = y;
                ((LinearLayout.LayoutParams)params).bottomMargin = -y;
                view.setLayoutParams(params);
            }else {
                FrameLayout.LayoutParams newParams = new FrameLayout.LayoutParams(params);
                newParams.leftMargin = x;
                newParams.rightMargin = -x;
                newParams.topMargin = y;
                newParams.bottomMargin = -y;
                view.setLayoutParams(newParams);
            }
        }
    }
}
