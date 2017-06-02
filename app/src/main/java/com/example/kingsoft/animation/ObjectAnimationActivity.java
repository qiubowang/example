package com.example.kingsoft.animation;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.kingsoft.CustomAdapter.R;

/**
 * Created by qiubowang on 2017/6/2.
 *
 * AnimatorSet 设置动画集合
 * ValueAnimator 本属性动画不正对任何属性，仅仅是定义一个动画而已，如果要让View对象的某个属性执行本动画，则需要根据AnimatorUpdateListener对动画值进行监听，从而在里面间接的设置属性值
 * AnimatorUpdateListener：动画的每一帧变化都会触发AnimatorUpdateListener，从而可以将变化的值复制给属性，间接达到动画效果
 * ObjectAnimator 继承 ValueAnimator，仅仅比ValueAnimator多一个peropertyName即是属性的名称，因此ObjectAnimator可以根据指定的peropertyName来对对象属性直接做动画
 * ObjectAnimator原理：ofFloat(mViewObjectButton, "rotationX",0 , 180)，mViewObjectButton对象名称，"rotationX"是View对象中有方法setRotationX来设定本View的X方向旋转值的，因此"rotationX"是根据
 * 反射setRotationX获取到的方法名称，通过不断的反射设置该值，从而就不停的重新设置了rotation达到了动画效果。从而可以判定，如果要执行ObjectAnimator，则view中必须拥有该set方法，否则无法产生动画；
 * 如果view中没有设置属性的初始值，则还必须拥有getXXX的方法，XXX为获取该属性值的方法，否则也无法产生动画。
 */

public class ObjectAnimationActivity extends Activity implements View.OnClickListener{
    private Button mObjectButton = null;
    private Button mViewObjectButton = null;
    private Button mTestButton = null;
    private LinearLayout mObjectLinear = null;
    private int testButtonStartWidth = 0;
    private ButtonWrapper mButtonWrapper = null;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.animation_layout);

        mObjectButton = (Button)findViewById(R.id.object_anim_button);
        mObjectButton.setOnClickListener(this);

        mViewObjectButton = (Button)findViewById(R.id.anim_button);

        mObjectLinear = (LinearLayout)findViewById(R.id.frame_animation_layout);

        mTestButton = (Button)findViewById(R.id.test_button);
        testButtonStartWidth = mTestButton.getLayoutParams().width;

        mButtonWrapper = new ButtonWrapper(mTestButton);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.object_anim_button){
            AnimatorSet set = new AnimatorSet();
            set.playTogether(ObjectAnimator.ofFloat(mViewObjectButton, "rotationX",0 , 180),
                    ObjectAnimator.ofFloat(mViewObjectButton, "rotationY", 0 , 180),
                    ObjectAnimator.ofFloat(mViewObjectButton, "scaleX", 1, 1.2f),
                    ObjectAnimator.ofFloat(mViewObjectButton, "alpha", 1, 0.4f));
            set.setDuration(5 * 1000).start();

            ValueAnimator valueAnimator = ObjectAnimator.ofInt(mObjectLinear, "backgroundColor", 0xffff8080, 0xff8080ff);
            valueAnimator.setEvaluator(new ArgbEvaluator());
            valueAnimator.setRepeatCount(-1);
            valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
            valueAnimator.setDuration(2000).start();

            //采用ValueAnimator实现动画，这里通过采用设置Button的宽度，是因为Button中的setWidth()设置的值并非Button的宽度，请查阅相关文档。
            //因此如果需要更新Button的宽度是没法通过属性动画来直接完成的(属性动画必须拥有该属性的set方法)，那么就需要间接来设置。
//            ValueAnimator testValAnim = ValueAnimator.ofInt(1,100);
//            final IntEvaluator evaluator = new IntEvaluator();
//            testValAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                    //获取当前动画进度，1 -100之间
//                    int currentVal = (Integer) valueAnimator.getAnimatedValue();
//                    //获取当前进度的百分比值
//                    float fraction = valueAnimator.getAnimatedFraction();
//                    //获取估算出来的值
//                    mTestButton.getLayoutParams().width = evaluator.evaluate(fraction,testButtonStartWidth, 500);
//                    mTestButton.requestLayout();
//                }
//            });
//            testValAnim.setDuration(5000)
//                    .start();

            //既然不能通过属性动画来设置Button的尺寸，那么可以将Button进行包装，让包装层拥有set，get属性从而达到直接使用属性动画来完成尺寸设置
            ObjectAnimator buttonAnim = ObjectAnimator.ofInt(mButtonWrapper, "width", 500);
            buttonAnim.setDuration(5000)
                    .start();
        }
    }
}
