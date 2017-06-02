package com.example.kingsoft.animation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.kingsoft.CustomAdapter.R;

/**
 * Created by kingsoft on 2017/5/31.
 *
 * View动画包括translate,scale,rotate, alpha
 */

public class ViewAnimationActivity extends Activity implements View.OnClickListener{
    private Button mButton = null;
    private LinearLayout mLinearLayout = null;
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.animation_layout );

        mButton = (Button)findViewById(R.id.anim_button);
        mButton.setOnClickListener(this);
        mLinearLayout = (LinearLayout)findViewById(R.id.frame_animation_layout) ;
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.view_anim);
        mButton.setAnimation(animation);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.anim_button){
            mLinearLayout.setBackgroundResource(R.drawable.frame_animation);
            AnimationDrawable animationDrawable = (AnimationDrawable)mLinearLayout.getBackground();
            animationDrawable.start();
        }
    }
}
