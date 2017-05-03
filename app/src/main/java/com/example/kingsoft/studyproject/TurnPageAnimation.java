package com.example.kingsoft.studyproject;

import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.kingsoft.CustomAdapter.R;

/**
 * Created by kingsoft on 2017/3/9.
 */

public class TurnPageAnimation extends FrameLayout{
    private VelocityTracker velocityTracker = null;
    private float viewWidth = 0, viewHeight = 0;
    private LinearLayout topPageView; //顶部页（包含阴影，下同）

    private ImageView touchImage = null;


    public TurnPageAnimation(@NonNull Context context) {
        super(context);
    }

    public TurnPageAnimation(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TurnPageAnimation(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TurnPageAnimation(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    public void  initVal(){
        Log.d("init","已经初始化");
//        Debug.waitForDebugger();
        this.setOnTouchListener(new OnTouchAnimation());
        this.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                topPageView = (LinearLayout)findViewById(R.id.touch_page);
                viewWidth = topPageView.getWidth();
                viewHeight = topPageView.getHeight();

                touchImage = (ImageView)findViewById(R.id.ppt_remote_img_clicker);
                touchImage.setVisibility(View.GONE);
                return true;
            }
        });

    }



    private class OnTouchAnimation implements OnTouchListener{
        private float preX, preY, curX, curY;
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Log.d("customMotionEvent",motionEvent.getAction() + "");
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    preX = curX = motionEvent.getX();
                    preY = curY = motionEvent.getY();
                    if (null == velocityTracker)
                        velocityTracker = VelocityTracker.obtain();
                    velocityTracker.addMovement(motionEvent);
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    velocityTracker.computeCurrentVelocity(1000);
                    if(Math.abs(velocityTracker.getXVelocity()) < 500){
                        curX = motionEvent.getX();
                        curY = motionEvent.getY();
                        if (velocityTracker.getXVelocity() > 0 && (curX - preX) >= viewWidth/3){
                            String aa = "向后翻";
                        }else if (velocityTracker.getXVelocity() < 0 && Math.abs((curX - preX)) <= viewWidth/3){
                            String aa = "向前翻";
                        }
                    }else{
                        if (velocityTracker.getXVelocity() < 0){
                            String aa = "向前翻";
                        }else if(velocityTracker.getXVelocity() > 0){
                            String aa = "向后翻";
                        }
                    }
                    onClickEvent(motionEvent);
                    break;
            }
            return true;
        }
    }

    private void clickAnimation(final ImageView imageView){
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);
        alphaAnimation.setDuration(1000);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,3.0f,1.0f,3.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(1000);
        AnimationSet animationSet = new AnimationSet(false);

        animationSet.addAnimation(alphaAnimation);
        animationSet.setFillEnabled(true);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setAnimationListener(getAnimationListener(imageView));
        imageView.startAnimation(animationSet);
    }

    public AnimationListener getAnimationListener(final ImageView view){
        return new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

    public void onClickEvent(MotionEvent event){
        int x = (int)event.getX();
        int y = (int)event.getY();
//        setViewLocation(touchImage, x - (int)touchImage.getWidth()/2, y - (int)touchImage.getHeight()/2);
        touchImage.setVisibility(VISIBLE);
        clickAnimation(touchImage);
    }

    //重新定位
    public static void setViewLocation(View view , int x, int y) {
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof FrameLayout.LayoutParams) {
            ((MarginLayoutParams) layoutParams).leftMargin = x;
            ((MarginLayoutParams) layoutParams).rightMargin = -x;
            ((MarginLayoutParams) layoutParams).topMargin = y;
            ((MarginLayoutParams) layoutParams).bottomMargin = -y;
            view.setLayoutParams(layoutParams);
        } else if(layoutParams instanceof LinearLayout.LayoutParams){
            ((LinearLayout.LayoutParams) layoutParams).leftMargin = x;
            ((LinearLayout.LayoutParams) layoutParams).rightMargin = -x;
            ((LinearLayout.LayoutParams) layoutParams).topMargin = y;
            ((LinearLayout.LayoutParams) layoutParams).bottomMargin = -y;
            view.setLayoutParams(layoutParams);
        } else {
            FrameLayout.LayoutParams newLayoutParams = new FrameLayout.LayoutParams(layoutParams);
            newLayoutParams.leftMargin = x;
            newLayoutParams.rightMargin = -x;
            newLayoutParams.topMargin = y;
            newLayoutParams.bottomMargin = -y;
            view.setLayoutParams(newLayoutParams);
        }
    }

}
